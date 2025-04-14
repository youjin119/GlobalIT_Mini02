$(document).ready(function(){
    $(".like-btn").click(function(e){
        e.preventDefault();

        // 로그인 안 된 사용자는 <a> 태그 → 그냥 링크로 이동
        if ($(this).is("a")) {
            window.location.href = $(this).attr("href");
            return;
        }

        var postID = $(this).siblings("input[name='postID']").val();
        var button = $(this);

        $.post("/pLike.do", { postID: postID }, function(response){
            if (response.redirect) {
                window.location.href = response.redirect;
                return;
            }

            if (response.isLiked) {
                button.addClass("liked").html("❤️");
            } else {
                button.removeClass("liked").html("🤍");
            }

            var likeCountSpan = button.closest(".top-icons").find(".like-count");
            likeCountSpan.text(response.likeCount);
        }, "json");
    });
});