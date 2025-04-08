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
	    
	    String adminid = request.getParameter("adminid");  // Nhận giá trị adminid từ request
	    String jdate = request.getParameter("jdate");
	    	    	    
	   
	    // Tạo đối tượng model (VO)
	    MemberDTO mVo = new MemberDTO();

	    // Kiểm tra nếu adminid là null hoặc rỗng, gán giá trị mặc định là 0
	    int admin = 0;  // Giá trị mặc định là 0
	    if (adminid != null && !adminid.trim().isEmpty()) {
	        admin = Integer.parseInt(adminid);  // Nếu có giá trị, chuyển thành int
	    }

	    // Thiết lập các giá trị vào mVo
	    mVo.setId(id);
	    mVo.setPw(pw);
	    mVo.setName(name);
	    mVo.setPhonenum(phonenum);
	    mVo.setAdminid(admin);  // Sử dụng giá trị admin đã kiểm tra

	    // Lấy ngày hiện tại
	    Calendar calendar = Calendar.getInstance();  // Lấy ngày giờ hiện tại
	    Date currentDate = new Date(calendar.getTimeInMillis());  // Chuyển đổi sang java.sql.Date

	    mVo.setJdate(currentDate);  // Thiết lập ngày vào mVo

	    DBConnPool db = new DBConnPool();  // Tạo đối tượng DBConnPool
	    MemberDAO mDao = MemberDAO.getInstance();// Gọi phương thức insertMember để thêm dữ liệu vào cơ sở dữ liệu
	   
	 // Kiểm tra ID đã tồn tại hay chưa
	    if (mDao.isIdExist(id)) {
	        request.setAttribute("message", "ID 이미 존재하고 있습니다, 다른 걸로 입력해주세요!");
	        RequestDispatcher dispatcher = request.getRequestDispatcher("/mini2/register.jsp");
	        dispatcher.forward(request, response);
	        return; // Dừng việc thực hiện tiếp
	    }
	    
	    
	    int result = -1;
	    try {
	        result = mDao.insertMember(mVo); 
	    } finally {
	        db.close();  // Đóng kết nối sau khi hoàn thành
	    }
	    

	    HttpSession session = request.getSession();  // Lấy session
	    if (result == 1) {
	        session.setAttribute("id", mVo.getId());
	        request.setAttribute("message", "회원 가입에 성공했습니다.");
	    } else {
	        request.setAttribute("message", "회원 가입에 실패했습니다.");
	    }

	    // Chuyển hướng đến trang login.jsp
	    RequestDispatcher dispatcher = request.getRequestDispatcher("/mini2/login.jsp");
	    dispatcher.forward(request, response);
	}

}
