package com.yeogi.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URLDecoder;

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
		String contentType = request.getContentType();
		System.out.println("🔥 deleteImageController POST 요청 도착");
		System.out.println("Content-Type: " + contentType);

		try {
			if (contentType != null && contentType.contains("application/json")) {
				// ✅ JSON 요청 처리
				StringBuilder sb = new StringBuilder();
				String line;
				BufferedReader reader = request.getReader();
				while ((line = reader.readLine()) != null) {
					sb.append(line);
				}

				JSONObject json = new JSONObject(sb.toString());
				JSONArray imageArray = json.getJSONArray("images");

				for (int i = 0; i < imageArray.length(); i++) {
					String filename = imageArray.getString(i);
					System.out.println("삭제 대상 파일명 (JSON): " + filename);
					FileUtil.deleteFile(request, "/uploads", filename);
				}

			} else if (contentType != null && contentType.contains("application/x-www-form-urlencoded")) {
				// ✅ 폼 데이터 (ex: images=a.jpg,b.jpg) 처리
				String param = request.getParameter("images");
				if (param != null && !param.isEmpty()) {
					String decoded = URLDecoder.decode(param, "UTF-8");
					String[] filenames = decoded.split(",");

					for (String filename : filenames) {
						System.out.println("삭제 대상 파일명 (form): " + filename);
						FileUtil.deleteFile(request, "/uploads", filename.trim());
					}
				}
			}else if (contentType != null && contentType.contains("text/plain")) {
				// ✅ sendBeacon()에서 보내는 JSON 문자열 처리
				StringBuilder sb = new StringBuilder();
				String line;
				BufferedReader reader = request.getReader();
				while ((line = reader.readLine()) != null) {
					sb.append(line);
				}

				String body = sb.toString();
				System.out.println("삭제 대상 파일명 (plain): " + body);

				try {
					JSONObject json = new JSONObject(body);
					JSONArray imageArray = json.getJSONArray("images");

					for (int i = 0; i < imageArray.length(); i++) {
						String filename = imageArray.getString(i);
						System.out.println("삭제 대상 파일명 (JSON in text/plain): " + filename);
						FileUtil.deleteFile(request, "/uploads", filename);
					}
				} catch (Exception e) {
					System.out.println("JSON 파싱 실패: " + e.getMessage());
				}
			} else {
				System.out.println("지원되지 않는 Content-Type 형식입니다.");
			}

			response.setStatus(HttpServletResponse.SC_OK);

		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}
	}