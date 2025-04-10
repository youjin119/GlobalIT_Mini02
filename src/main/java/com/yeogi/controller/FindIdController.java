package com.yeogi.controller;

import com.yeogi.dao.MemberDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/findId.do")
public class FindIdController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        String phonenum = request.getParameter("phonenum");
        System.out.println(phonenum);

        MemberDAO dao = MemberDAO.getInstance();
        String foundId = dao.findIdByPhone(phonenum);
        System.out.println(foundId);

        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write("{\"id\":\"" + (foundId != null ? foundId : "") + "\"}");
    }
}