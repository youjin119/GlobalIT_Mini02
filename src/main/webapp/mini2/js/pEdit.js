document.addEventListener("DOMContentLoaded", function () {
  let savedRange;
  let lastInsertedImage = null;

  const editor = document.getElementById('editor');


  let uploadedImages = [...originalImages]; // 현재 포함된 이미지 목록
  let insertedImages = []; // 새로 추가된 이미지
  let deletedImages = [];
  let insertedImageStack = [];
  
  function saveCursor() {
	    const sel = window.getSelection();
	    if (sel.rangeCount > 0) {
	      savedRange = sel.getRangeAt(0).cloneRange();
	    }
	  }
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
  
  
  
  editor.addEventListener("input", function () {
	  const currentImageNames = Array.from(editor.querySelectorAll("img.inserted-image"))
	    .map(img => img.src.substring(img.src.lastIndexOf("/") + 1));

	  const removed = insertedImages.filter(name => !currentImageNames.includes(name));

	  if (removed.length > 0) {
	    removed.forEach(name => {
	      // 삭제 목록에 추가
	      deletedImages.push(name);
	      
	      // 서버에 삭제 요청
	      fetch("/deleteImage.do", {
	        method: "POST",
	        headers: { "Content-Type": "application/json" },
	        body: JSON.stringify({ images: [name] })
	      });
	    });

	    // insertedImages에서도 제거
	    insertedImages = insertedImages.filter(name => currentImageNames.includes(name));
	  }
	});
  
  document.getElementById('imgInput').addEventListener('change', async function (e) {
    const file = e.target.files[0];
    if (!file || !file.type.startsWith('image/')) return;

    const formData = new FormData();
    formData.append("ofile", file);

    try {
      const response = await fetch("/uploadImage.do", {
        method: "POST",
        body: formData
      });

      if (!response.ok) {
        alert("이미지 업로드에 실패했습니다.");
        return;
      }

      const fileName = await response.text();

      uploadedImages.push(fileName);      // 현재 이미지 목록에 추가
      insertedImages.push(fileName);      // 새로 추가된 이미지 기록

      

      const imgTag = document.createElement("img");
      imgTag.src = "/uploads/" + fileName;
      imgTag.className = "inserted-image";
      imgTag.style.maxWidth = "100%";

      
	  // 커서 위치에 삽입
      editor.focus();
      if (savedRange) {
        const sel = window.getSelection();
        sel.removeAllRanges();
        sel.addRange(savedRange);
        savedRange.deleteContents();
        savedRange.insertNode(imgTag);
        
        insertedImageStack.push(imgTag);
        
        const newRange = document.createRange();
        newRange.setStartAfter(imgTag);
        newRange.collapse(true);
        sel.removeAllRanges();
        sel.addRange(newRange);
      } else {
        editor.appendChild(imgTag);
        insertedImageStack.push(imgTag);
        editor.appendChild(document.createElement("br"));
      }

      lastInsertedImage = imgTag;
      e.target.value = '';
    } catch (err) {
      console.error("이미지 업로드 에러:", err);
      alert("이미지 업로드 중 오류가 발생했습니다.");
    }
  });

  // 🔥 새로고침 전, 새로 추가되었지만 저장되지 않은 이미지 삭제
  window.addEventListener("beforeunload", function () {
    const form = document.pWriteFrm;
    const finalImages = Array.from(document.querySelectorAll("input[name='imgid']")).map(i => i.value);

    // 원본에 없고, 현재 제출되지 않은 이미지만 삭제
    const unsavedImages = insertedImages.filter(name => !finalImages.includes(name));

    if (unsavedImages.length > 0) {
      const data = new URLSearchParams();
      data.append("images", unsavedImages.join(","));
      navigator.sendBeacon("/deleteImage.do", data);
    }
  });
  
  // ✅ submit 시, content 저장 + 삭제 대상 이미지 정리
  window.submitPost = function () {
    const form = document.pWriteFrm;
    const content = document.getElementById('editor').innerHTML.trim();
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
	console.log('입력값:', '[' + tagValue + ']');
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

    // 기존 input 제거 후 다시 추가
    const oldInputs = form.querySelectorAll("input[name='imgid']");
    oldInputs.forEach(input => input.remove());

    const imgs = document.querySelectorAll("#editor img.inserted-image");
    imgs.forEach(img => {
      const src = img.src;
      const fileName = src.substring(src.lastIndexOf("/") + 1);
      const input = document.createElement("input");
      input.type = "hidden";
      input.name = "imgid";
      input.value = fileName;
      form.appendChild(input);
    });

    // 기존 이미지 중 삭제된 것만 서버에 요청
    const currentImgNames = Array.from(imgs).map(img =>
      img.src.substring(img.src.lastIndexOf("/") + 1)
    );
    const removedImages = originalImages.filter(name => !currentImgNames.includes(name));

    if (removedImages.length > 0) {
      fetch("/deleteImage.do", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ images: removedImages })
      });
    }

    document.getElementById('content').value = content;
    return true;
  };


  window.triggerImageUpload = function () {
    document.getElementById('imgInput').click();
  };

  window.undoLastImage = function () {
	  if (insertedImageStack.length === 0) return;

	  const lastImage = insertedImageStack.pop(); // ✅ 마지막 삽입 이미지 꺼냄
	  const src = lastImage.src;
	  const fileName = src.substring(src.lastIndexOf("/") + 1);

	  uploadedImages = uploadedImages.filter(name => name !== fileName);
	  insertedImages = insertedImages.filter(name => name !== fileName);

	  // 서버에 삭제 요청
	  fetch("/deleteImage.do", {
	    method: "POST",
	    headers: {
	      "Content-Type": "application/json"
	    },
	    body: JSON.stringify({ images: [fileName] })
	  });

	  // hidden input 삭제
	  const inputs = document.querySelectorAll("input[name='imgid']");
	  inputs.forEach(input => {
	    if (input.value === fileName) input.remove();
	  });

	  // 이미지 제거
	  lastImage.remove();
	};
  
  ["click", "keyup", "mouseup", "input"].forEach(event => {
	  editor.addEventListener(event, saveCursor);
	});
	// 버튼에 이벤트 리스너 추가
	  document.getElementById("imgAddBtn").addEventListener("click", triggerImageUpload);
	  document.getElementById("imgUndoBtn").addEventListener("click", undoLastImage);
	  document.pWriteFrm.addEventListener("submit", function (e) {
	    if (!submitPost()) {
	      e.preventDefault();
	    }
	  });
});
