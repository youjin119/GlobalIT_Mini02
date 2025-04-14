$(document).ready(function(){
       $(".like-btn").click(function(e){
           e.preventDefault(); // 기본 폼 제출 방지

           var postID = $(this).siblings("input[name='postID']").val(); // postID 가져오기
           var button = $(this);
           
           $.post("/pLike.do", { postID: postID }, function(response){
               // 좋아요 상태가 바뀌었으면 버튼의 텍스트를 업데이트
               console.log(response);
               if (response.isLiked) {
                   button.addClass("liked");
                   button.html("❤️"); // 좋아요 상태로 변경
               } else {
                   button.removeClass("liked");
                   button.html("🤍"); // 좋아요 취소 상태로 변경
               }

               // 좋아요 수를 갱신
               $(".like-count").text(response.likeCount);
           });
       });
   });