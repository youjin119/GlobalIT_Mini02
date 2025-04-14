package com.yeogi.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

		 HttpSession session = request.getSession();
	        MemberDTO loginUser = (MemberDTO) session.getAttribute("loginUser");
	        
	        response.setContentType("application/json");
	        response.setCharacterEncoding("UTF-8");
	        
	        int postID = Integer.parseInt(request.getParameter("postID"));

	        JSONObject json = new JSONObject();

	        if (loginUser == null) {
	            // 로그인 안 한 경우 → redirect URL 포함 응답
	            String redirectURL = "/login.do?redirectURL=/pView.do?postID=" + postID;
	            json.put("redirect", redirectURL);
	            response.getWriter().write(json.toString());
	            return;
	        }

	        String userId = loginUser.getId();

	        ImglikeDAO likeDAO = new ImglikeDAO();
	        boolean isLiked = likeDAO.isLiked(userId, postID);

	        if (isLiked) {
	            // 이미 좋아요 눌렀으면 취소
	            likeDAO.removeLike(userId, postID);
	            json.put("isLiked", false);
	        } else {
	            // 좋아요 추가
	            likeDAO.addLike(userId, postID);
	            json.put("isLiked", true);
	        }

	        int likeCount = likeDAO.getLikeCount(postID);
	        json.put("likeCount", likeCount);

	        likeDAO.close();
	        response.getWriter().write(json.toString());
	}
}