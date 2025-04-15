package com.yeogi.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yeogi.dao.ImglikeDAO;
import com.yeogi.dao.PostDAO;
import com.yeogi.dto.PostDTO;

import util.FileUtil;

/**
 * Servlet implementation class pEditController
 */
@WebServlet("/pEdit.do")
public class pEditController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public pEditController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int postID = Integer.parseInt(request.getParameter("postID"));  // URL에서 postID 가져옴

		PostDAO dao = new PostDAO();
		PostDTO pdto = dao.selectView(postID); // 해당 게시글 정보 가져오기
		request.setAttribute("pdto", pdto);  // JSP로 넘기기
		dao.close(); // ✅ DB 커넥션 반납
		
		// ✅ 기존 이미지 리스트 가져오기
		ImglikeDAO idao = new ImglikeDAO();
		request.setAttribute("imgList", idao.getImagesByPostID(postID)); // ← 이 한 줄이 핵심
		idao.close();
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("mini2/pEdit.jsp");
		dispatcher.forward(request, response);  // 수정 폼으로 이동
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		 System.out.println("🔥 doPost 진입 확인"); // 이거 먼저 찍어봐
		 request.setCharacterEncoding("UTF-8");
		// 1. 게시글 수정
				int postID = Integer.parseInt(request.getParameter("postID"));
				String title = request.getParameter("title");
				String tag = request.getParameter("tag");
				String country = request.getParameter("country");
				String content = request.getParameter("content");
				
				// System.out.println("📄 수정된 게시글 내용: " + content);  // 디버깅용 로깅 추가
				
				PostDTO pdto = new PostDTO();
				pdto.setPostID(postID);
				pdto.setTitle(title);
				pdto.setTag(tag);
				pdto.setCountry(country);
				pdto.setContent(content);

				PostDAO pdao = new PostDAO();
				ImglikeDAO idao = new ImglikeDAO();
				try {
				    // ⭐ 트랜잭션 시작
				    pdao.con.setAutoCommit(false);
				    idao.con.setAutoCommit(false);

				    // 게시글 수정
				    pdao.updatePost(pdto);

				    // ✅ 기존 이미지 파일명 가져오기
				    List<String> oldImgList = idao.getImgidsByPostID(postID);
				  //  System.out.println("🧾 기존 이미지 파일 수: " + oldImgList.size());

				    // ✅ 서버에서 이미지 파일 삭제
				    for (String imgid : oldImgList) {
				        FileUtil.deleteFile(request, "/upload", imgid);  
				    }
				    //System.out.println("🧹 서버에서 이미지 파일 삭제 완료");

				    // ✅ DB에서 이미지 삭제
				    int deleted = idao.deleteImagesByPostID(postID);
				    //System.out.println("🗑️ 삭제된 이미지 수: " + deleted);

				    // 새 이미지 등록
				    String[] imgids = request.getParameterValues("imgid");
				    if (imgids != null && imgids.length > 0) {
				        for (int i = 0; i < imgids.length; i++) {
				            idao.insertImage(imgids[i], i + 1, postID);
				        }
				      //  System.out.println("🖼️ 새 이미지 " + imgids.length + "개 등록 완료");
				    } else {
				        //System.out.println("📭 등록할 새 이미지가 없습니다");
				    }

				    // ⭐ 커밋!
				    pdao.con.commit();
				    idao.con.commit();

				} catch (Exception e) {
				    try {
				        //System.out.println("⚠️ 예외 발생, 롤백 수행 중...");
				        pdao.con.rollback();
				        idao.con.rollback();
				    } catch (Exception rollbackEx) {
				        rollbackEx.printStackTrace();
				    }
				    e.printStackTrace();
				} finally {
				    pdao.close();
				    idao.close();
				}
				// 3. 리디렉션 (수정된 글 보기 페이지로)
				response.sendRedirect("/pView.do?postID=" + postID);
			}
	}