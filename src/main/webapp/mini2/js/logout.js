
document.addEventListener('DOMContentLoaded', function() {
	
	 // a 태그의 href속성까지 같이 검색
	const adminSelector = document.querySelector('a.dropdown-item[href="/pWrite.do"]');
	if (isadmin==0) {
	    console.log(userId+" : "+isadmin); // userId 값 출력
	    adminSelector.remove();
	} else {
	    console.log(userId+" : "+isadmin); // userId 값 출력
	    console.log("관리자입니다.");
	}
	
	
});