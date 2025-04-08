package com.yeogi.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yeogi.dao.PostDAO;
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
		System.out.println("받아온 게시물 제목: " + pdto.getTitle());
		System.out.println("받아온 게시물 내용: " + pdto.getContent());
		pdao.close();

		// 줄바꿈 처리
		pdto.setContent(pdto.getContent().replaceAll("\r\n", "<br/>"));


		// 게시물(dto) 저장 후 뷰로 포워드
		request.setAttribute("pdto", pdto);
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
