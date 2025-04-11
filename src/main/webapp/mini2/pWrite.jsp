
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>여기저기 기사 쓰기</title>
<style>
h2 {
	border: 1px solid black;
	width: 80%;
	height: 150px;
	margin: auto;
	line-height: 150px;
	text-align: center;
}

table {
	margin: auto;
	margin-top: 50px;
}

tr>td:nth-child(1) {
	text-align: center;
	width: 150px;
	font-size: 16px;
	font-weight: bold;
}

tr>td:nth-child(2)>input {
	height: 30px;
}

td {
	padding-bottom: 30px;
}

tr>td:nth-child(2)>button {
	margin-top: 10px;
}

.inserted-image {
	max-width: 100%;
	height: auto;
	display: block; /* ✅ 핵심! 블록 요소로 만들기 */
	margin: 10px auto;
	clear: both; /* ✅ float 방지 (혹시 몰라서) */
}
#editor {
  		text-align: center;
  		line-height: 1.6;
	}
</style>
<script>

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
});
</script>

</head>
<body>
	<h2>기사쓰기</h2>
	<form name="pWriteFrm" method="post" action="/pWrite.do"
		onsubmit="return submitPost();">
		<table border="0" width="80%">
			<tr>
				<td>기사제목 :</td>
				<td><input type="text" name="title" style="width: 50%;" /></td>
			</tr>
			<tr>
				<td>기사 소개 태그 :</td>
				<td><input type="text" name="tag" style="width: 25%;" /></td>
			</tr>
			<tr>
				<td>국가/지역 :</td>
				<td><input type="text" name="country" style="width: 25%;" /></td>
			</tr>
			<tr>
				<td>기사 내용 :</td>
				<td>
					<div id="editor" contenteditable="true"
						style="width: 90%; min-height: 300px; border: 1px solid; padding: 10px; overflow-y: visible;"></div>
					<input type="hidden" name="content" id="content"> <!-- 이미지 업로드 버튼 -->
					<button type="button" onclick="triggerImageUpload()">이미지
						추가</button>
					<button type="button" onclick="undoLastImage()">이미지 취소</button> <input
					type="file" id="imgInput" accept="image/*" style="display: none;" />
				</td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<button type="submit">작성 완료</button>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>