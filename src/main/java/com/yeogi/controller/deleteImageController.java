package com.yeogi.controller;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import util.FileUtil;

/**
 * Servlet implementation class deleteImageController
 */
@WebServlet("/deleteImage.do")
public class deleteImageController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public deleteImageController() {
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
		  StringBuilder sb = new StringBuilder();
	        String line;
	        BufferedReader reader = request.getReader();
	        while ((line = reader.readLine()) != null) {
	            sb.append(line);
	        }
	        
	     // ✅ 로그 찍기 - 요청이 왔는지, Body에 뭐가 들어왔는지 확인
	        System.out.println("🔥 deleteImageController POST 요청 도착");
	        System.out.println("요청 Body 내용: " + sb.toString());
	        
	        try {
	            JSONObject json = new JSONObject(sb.toString());
	            JSONArray imageArray = json.getJSONArray("images");
	            
	            System.out.println("요청 데이터: " + sb.toString());
	            System.out.println("파일 삭제 시도 시작");
	            
	            for (int i = 0; i < imageArray.length(); i++) {
	                String filename = imageArray.getString(i);
	                System.out.println("삭제 대상 파일명: " + filename);
	                FileUtil.deleteFile(request, "/uploads", filename); // 업로드 디렉토리 기준
	            }

	            response.setStatus(HttpServletResponse.SC_OK); // 200
	        } catch (Exception e) {
	            e.printStackTrace();
	            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // 500
	        }
	    }
	}

