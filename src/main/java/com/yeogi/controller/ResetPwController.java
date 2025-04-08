package com.yeogi.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yeogi.dao.MemberDAO;

@WebServlet("/resetPw.do")
public class ResetPwController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String id = request.getParameter("id");
        String phonenum = request.getParameter("phonenum");
        String newPw = request.getParameter("newPw");

        MemberDAO dao = MemberDAO.getInstance();  // Lấy instance của DAO

        try {
            // Gọi phương thức dao để đặt lại mật khẩu
            boolean success = dao.resetPassword(id, phonenum, newPw);

            // Trả về kết quả
            if (success) {
                response.setStatus(200);  // Thành công
            } else {
                response.setStatus(400); // Không cập nhật được mật khẩu
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(500);  // Lỗi server
        }
    }
}