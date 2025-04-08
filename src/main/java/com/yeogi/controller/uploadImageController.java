package com.yeogi.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.FileUtil;

/**
 * Servlet implementation class uploadImageController
 */
@WebServlet("/uploadImage.do")
@MultipartConfig(maxFileSize = 1024 * 1024 * 1, // 1mbyte ==1024kb == 1024
maxRequestSize = 1024 * 1024 * 10 // 10mbyte
)
public class uploadImageController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public uploadImageController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String saveDir = request.getServletContext().getRealPath("/uploads");

		// 1. 업로드
		String originalFileName = FileUtil.uploadFile(request, saveDir);

		// 2. 리네임
		String newFileName = FileUtil.renameFile(saveDir, originalFileName);

		// 3. 리턴
		response.setContentType("text/plain; charset=UTF-8");
		response.getWriter().write(newFileName); // 파일명만 리턴!
	}

}
