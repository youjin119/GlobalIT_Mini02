package com.yeogi.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.yeogi.dao.ImglikeDAO;
import com.yeogi.dao.PostDAO;
import com.yeogi.dto.ImglikeDTO;
import com.yeogi.dto.MemberDTO;
import com.yeogi.dto.PostDTO;

/**
 * Servlet implementation class pWriteController
 */
@WebServlet("/pWrite.do")

public class pWriteController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public pWriteController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("mini2/pWrite.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//request.setCharacterEncoding("UTF-8");
		
		java.util.Enumeration<String> params = request.getParameterNames();
		while (params.hasMoreElements()) {
		    String paramName = params.nextElement();
		    System.out.println("Parameter Name: " + paramName + ", Value: " + request.getParameter(paramName));
		}
		
		String title = request.getParameter("title");
        String tag = request.getParameter("tag");
        String country = request.getParameter("country");
        String content = request.getParameter("content");

        System.out.println("title = " + title);  // 확인용
        System.out.println("tag = " + tag);      // 확인용
        System.out.println("country = " + country);  // 확인용
        System.out.println("content = " + content);  // 확인용
        
        HttpSession session = request.getSession();
        MemberDTO loginUser = (MemberDTO) session.getAttribute("loginUser");

        if (loginUser == null) {
            response.sendRedirect("mini2/login.jsp");
            return;
        }

        String id = loginUser.getId(); // 이제 id가 null 아님!
        
        PostDTO pdto = new PostDTO();
        pdto.setTitle(title);
        pdto.setTag(tag);
        pdto.setCountry(country);
        pdto.setContent(content);
        pdto.setId(id);

        PostDAO pdao = new PostDAO();
        int postID = pdao.insertPost(pdto);  // 게시글 저장

        if (postID > 0) {
            String[] imgids = request.getParameterValues("imgid");
            if (imgids != null) {
                ImglikeDAO imglikeDAO = new ImglikeDAO();
                for (int i = 0; i < imgids.length; i++) {
                    ImglikeDTO imglikeDTO = new ImglikeDTO();
                    imglikeDTO.setImgid(imgids[i]);
                    imglikeDTO.setImg_index(i + 1);
                    imglikeDTO.setPostID(postID);
                    imglikeDAO.insertImg(imglikeDTO);  // 이미지 저장
                }
                imglikeDAO.close();
            }

            response.sendRedirect("pList.do");
        } else {
            request.setAttribute("message", "게시글 등록 실패");
            request.getRequestDispatcher("mini2/pWrite.jsp").forward(request, response);
        }

       pdao.close();
    }

}