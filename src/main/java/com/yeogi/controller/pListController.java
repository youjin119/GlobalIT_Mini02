package com.yeogi.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yeogi.dao.PostDAO;
import com.yeogi.dto.PostDTO;

import util.Pageing;

/**
 * Servlet implementation class pListController
 */
@WebServlet("/pList.do")
public class pListController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public pListController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		 // DAO 생성
	       PostDAO dao = new PostDAO();

	        // 뷰에 전달할 매개변수 저장용 맵 생성
	        Map<String, Object> map = new HashMap<>();

	        //검색
//	        String searchField = req.getParameter("searchField");
//	        String searchWord = req.getParameter("searchWord");
//	        if (searchWord != null) {
//	            // 쿼리스트링으로 전달받은 매개변수 중 검색어가 있다면 map에 저장
//	            map.put("searchField", searchField);
//	            map.put("searchWord", searchWord);
//	        }
	        
	        int totalCount = dao.selectCount(map);  // 게시물 개수

	        /* 페이지 처리 start */
//	        ServletContext application = getServletContext();
//	        int pageSize = Integer.parseInt(application.getInitParameter("POSTS_PER_PAGE"));
//	        int blockPage = Integer.parseInt(application.getInitParameter("PAGES_PER_BLOCK"));

	        int pageSize = 10; //한 페이지에 출력할 글의 갯수
	        int blockPage = 5; //페이지 번호의 갯수 1,2,3,4,5

	        // 현재 페이지 확인
	        int pageNum = 1;  // 기본값
	        String pageTemp = req.getParameter("pageNum");
	        if (pageTemp != null && !pageTemp.equals(""))
	            pageNum = Integer.parseInt(pageTemp); // 요청받은 페이지로 수정

	        // 목록에 출력할 게시물 범위 계산
	        int start = (pageNum - 1) * pageSize + 1;  // 첫 게시물 번호
	        int end = pageNum * pageSize; // 마지막 게시물 번호
	        map.put("start", start);
	        map.put("end", end);
	        /* 페이지 처리 end */

	        List<PostDTO> boardLists = dao.selectListPage(map);  // 게시물 목록 받기
	        List<PostDTO> panelLists = new ArrayList<PostDTO>();
	        if(boardLists.size()>3 && pageNum==1) {
	        	panelLists = boardLists.subList(0, 3); // 게시글 번호가 가장 늦은(최근)기사 3개 가져옴
	        }
	        else if(pageNum==1) {
	        	panelLists = boardLists; //게시글이 3개 이하면 그대로 받아옴
	        }
	        else {
		        map.put("start", 1); //값 덮어쓰기
		        map.put("end", 3);	//1번~3번 게시물 불러옴
		        panelLists = dao.selectListPage(map);
	        }
	        dao.close(); // DB 연결 닫기

	        // 뷰에 전달할 매개변수 추가
	        String pagingImg = Pageing.pagingStr(totalCount, pageSize,
	                blockPage, pageNum, "../pList.do");  // 바로가기 영역 HTML 문자열
	        System.out.println(pagingImg);
	        map.put("pagingImg", pagingImg);
	        map.put("totalCount", totalCount);
	        map.put("pageSize", pageSize);
	        map.put("pageNum", pageNum);
	        
	        String s = req.getParameter("click");
//	        System.out.println(s);
	        if(s!=null) {
	        	System.out.println(s+" 전달되었습니다.");
	        }
	        	
	        
	        // 전달할 데이터를 request 영역에 저장 후 List.jsp로 포워드
	        req.setAttribute("boardLists", boardLists);
	        req.setAttribute("panelLists", panelLists);
	        req.setAttribute("map", map);
	        req.getRequestDispatcher("/mini2/pList.jsp").forward(req, resp);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
