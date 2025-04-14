package com.yeogi.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.yeogi.dao.ImglikeDAO;
import com.yeogi.dao.PostDAO;
import com.yeogi.dto.MemberDTO;
import com.yeogi.dto.PostDTO;


/**
 * Servlet implementation class pViewController
 */
@WebServlet("/pView.do")
public class pViewController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public pViewController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 게시물 불러오기
		PostDAO pdao = new PostDAO();
		int postID = Integer.parseInt(request.getParameter("postID"));
		pdao.updateVcount(postID);  // 조회수 1 증가
		PostDTO pdto = pdao.selectView(postID);
		/*
		 * System.out.println("받아온 게시물 제목: " + pdto.getTitle());
		 * System.out.println("받아온 게시물 내용: " + pdto.getContent());
		 */
		pdao.close();

		// 줄바꿈 처리
		pdto.setContent(pdto.getContent().replaceAll("\r\n", "<br/>"));
		
		
		// 💡 페이지 번호 받아서 저장
		String pageNum = request.getParameter("pageNum");
		request.setAttribute("pageNum", pageNum);
		/* System.out.println("넘어온 pageNum = " + pageNum); */
		String tag = request.getParameter("tag");
		request.setAttribute("tag", tag);
		
		// 💡 세션에서 loginUser 가져와서 JSP에 넘기기
		HttpSession session = request.getSession();
		MemberDTO loginUser = (MemberDTO) session.getAttribute("loginUser");
		request.setAttribute("loginUser", loginUser);
		
		// 게시물(dto) 저장 후 뷰로 포워드
		request.setAttribute("pdto", pdto);
		
		// 💡 좋아요 수 조회해서 JSP에 전달
		ImglikeDAO ImglikeDAO = new ImglikeDAO();
		int likeCount = ImglikeDAO.getLikeCount(postID);
		request.setAttribute("likeCount", likeCount);
		// 💡 로그인한 경우 → 해당 사용자가 좋아요 눌렀는지 여부 확인
				boolean userLiked = false;
				if (loginUser != null) {
					String userId = loginUser.getId();
					userLiked = ImglikeDAO.isLiked(userId, postID);
				}
				request.setAttribute("userLiked", userLiked);
		ImglikeDAO.close(); // DAO에 close() 있으면 호출
		
		request.getRequestDispatcher("mini2/pView.jsp").forward(request, response);

		// ------------------------------------------

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}