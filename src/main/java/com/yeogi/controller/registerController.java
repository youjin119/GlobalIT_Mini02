package com.yeogi.controller;

import java.io.IOException;
import java.sql.Date;
import java.util.Calendar;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.yeogi.dao.MemberDAO;
import com.yeogi.dto.MemberDTO;

import conn.DBConnPool;



/**
 * Servlet implementation class JoinServlet
 */
@WebServlet("/register.do")
public class registerController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public registerController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("mini2/register.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		String name = request.getParameter("name");
		String phonenum = request.getParameter("phonenum");
		String adminid = request.getParameter("adminid");

		// Debug
		System.out.println("id: " + id);
		System.out.println("pw: " + pw);
		System.out.println("name: " + name);
		System.out.println("Phone number: " + phonenum);

		// Set default adminid = 0 if not given
		int admin = 0;
		if (adminid != null && !adminid.trim().isEmpty()) {
			admin = Integer.parseInt(adminid);
		}

		// Set current date
		Calendar calendar = Calendar.getInstance();
		Date currentDate = new Date(calendar.getTimeInMillis());

		// Create VO
		MemberDTO mVo = new MemberDTO();
		mVo.setId(id);
		mVo.setPw(pw);
		mVo.setName(name);
		mVo.setPhonenum(phonenum);
		mVo.setAdminid(admin);
		mVo.setJdate(currentDate);

		MemberDAO mDao = new MemberDAO();  
		int result = -1;

		try {
			// Check if ID already exists
			if (mDao.isIdExist(id)) {
				request.setAttribute("message", "ID 이미 존재하고 있습니다, 다른 걸로 입력해주세요!");
				RequestDispatcher dispatcher = request.getRequestDispatcher("/mini2/register.jsp");
				dispatcher.forward(request, response);
				return;
			}

			// Insert new member
			result = mDao.insertMember(mVo);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			mDao.close(); //DB 지원 반납
		}

		// Result 처리
		HttpSession session = request.getSession();
		if (result == 1) {
			session.setAttribute("id", mVo.getId());
			request.setAttribute("message", "회원 가입에 성공했습니다. 로그인 해주세요");
		} else {
			request.setAttribute("message", "회원 가입에 실패했습니다.");
		}

		// 이동
		RequestDispatcher dispatcher = request.getRequestDispatcher("/mini2/login.jsp");
		dispatcher.forward(request, response);
	}
}
