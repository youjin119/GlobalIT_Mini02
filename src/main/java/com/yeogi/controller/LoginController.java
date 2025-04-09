package com.yeogi.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.yeogi.dao.MemberDAO;
import com.yeogi.dto.MemberDTO;



/**
 * Servlet implementation class LoginController
 */
@WebServlet("/login.do")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url="/mini2/login.jsp";
		HttpSession session = request.getSession(); // session객체 구하기
		if (session.getAttribute("loginUser") != null) {// 이미 로그인 된 사용자이면
			url = "main.do"; // 메인 페이지로 이동한다.
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response); //주소가 변경되지 않음
	}
		

		

	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String url = "/mini2/login.jsp";
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		
		MemberDAO mDao = MemberDAO.getInstance();
		int result = mDao.userCheck(id, pw);
		
		if (result == 1) { //id, pass일치할 때
			MemberDTO mVo = mDao.getMember(id);			
			HttpSession session = request.getSession();			
			session.setAttribute("loginUser",mVo);
			
			
			url = "main.do";
			response.sendRedirect(url);//주소변경
			
		} else if (result == 0) { //pass 일치하지 않을때
			request.setAttribute("message", "비밀번호가 맞지 않습니다.");
			RequestDispatcher dispatcher = request.getRequestDispatcher(url);
			dispatcher.forward(request, response);
		} else if (result == -1) { //id 존재하지 않을때
			request.setAttribute("message", "존재하지 않는 회원입니다.");
			RequestDispatcher dispatcher = request.getRequestDispatcher(url);
			dispatcher.forward(request, response);
		}
		
//dieu chinh
	}
		
	

}