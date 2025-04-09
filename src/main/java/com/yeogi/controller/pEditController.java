package com.yeogi.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yeogi.dao.PostDAO;
import com.yeogi.dto.PostDTO;

/**
 * Servlet implementation class pEditController
 */
@WebServlet("/pEdit.do")
public class pEditController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public pEditController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int postID = Integer.parseInt(request.getParameter("postID"));  // URL에서 postID 가져옴

		PostDAO dao = new PostDAO();
		PostDTO pdto = dao.selectView(postID); // 해당 게시글 정보 가져오기

		request.setAttribute("pdto", pdto);  // JSP로 넘기기

		RequestDispatcher dispatcher = request.getRequestDispatcher("mini2/pEdit.jsp");
		dispatcher.forward(request, response);  // 수정 폼으로 이동
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
