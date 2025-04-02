package com.yeogi.dao;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.yeogi.dto.PostDTO;

import conn.DBConnPool;

public class PostDAO extends DBConnPool{
	public PostDAO() {
		super();
	}
	
	// 검색 조건에 맞는 게시물의 개수를 반환합니다.
    public int selectCount(Map<String, Object> map) {
        int totalCount = 0;
        String query = "SELECT COUNT(*) FROM post";
//        if (map.get("searchWord") != null) {
//            query += " WHERE " + map.get("searchField") + " "
//                   + " LIKE '%" + map.get("searchWord") + "%'";
//        }
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            rs.next();
            totalCount = rs.getInt(1);
        }
        catch (Exception e) {
            System.out.println("게시물 카운트 중 예외 발생");
            e.printStackTrace();
        }

        return totalCount;
    }
    public List<PostDTO> selectListPage(Map<String,Object> map) {
        List<PostDTO> board = new ArrayList<PostDTO>();
        String query = " "
//        				+ " SELECT * FROM post ";
        		
        		
                     + "SELECT * FROM ( "
                     + " SELECT Tb.*, ROWNUM rNum FROM ( "
                     + "  SELECT * FROM post ";
        //검색을 하는 경우 where절 추가
//        if (map.get("searchWord") != null)
//        {
//            query += " WHERE " + map.get("searchField")
//                   + " LIKE '%" + map.get("searchWord") + "%' ";
//        }

        query += "   ORDER BY postid DESC "
               + "  ) Tb "
               + " ) "
               + " WHERE rNum BETWEEN ? AND ?";
        
        System.out.println(query.replaceFirst("\\?", map.get("start").toString())+map.get("end").toString());
        
        try {
            psmt = con.prepareStatement(query);
            psmt.setString(1, map.get("start").toString());
            psmt.setString(2, map.get("end").toString());
            rs = psmt.executeQuery();

            while (rs.next()) {
            	// Model 객체에 게시글 데이터 저장
            	PostDTO dto = new PostDTO();

                dto.setIdx(rs.getString(1));
                dto.setTitle(rs.getString(2));
                dto.setCountry(rs.getString(4));
                dto.setTag(rs.getString(3));
                dto.setContent(rs.getString(5));
                dto.setPostdate(rs.getDate(6));
//                dto.setOfile(rs.getString(6));
//                dto.setSfile(rs.getString(7));
//                dto.setDowncount(rs.getInt(8));
//                dto.setPass(rs.getString(9));
//                dto.setVisitcount(rs.getInt(10));

                board.add(dto); // list에 model 추가
            }
        }
        catch (Exception e) {
            System.out.println("게시물 조회 중 예외 발생");
            e.printStackTrace();
        }
        return board; // list 리턴
    }
	
}
