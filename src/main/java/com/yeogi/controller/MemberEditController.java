package com.yeogi.controller;

import java.io.IOException;
<<<<<<< HEAD

=======
>>>>>>> c49aa62ec57d600ee34e4519f5dbab4618e1839c
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
<<<<<<< HEAD
=======
import javax.servlet.http.HttpSession;
>>>>>>> c49aa62ec57d600ee34e4519f5dbab4618e1839c

import com.yeogi.dao.MemberDAO;
import com.yeogi.dto.MemberDTO;

<<<<<<< HEAD
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
=======
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
>>>>>>> c49aa62ec57d600ee34e4519f5dbab4618e1839c
		RequestDispatcher dispatcher = request.getRequestDispatcher("/mini2/memberEdit.jsp");
		dispatcher.forward(request, response);
	}

<<<<<<< HEAD
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8"); // 한글 깨짐을 방지
		// 폼에서 입력한 회원 정보 얻어오기
=======
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
>>>>>>> c49aa62ec57d600ee34e4519f5dbab4618e1839c
		String name = request.getParameter("name");
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");	
		String phonenum = request.getParameter("phonenum");	
		
<<<<<<< HEAD
		// 회원 정보를 저장할 Model객체 생성
=======
>>>>>>> c49aa62ec57d600ee34e4519f5dbab4618e1839c
		MemberDTO mVo = new MemberDTO();
		mVo.setName(name);
		mVo.setId(id);
		mVo.setPw(pw);
		mVo.setPhonenum(phonenum);
		
<<<<<<< HEAD
		DBConnPool db = new DBConnPool();  // DBConnPool 객체 만들기
		MemberDAO mDao = MemberDAO.getInstance();
	   
	    
		
	    try {
	    	mDao.updateMember(mVo);
	    	
	    	// Sau khi updateMember
	    	MemberDTO updatedMember = mDao.getMember(id); // Lấy lại thông tin mới nhất
	    	request.getSession().setAttribute("loginUser", updatedMember); // Cập nhật lại loginUser trong session
	    	
	    } finally {
	        db.close();  // 실행 후 connect 닫기
	    }	
		
		response.sendRedirect("login.do");
	}

}
=======
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
>>>>>>> c49aa62ec57d600ee34e4519f5dbab4618e1839c
