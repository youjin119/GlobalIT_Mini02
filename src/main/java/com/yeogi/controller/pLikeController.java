package com.yeogi.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.yeogi.dao.ImglikeDAO;
import com.yeogi.dto.MemberDTO;

/**
 * Servlet implementation class pLikeController
 */
@WebServlet("/pLike.do")
public class pLikeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public pLikeController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
	        // 로그인된 사용자 확인
	        MemberDTO loginUser = (MemberDTO) request.getSession().getAttribute("loginUser");
	        if (loginUser == null) {
	            // 로그인되지 않은 경우 로그인 페이지로 리다이렉트
	            response.sendRedirect("/login.do?redirectURL=" + request.getRequestURL());
	            return;
	        }

	        String userId = loginUser.getId(); // 로그인된 사용자의 ID 가져오기
	        int postID = Integer.parseInt(request.getParameter("postID")); // 좋아요를 누를 게시글 ID

	        // 좋아요 상태 확인 및 좋아요 개수 가져오기
	        ImglikeDAO imgLikeDAO = new ImglikeDAO();
	        boolean isLiked = imgLikeDAO.isLiked(userId, postID); // 사용자가 이미 좋아요를 눌렀는지 확인

	        // 좋아요 취소 또는 추가 처리
	        if (isLiked) {
	            // 좋아요 취소
	            imgLikeDAO.removeLike(userId, postID);
	        } else {
	            // 좋아요 추가
	            imgLikeDAO.addLike(userId, postID);
	        }

	        // 좋아요 수를 최신화
	        int likeCount = imgLikeDAO.getLikeCount(postID);
	        imgLikeDAO.close(); // DB 연결 닫기

	        // JSON 응답으로 좋아요 상태와 좋아요 수를 보내기
	        JSONObject jsonResponse = new JSONObject();
	        jsonResponse.put("isLiked", !isLiked); // 반전된 좋아요 상태
	        jsonResponse.put("likeCount", likeCount);

	        response.setContentType("application/json");
	        response.getWriter().write(jsonResponse.toString());
	    } catch (Exception e) {
	        // 예외 처리: 오류 메시지 로그 또는 500 에러 페이지로 리디렉션
	        e.printStackTrace(); // 예외 로그 출력 (개발 단계에서 유용)
	        //response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "서버 오류 발생");
	    }
	}
}