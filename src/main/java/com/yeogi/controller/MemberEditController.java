package com.yeogi.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yeogi.dao.MemberDAO;
import com.yeogi.dto.MemberDTO;

import conn.DBConnPool;

/**
 * Servlet implementation class MemberEditController
 */
@WebServlet("/memberEdit.do")
public class MemberEditController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberEditController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		MemberDAO mDao = MemberDAO.getInstance();
		MemberDTO mVo = mDao.getMember(id);
		request.setAttribute("mVo", mVo);// view로 mVo전달 
		RequestDispatcher dispatcher = request.getRequestDispatcher("/mini2/memberEdit.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8"); // 한글 깨짐을 방지
		// 폼에서 입력한 회원 정보 얻어오기
		String name = request.getParameter("name");
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");	
		String phonenum = request.getParameter("phonenum");	
		
		// 회원 정보를 저장할 Model객체 생성
		MemberDTO mVo = new MemberDTO();
		mVo.setName(name);
		mVo.setId(id);
		mVo.setPw(pw);
		mVo.setPhonenum(phonenum);
		
		DBConnPool db = new DBConnPool();  // DBConnPool 객체 만들기
		MemberDAO mDao = MemberDAO.getInstance();
	   
	    
	    try {
	    	mDao.updateMember(mVo);
	    } finally {
	        db.close();  // 실행 후 connect 닫기
	    }	
		
		response.sendRedirect("login.do");
	}

}
