package com.yeogi.controller;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.yeogi.dao.MemberDAO;
import com.yeogi.dto.MemberDTO;

@WebServlet("/memberEdit.do")
public class MemberEditController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public MemberEditController() {
        super();
    }

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");

		MemberDAO mDao = new MemberDAO();  
		MemberDTO mVo = null;
		
		try {
			mVo = mDao.getMember(id);
		} finally {
			mDao.close();  //DB 지원 반납
		}
		
		request.setAttribute("mVo", mVo); 
		RequestDispatcher dispatcher = request.getRequestDispatcher("/mini2/memberEdit.jsp");
		dispatcher.forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		String name = request.getParameter("name");
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");	
		String phonenum = request.getParameter("phonenum");	
		
		MemberDTO mVo = new MemberDTO();
		mVo.setName(name);
		mVo.setId(id);
		mVo.setPw(pw);
		mVo.setPhonenum(phonenum);
		
		MemberDAO mDao = new MemberDAO();  
		try {
			mDao.updateMember(mVo);
			
			// 최신 정보 얻기
			MemberDTO updatedMember = mDao.getMember(id); 
			HttpSession session = request.getSession();
			session.setAttribute("loginUser", updatedMember);
			
		} finally {
			mDao.close();  // DB 지원 반납
		}
		
		response.sendRedirect("login.do");
	}
}
