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

        MemberDAO dao = new MemberDAO();  

        try {
            boolean success = dao.resetPassword(id, phonenum, newPw);

            if (success) {
                response.setStatus(200);  // 성공
            } else {
                response.setStatus(400);  //  ID or 전화번호 불일치
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(500);  // 서버 오류
        } finally {
            dao.close();  // DB 지원 반납
        }
    }
}
