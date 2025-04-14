package com.yeogi.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yeogi.dao.PostDAO;

/**
 * Servlet implementation class pDeleteController
 */
@WebServlet("/pDelete.do")
public class pDeleteController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public pDeleteController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		  String postID = request.getParameter("postID");

	        if (postID != null && !postID.isEmpty()) {
	            try {
	                int pid = Integer.parseInt(postID);
	                String uploadPath = request.getServletContext().getRealPath("/uploads");
	                
	                PostDAO pdao = new PostDAO();
	                pdao.deletePost(pid,uploadPath);
	                pdao.close();
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }
	        response.sendRedirect("/pList.do");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}