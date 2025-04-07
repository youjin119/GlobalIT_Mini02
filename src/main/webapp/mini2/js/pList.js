/**
 * 
 */

document.addEventListener('DOMContentLoaded', function() {
	
	const headLink = document.getElementById("toIndexLink");
	headLink.addEventListener('click',function(event){
		window.location.href = "/mini2/index.jsp";
	});
	const tagSelectors = document.querySelectorAll('tagSelector');
	console.log("js가 실행중");
	    tagSelectors.forEach(function(tagSelector) {
	        tagSelector.addEventListener('click', function() {
				console.log("클릭됨",this);
/*	            // 모든 tagSelector에서 tagSelected 클래스 제거
	            tagSelectors.forEach(function(ts) {
	                ts.classList.remove('tagSelected');
	            });*/

	            // 클릭된 tagSelector에 tagSelected 클래스 추가
	            this.classList.add('tagSelected');
	        });
	    });
	
	
	const viewLinks = document.querySelectorAll(".viewSelector");
	
	viewLinks.forEach(function(viewLink){
	    viewLink.addEventListener('click', function(event) {
			const idx = this.id.replace("viewLink",'');
		    const viewForm = document.getElementById('viewForm'+idx);
	        viewForm.submit();
	    });		
		
	});

});