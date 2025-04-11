/**
 * 
 */

document.addEventListener('DOMContentLoaded', function() {
	

	viewLinksEventListeners();
	changeTagColor(selectedTag);
});





// 태그 글자 색 변경 함수
function changeTagColor(selectedTag) {
    if (selectedTag) {
        const tagElements = document.querySelectorAll(".tagsContainer li a");
        tagElements.forEach(tagElement => {
            if (tagElement.textContent === selectedTag) {
                tagElement.style.color = "red";
            }
        });
    }
}

// 이벤트 리스너 등록 함수 (필요한 경우 구현)
function viewLinksEventListeners() {
    // 필요한 이벤트 리스너 재등록 코드 작성
    // 예: viewLinks 등에 대한 이벤트 리스너 재등록
    const viewLinks = document.querySelectorAll(".viewSelector");
	console.log(viewLinks);
    viewLinks.forEach(function(viewLink) {
        viewLink.addEventListener('click', function(event) {
            const idx = this.id.replace("viewLink", '');
            const viewForm = document.getElementById('viewForm' + idx);
            viewForm.submit();
        });
    });
}
