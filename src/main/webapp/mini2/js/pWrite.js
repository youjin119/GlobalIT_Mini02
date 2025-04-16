
let savedRange;

  function saveCursor() {
    const sel = window.getSelection();
    if (sel.rangeCount > 0) {
      savedRange = sel.getRangeAt(0).cloneRange();
    }
  }
  
window.triggerImageUpload = function () {
  saveCursor();
  document.getElementById('imgInput').click();
};

document.addEventListener("DOMContentLoaded", function () {
  const editor = document.getElementById('editor');
  let uploadedImages = [];
  let insertedImages = [];

  
  
  
  editor.addEventListener('mouseup', saveCursor);
  editor.addEventListener('keyup', saveCursor);
  editor.addEventListener('focus', saveCursor);

  editor.addEventListener("keydown", function (e) {
    const sel = window.getSelection();
    if (!sel.rangeCount) return;

    const range = sel.getRangeAt(0);
    const container = range.startContainer;

    if (e.key === "Tab") {
      e.preventDefault();
      const tabNode = document.createTextNode("\u00a0\u00a0\u00a0\u00a0");
      range.insertNode(tabNode);
      range.setStartAfter(tabNode);
      range.collapse(true);
      sel.removeAllRanges();
      sel.addRange(range);
      saveCursor();
    }

    if (e.key === "Backspace" || e.key === "Delete") {
      let targetImg = null;

      if (container.nodeType === 3) {
        targetImg = container.previousSibling;
      } else if (container.nodeType === 1) {
        targetImg = container.childNodes[range.startOffset - 1];
      }

      if (targetImg && targetImg.tagName === "IMG") {
        const src = targetImg.src;
        const fileName = src.substring(src.lastIndexOf("/") + 1);

        fetch("/deleteImage.do", {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify({ images: [fileName] })
        });

        uploadedImages = uploadedImages.filter(name => name !== fileName);

        document.querySelectorAll("input[name='imgid']").forEach(input => {
          if (input.value === fileName) input.remove();
        });

        // 이미지 제거는 브라우저 기본 동작에 맡김
      }
    }
  });
  editor.addEventListener("paste", function (e) {
        const clipboardData = e.clipboardData || window.clipboardData;
        const items = clipboardData && clipboardData.items;

        if (!items) return;

        for (let i = 0; i < items.length; i++) {
          const item = items[i];

          // 📛 이미지 데이터 직접 붙여넣기 방지
          if (item.kind === 'file' && item.type.startsWith("image/")) {
            e.preventDefault();
            return;
          }

          // 📛 이미지 포함된 HTML도 차단
          if (item.kind === 'string' && item.type === 'text/html') {
            e.preventDefault(); // 먼저 무조건 차단
            item.getAsString(function (html) {
              // img 태그 포함된 경우
              if (html.includes("<img") || html.match(/<img\s[^>]*src=["'][^"']+["']/i)) {
                // 아무 것도 안 함, 차단됨
              } else {
                // 이미지가 아니면 직접 붙여넣기 (text/html 정상처리)
                document.execCommand("insertHTML", false, html);
              }
            });
            return;
          }
        }
      });

	
  document.getElementById('imgInput').addEventListener('change', async function (e) {
    const file = e.target.files[0];
    if (!file || !file.type.startsWith('image/')) return;

    const formData = new FormData();
    formData.append("ofile", file);

    try {
      const res = await fetch("/uploadImage.do", { method: "POST", body: formData });
      const fileName = await res.text();
      uploadedImages.push(fileName);

      const imgTag = document.createElement("img");
      imgTag.src = "/uploads/" + fileName;
      imgTag.className = "inserted-image";

      // 이미지 삽입
      editor.focus();
      if (savedRange) {
        const sel = window.getSelection();
        sel.removeAllRanges();
        sel.addRange(savedRange);

        savedRange.deleteContents();
        savedRange.insertNode(imgTag);

        const br = document.createElement("br");
        imgTag.after(br);

        const newRange = document.createRange();
        newRange.setStartAfter(br);
        newRange.collapse(true);
        sel.removeAllRanges();
        sel.addRange(newRange);

        saveCursor();
      } else {
        editor.appendChild(imgTag);
        editor.appendChild(document.createElement("br"));
      }

      insertedImages.push(imgTag);
      e.target.value = '';

    } catch (err) {
      console.error("이미지 업로드 에러:", err);
      alert("이미지 업로드 중 오류가 발생했습니다.");
    }
  });

  window.undoLastImage = function () {
    const lastImage = insertedImages.pop();
    if (lastImage && lastImage.parentNode) {
      const src = lastImage.src;
      const fileName = src.substring(src.lastIndexOf("/") + 1);

      uploadedImages = uploadedImages.filter(name => name !== fileName);

      fetch("/deleteImage.do", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ images: [fileName] })
      });

      document.querySelectorAll("input[name='imgid']").forEach(input => {
        if (input.value === fileName) input.remove();
      });

      lastImage.remove();
    }
  };

  window.addEventListener("beforeunload", function () {
    const usedImages = Array.from(document.querySelectorAll("input[name='imgid']")).map(i => i.value);
    const unsavedImages = uploadedImages.filter(name => !usedImages.includes(name));
    if (unsavedImages.length > 0) {
      const data = new URLSearchParams();
      data.append("images", unsavedImages.join(","));
      navigator.sendBeacon("/deleteImage.do", data);
    }
  });

  window.submitPost = function () {
    const form = document.pWriteFrm;
    const content = editor.innerHTML.trim();
    const plainText = editor.innerText.trim();
    const hasImages = editor.querySelectorAll("img.inserted-image").length > 0;

    if (form.title.value.trim() === "") {
      alert("기사제목을 입력하세요.");
      form.title.focus();
      return false;
    }
    if (form.tag.value.trim() === "") {
      alert("기사 소개 태그를 입력하세요.");
      form.tag.focus();
      return false;
    }
    
 	// 태그 형식 검사: #으로 시작하고 ,로 구분되어야 함
	const tagValue = form.tag.value.trim();
	const tagPattern = /^(#[\w가-힣]+)(,#[\w가-힣]+)*$/;

	if (!tagPattern.test(tagValue)) {
	  alert("태그 형식이 올바르지 않습니다.\n예: #한국");
	  form.tag.focus();
	  return false;
	}
    
    if (form.country.value.trim() === "") {
      alert("국가/지역을 입력하세요.");
      form.country.focus();
      return false;
    }
    if (plainText === "" && !hasImages) {
      alert("내용을 입력하세요.");
      editor.focus();
      return false;
    }
    
 // ✅ 기존 imgid hidden input들 제거
    document.querySelectorAll("input[name='imgid']").forEach(input => input.remove());

    // ✅ 현재 editor에 삽입된 이미지만 input으로 추가
    const images = editor.querySelectorAll("img.inserted-image");
    images.forEach(img => {
      const fileName = img.src.split("/").pop();
      const hiddenInput = document.createElement("input");
      hiddenInput.type = "hidden";
      hiddenInput.name = "imgid";
      hiddenInput.value = fileName;
      form.appendChild(hiddenInput);
    });

    document.getElementById('content').value = content;
    return true;
  };
  // 버튼에 이벤트 리스너 추가
  	  document.getElementById("imgAddBtn").addEventListener("click", triggerImageUpload);
  	  document.getElementById("imgUndoBtn").addEventListener("click", undoLastImage);
  	  document.pWriteFrm.addEventListener("submit", function (e) {
  	    if (!submitPost()) {
  	      e.preventDefault();
  	    }
  	  });
});