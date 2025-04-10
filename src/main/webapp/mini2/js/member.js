
//---------------화원가입 부분--------------------------------//
//----------------------------------------------------------//

//ID 칸에 focus 하면 "ID 이미 존재" 메시지 사라지기
document.addEventListener("DOMContentLoaded", function () {
  let emailInput = document.querySelector("#idName");
  let messageP = document.querySelector(".isID");

  if (emailInput && messageP) {
      emailInput.addEventListener("focus", function () {
          messageP.style.display = "none"; // 
      });
  }
});

//input 칸에 forcus 하면 안내 메시지 나타나기
document.addEventListener("DOMContentLoaded", function() {


let passwordInput = document.querySelector("#password");
let passwordConfirmInput = document.querySelector("#passwordConfirm"); 
let telInput = document.querySelector("#tel");



let mes2 = document.querySelectorAll(".mes2");
let mes3 = document.querySelector(".mes3");  
let mes5 = document.querySelector(".mes5");


if (passwordInput && mes2) {
  passwordInput.addEventListener("focus", function() {
    mes2.forEach(mes => mes.style.display = "block"); 
  }); 
}

if (passwordConfirmInput && mes3) {
  passwordConfirmInput.addEventListener("focus", function() {
    mes3.style.display = "block";
  });   
}


if (telInput && mes5) {
  telInput.addEventListener("focus", function() {
    mes5.style.display = "block";
  });    
}
});

//회원가입 입력 조건 생성 
function joinCheck() {
  var form = document.frm;
  var isValid = true;

  // 기본 에러 메시지 삭제
  document.querySelectorAll(".error-message").forEach(el => el.textContent = "");

  var emailInputElement = form.idName;
  var email = emailInputElement.value.trim();
  
  
  var password = form.password.value.trim();
  var passwordConfirm = form.passwordConfirm.value.trim();
  var name = form.name.value.trim();
  var phone = form.phonenum.value.trim();

  // 이메일 조건 체크
  if (!emailInputElement.readOnly) {
      var emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
      if (!email) {
          showError("error-idName", "이메일을 입력해주세요.");
          isValid = false;
      } else if (!emailRegex.test(email)) {
          showError("error-idName", "올바른 이메일 형식을 입력해주세요. (예: abc@gmail.com)");
          isValid = false;
      }
  }
  
  
  // 비밀번호 조건 체크 
   if (password.length < 4 || password.length > 20) {
       showError("error-password", "비밀번호는 4~20자 사이로 입력해주세요.");
       isValid = false;
   }
  
  
  // 비밀번호 다시 확인
  if (password !== passwordConfirm) {
      showError("error-passwordConfirm", "비밀번호 확인이 일치하지 않습니다.");
      isValid = false;
  }

  //이름 조건
  if (!name) {
      showError("error-name", "이름을 입력해주세요.");
      isValid = false;
  } else if (name.length > 40) {
      showError("error-name", "이름은 40자 이하로 입력해주세요.");
      isValid = false;
  }

  // 전화번호 정보 조건
  var phoneRegex = /^010-\d{3,4}-\d{4}$/;
  if (!phoneRegex.test(phone)) {
      showError("error-tel", "휴대폰 번호는 '010-XXXX-XXXX' 형식으로 입력해주세요.");
      isValid = false;
  }

  return isValid;
}

// 조건에 맞지 않으면 error 메시지 보여주기
function showError(errorId, message) {
  var errorSpan = document.getElementById(errorId);
  if (errorSpan) {
      errorSpan.textContent = message;
      errorSpan.style.color = "red"; // 빨깐 색으로 표시
  }
}

//---------------ID, 비밀번호 찾기--------------------------------//
//----------------------------------------------------------//

//ID 찾기
  function findId() {
	  console.log("findId() called!");
    const phone = $('#findPhone').val().trim();

    if (phone === "") {
      $('#foundIdMsg').text("전화번호를 입력해주세요.");
      return;
    }

    $.ajax({
      type: 'POST',
      url: '/findId.do',
      data: { phonenum: phone },
      success: function (response) {
        const id = response.id;
        if (id) {
          $('#foundIdMsg').html("회원님의 아이디는 <strong>" + id + "</strong> 입니다.");
        } else {
          $('#foundIdMsg').text("해당 전화번호로 등록된 아이디가 없습니다.");
        }
      },
      error: function () {
        $('#foundIdMsg').text("서버 오류. 다시 시도해주세요.");
      }
    });
  }
  
// 새비밀번호 설정
  function resetPassword() {
    const id = $("#resetId").val();
    const phone = $("#resetPhone").val();
    const newPw = $("#newPassword").val();

    $.ajax({
      type: "POST",
      url: "/resetPw.do",
      data: { id: id, phonenum: phone, newPw: newPw },
      success: function(res) {
        $("#resetMsg").text("비밀번호가 성공적으로 변경되었습니다.").css("color", "green");
      },
      error: function() {
        $("#resetMsg").text("정보가 일치하지 않습니다.").css("color", "red");
      }
    });
  }




