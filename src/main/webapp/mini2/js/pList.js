/**
 * 
 */

document.addEventListener('DOMContentLoaded', function() {
	const headLink = document.getElementById("toIndexLink");
	headLink.addEventListener('click',function(event){
		window.location.href = "/mini2/index.jsp";
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