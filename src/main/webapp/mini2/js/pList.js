/**
 * 
 */

document.addEventListener('DOMContentLoaded', function() {
	viewLinksEventListeners();
	sidePanelLinksEventListeners();
	changeTagColor(selectedTag);
});





// 태그 글자 색 변경 함수
function changeTagColor(selectedTag) {
    if (selectedTag) {
        const tagElements = document.querySelectorAll(".tagsContainer li a");
        tagElements.forEach(tagElement => {
            if (tagElement.textContent === selectedTag) {
                tagElement.style.color = "#78C8BA";
            }
        });
    }
}

// 보드 이벤트 리스너 등록 함수
function viewLinksEventListeners() {
    const viewLinks = document.querySelectorAll(".viewSelector");
	//console.log(viewLinks);
    viewLinks.forEach(function(viewLink) {
        viewLink.addEventListener('click', function(event) {
            const idx = this.id.replace("viewLink", '');
            const viewForm = document.getElementById('viewForm' + idx);
            viewForm.submit();
        });
    });
}
// 사이드 패널 이벤트 리스너
function sidePanelLinksEventListeners() {
    const sPLinks = document.querySelectorAll(".aPSelector");
	//console.log(sPLinks);
    sPLinks.forEach(function(sPLink) {
        sPLink.addEventListener('click', function(event) {
            const idx = this.id.replace("aPLink", '');
            const aPForm = document.getElementById('aPForm' + idx);
            aPForm.submit();
        });
    });
}
