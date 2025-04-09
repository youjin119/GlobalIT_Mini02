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
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public registerController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("mini2/register.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String id = request.getParameter("id");
	    String pw = request.getParameter("pw");
	    String name = request.getParameter("name");
	    String phonenum = request.getParameter("phonenum");	 
	    
	    System.out.println("id: " + id);  // Debugging
	    System.out.println("pw: " + pw);  // Debugging
	    System.out.println("nane: " + name);  // Debugging
	    System.out.println("Phone number: " + phonenum);  // Debugging
	    
	    String adminid = request.getParameter("adminid"); 
	    String jdate = request.getParameter("jdate");
	    	    	    
	   
	    //  model (VO) 생성
	    MemberDTO mVo = new MemberDTO();

	    //  adminid가 null이면 기본값 0으로 설정
	    int admin = 0;  // Giá trị mặc định là 0
	    if (adminid != null && !adminid.trim().isEmpty()) {
	        admin = Integer.parseInt(adminid);  // 값이 있으면, int으로 바꿈
	    }

	    //  mVo 값 설정
	    mVo.setId(id);
	    mVo.setPw(pw);
	    mVo.setName(name);
	    mVo.setPhonenum(phonenum);
	    mVo.setAdminid(admin);  //체크된 admin 값 사용

	    // 현재 Date 받기
	    Calendar calendar = Calendar.getInstance();  // 
	    Date currentDate = new Date(calendar.getTimeInMillis());  // java.sql.Date로 전환

	    mVo.setJdate(currentDate);  //  mVo에 Date 설정

	    DBConnPool db = new DBConnPool();  
	    MemberDAO mDao = MemberDAO.getInstance();//insertMember 불러, data 추가
	   
	 // ID 존재 여부 확인
	    if (mDao.isIdExist(id)) {
	        request.setAttribute("message", "ID 이미 존재하고 있습니다, 다른 걸로 입력해주세요!");
	        RequestDispatcher dispatcher = request.getRequestDispatcher("/mini2/register.jsp");
	        dispatcher.forward(request, response);
	        return; 
	    }
	    
	    
	    int result = -1;
	    try {
	        result = mDao.insertMember(mVo); 
	    } finally {
	        db.close();  
	    }
	    

	    HttpSession session = request.getSession();  //  session 꺼내기
	    if (result == 1) {
	        session.setAttribute("id", mVo.getId());
	        request.setAttribute("message", "회원 가입에 성공했습니다.");
	    } else {
	        request.setAttribute("message", "회원 가입에 실패했습니다.");
	    }

	    //  login.jsp 페이지로 이동
	    RequestDispatcher dispatcher = request.getRequestDispatcher("/mini2/login.jsp");
	    dispatcher.forward(request, response);
	}

}
