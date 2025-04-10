/**
 * 
 */

document.addEventListener('DOMContentLoaded', function() {
	
	const headLink = document.getElementById("toIndexLink");
	headLink.addEventListener('click',function(event){
		window.location.href = "/mini2/index.jsp";
	});
	const tagSelectors = document.querySelectorAll('.tagSelector');
	console.log(tagSelectors);
	    tagSelectors.forEach(function(tagSelector) {
	        tagSelector.addEventListener('click', function() {
				console.log("클릭됨",this);
	            // 모든 tagSelector에서 tagSelected 클래스 제거
	            tagSelectors.forEach(function(ts) {
	                ts.classList.remove('tagSelected');
	            });
			
	            // 클릭된 tagSelector에 tagSelected 클래스 추가
	            this.classList.add('tagSelected');
				
				// boardLists 새로 가져오기
				fetchBoardLists(this.textContent); // 클릭된 태그의 텍스트를 인자로 전달
	        });
	    });
	
	viewLinksEventListeners();

});



// boardLists를 새로 가져오는 함수
function fetchBoardLists(tag) {
    fetch('/pList.do', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: 'tag=' + encodeURIComponent(tag) // 클릭된 태그를 서버에 전달
    })
    .then(response => response.text())
    .then(data => {
        // 서버에서 받은 데이터를 처리하여 boardLists를 업데이트
        const parser = new DOMParser();
        const doc = parser.parseFromString(data, 'text/html');
        const newBoardLists = doc.querySelector('.boardListsContainer'); // 게시물 목록을 감싸는 컨테이너 클래스
        const oldBoardLists = document.querySelector('.boardListsContainer');
        oldBoardLists.innerHTML = newBoardLists.innerHTML;
        // 업데이트 후 필요한 JavaScript 코드 재실행 (예: 이벤트 리스너 재등록)
        viewLinksEventListeners();
    })
    .catch(error => {
        console.error('Error:', error);
    });
}

// 이벤트 리스너 재등록 함수 (필요한 경우 구현)
function viewLinksEventListeners() {
    // 필요한 이벤트 리스너 재등록 코드 작성
    // 예: viewLinks 등에 대한 이벤트 리스너 재등록
    const viewLinks = document.querySelectorAll(".viewSelector");

    viewLinks.forEach(function(viewLink) {
        viewLink.addEventListener('click', function(event) {
            const idx = this.id.replace("viewLink", '');
            const viewForm = document.getElementById('viewForm' + idx);
            viewForm.submit();
        });
    });
}

