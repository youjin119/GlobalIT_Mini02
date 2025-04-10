
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
	document.addEventListener("DOMContentLoaded", function () {
	  let savedRange;
	  let lastInsertedImage = null; // 마지막 삽입된 이미지 기억
	  const editor = document.getElementById('editor');
	  let uploadedImages = [];
	  let insertedImages = [];
	  
	  editor.addEventListener('mouseup', saveCursor);
	  editor.addEventListener('keyup', saveCursor);
	  editor.addEventListener('focus', saveCursor);
	  editor.addEventListener("keydown", function (e) {
		  if (e.key === "Backspace" || e.key === "Delete") {
		    const sel = window.getSelection();
		    if (!sel.rangeCount) return;
	
		    const range = sel.getRangeAt(0);
		    const container = range.startContainer;
		    if (e.key === "Tab") {
		        e.preventDefault(); // ❌ 기본 동작 막기 (다른 input으로 안 넘어가게)

		        const sel = window.getSelection();
		        if (!sel.rangeCount) return;

		        const range = sel.getRangeAt(0);
		        const tabNode = document.createTextNode("\u00a0\u00a0\u00a0\u00a0"); // 띄어쓰기 4칸
		        range.insertNode(tabNode);

		        // 커서를 방금 삽입한 노드 뒤로 이동
		        range.setStartAfter(tabNode);
		        range.collapse(true);
		        sel.removeAllRanges();
		        sel.addRange(range);
		        
		        saveCursor(); // 커서 위치 저장
		      }
		    // 백스페이스/딜리트 직전에 있는 이미지 확인
		    let targetImg = null;
	
		    if (container.nodeType === 3) {
		      // 텍스트 노드일 경우: 이전 형제를 탐색
		      targetImg = container.previousSibling;
		    } else if (container.nodeType === 1) {
		      // 요소 노드일 경우: 커서 바로 앞 요소
		      targetImg = container.childNodes[range.startOffset - 1];
		    }
	
		    if (targetImg && targetImg.tagName === "IMG") {
		      const src = targetImg.src;
		      const fileName = src.substring(src.lastIndexOf("/") + 1);
	
		      // 1. 서버에 삭제 요청
		      fetch("/deleteImage.do", {
		        method: "POST",
		        headers: {
		          "Content-Type": "application/json"
		        },
		        body: JSON.stringify({ images: [fileName] })
		      });
	
		      // 2. uploadedImages 배열에서 제거
		      uploadedImages = uploadedImages.filter(name => name !== fileName);
			
		   	  // 3. hidden input도 제거
		      const inputs = document.querySelectorAll("input[name='imgid']");
		      inputs.forEach(input => {
		        if (input.value === fileName) {
		          input.remove();
		        }
		      });
		      
		      // 3. DOM에서 제거는 브라우저 기본 동작에 맡기기
		    }
		  }
		});
	  document.getElementById('imgInput').addEventListener('change', async function (e) {
		  const file = e.target.files[0];
		  if (!file || !file.type.startsWith('image/')) return;
	
		  const formData = new FormData();
		  formData.append("ofile", file); // 서버에서 받을 이름 맞춰야 해!
	
		  try {
		    // 서버에 업로드 요청
		    const response = await fetch("/uploadImage.do", {
		      method: "POST",
		      body: formData
		    });
	
		    if (!response.ok) {
		      alert("이미지 업로드에 실패했습니다.");
		      return;
		    }
	
		    const fileName = await response.text(); // 서버에서 업로드된 파일명 받아옴
		    uploadedImages.push(fileName);
		    
		 // ⭐⭐ hidden input 생성해서 form에 추가
		    const hiddenInput = document.createElement("input");
		    hiddenInput.type = "hidden";
		    hiddenInput.name = "imgid";
		    hiddenInput.value = fileName;
		    document.pWriteFrm.appendChild(hiddenInput);
		    
		    const imgTag = document.createElement("img");
		    imgTag.src = "/uploads/" + fileName; // ✅ 실제 이미지 경로
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
	
		    lastInsertedImage = imgTag;
		    insertedImages.push(imgTag);
		    e.target.value = ''; // 파일 선택 초기화
		  } catch (err) {
		    console.error("이미지 업로드 에러:", err);
		    alert("이미지 업로드 중 오류가 발생했습니다.");
		  }
		});
	
	
	  function saveCursor() {
	    const sel = window.getSelection();
	    if (sel.rangeCount > 0) {
	      savedRange = sel.getRangeAt(0).cloneRange();
	    }
	  }
	
	  // 이미지 업로드 트리거
	  window.triggerImageUpload = function () {
	    document.getElementById('imgInput').click();
	  };
	  
	  // 마지막 이미지 되돌리기
	  window.undoLastImage = function () {
	  const lastImage = insertedImages.pop(); // 마지막 삽입 이미지 꺼냄
	  if (lastImage && lastImage.parentNode) {
	    const src = lastImage.src;
	    const fileName = src.substring(src.lastIndexOf("/") + 1);
	
	    // 1. uploadedImages 배열에서 제거
	    uploadedImages = uploadedImages.filter(name => name !== fileName);
	
	    // 2. 서버에 삭제 요청
	    fetch("/deleteImage.do", {
	      method: "POST",
	      headers: {
	        "Content-Type": "application/json"
	      },
	      body: JSON.stringify({ images: [fileName] })
	    });
	
	    // 3. hidden input 제거
	    const inputs = document.querySelectorAll("input[name='imgid']");
	    inputs.forEach(input => {
	      if (input.value === fileName) {
	        input.remove();
	      }
	    });
	
	    // 4. DOM에서 이미지 제거
	    lastImage.parentNode.removeChild(lastImage);
	  }
	};
	
	window.triggerImageUpload = function () {
		  saveCursor(); // 커서 강제 저장!
		  document.getElementById('imgInput').click();
		};
		
	  window.addEventListener("beforeunload", function () {
		  console.log("beforeunload fired");
		  console.log("uploadedImages =", uploadedImages);
	
		  if (!document.getElementById("content").value && uploadedImages.length > 0) {
		    console.log("삭제 요청 시도!");
		    navigator.sendBeacon("/deleteImage.do", JSON.stringify({ images: uploadedImages }));
		  } else {
		    console.log("삭제 안함 조건 미충족");
		  }
		});
	
	  // 제출 유효성 검사
	  window.submitPost = function () {
		    const form = document.pWriteFrm;
		    console.log("title =", form.title.value);
		    console.log("tag =", form.tag.value);
		    console.log("country =", form.country.value);
		    const content = document.getElementById('editor').innerHTML.trim(); // innerHTML로 내용 추출
		    console.log("content =", content); // 확인용
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
	 
		    document.getElementById('content').value = content; // 최종적으로 content를 hidden input에 저장
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
