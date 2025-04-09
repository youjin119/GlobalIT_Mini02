package com.yeogi.dao;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.yeogi.dto.PostDTO;

import conn.DBConnPool;

public class PostDAO extends DBConnPool{
	public PostDAO() {
		super();
	}
	
	// 게시물의 개수를 반환
    public int selectCount(Map<String, Object> map) {
        int totalCount = 0;
        String query = "SELECT COUNT(*) FROM post";

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
    // 페이징 - 선택된 페이지의 게시물 출력
    public List<PostDTO> selectListPage(Map<String,Object> map) {
        List<PostDTO> board = new ArrayList<PostDTO>();
        String query = " "
        		
                     + "SELECT * FROM ( "
                     + " SELECT Tb.*, ROWNUM rNum FROM ( "
                     + "  SELECT * FROM post ";

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
            	
            	PreparedStatement subPsmt=con.prepareStatement(" select imgid from img where postid = ? and img_index=1 ");
            	subPsmt.setInt(1, rs.getInt("postid"));
            	ResultSet subrs = subPsmt.executeQuery();
            	while (subrs.next())
            		dto.setContent(subrs.getString("imgid"));
            	
            	
            	dto.setPostID(rs.getInt("postID"));  // ✅ postID 설정
                dto.setId(rs.getString("id"));
                dto.setTitle(rs.getString("title"));
                dto.setTag(rs.getString("tag"));
                dto.setCountry(rs.getString("country"));
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                dto.setPostDate(sdf.format(rs.getDate("postDate")));
                dto.setVcount(rs.getInt(8));

                board.add(dto); // list에 model 추가
            }
        }
        catch (Exception e) {
            System.out.println("게시물 조회 중 예외 발생");
            e.printStackTrace();
        }
        return board; // list 리턴
    }
    public int insertPost(PostDTO pdto) {
        int generatedPostID = -1;
        String sql = "INSERT INTO post (postID, title, tag, country, content, postDate, id, vcount) " +
                     "VALUES (post_seq.nextval, ?, ?, ?, ?, SYSDATE, ?, 0)";

        try {
            // prepareStatement에 두 번째 인자로 컬럼명을 넘기면 생성된 키를 받아올 수 있음
            psmt = con.prepareStatement(sql, new String[]{"postID"});
            psmt.setString(1, pdto.getTitle());
            psmt.setString(2, pdto.getTag());
            psmt.setString(3, pdto.getCountry());
            psmt.setString(4, pdto.getContent());
            psmt.setString(5, pdto.getId());

            int result = psmt.executeUpdate();

            if (result > 0) {
                rs = psmt.getGeneratedKeys(); // 생성된 postID 받아오기
                if (rs.next()) {
                    generatedPostID = rs.getInt(1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } 
        return generatedPostID;
    }
    // 주어진 일련번호에 해당하는 게시물을 DTO에 담아 반환합니다.
    public PostDTO selectView(int postID) {
        PostDTO pdto = new PostDTO(); // DTO 객체 생성
        String query = "SELECT p.*, m.name FROM post p JOIN member m ON p.id = m.id WHERE p.postID=?";
        try {
            psmt = con.prepareStatement(query); // 쿼리문 준비
            psmt.setInt(1, postID); // 인파라미터 설정
            rs = psmt.executeQuery(); // 쿼리문 실행

            if (rs.next()) { // 결과를 DTO 객체에 저장
                pdto.setPostID(rs.getInt("postID"));
                pdto.setTitle(rs.getString("title"));
                pdto.setCountry(rs.getString("country"));
                pdto.setContent(rs.getString("content"));
                pdto.setTag(rs.getString("tag"));
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                pdto.setPostDate(sdf.format(rs.getDate("postDate")));
                pdto.setName(rs.getString("name"));  // ← 회원 테이블에서 가져온 이름
                pdto.setVcount(rs.getInt("vcount"));
                pdto.setId(rs.getString("id"));
            }
        } catch (Exception e) {
            System.out.println("게시물 상세보기 중 예외 발생");
            e.printStackTrace();
        }
        return pdto; // 결과 반환
    }

    
    
	// 조회수 증가
    public void updateVcount(int postId) {
    	String query = " update post set vcount = vcount+1 where postid=? ";
    	try {
    		psmt = con.prepareStatement(query);
    		psmt.setInt(1, postId);
    		psmt.executeQuery();
    	}catch (Exception e) {
    		System.out.println("게시물 조회수 증가 중 오류");
    		e.printStackTrace();
    	}
    }
    
    public List<PostDTO> selectTagPage (Map<String, Object> map) {
        List<PostDTO> board = null;
        
        String query = "SELECT * FROM (" +
                "  SELECT Tb.*, ROWNUM rNum FROM (" +
                "    SELECT * FROM post";
        if (map.containsKey("tag")) {
        	query += " WHERE tag LIKE ?";
        }
        
        query += "    ORDER BY postId DESC" +
        		"  ) Tb" +
        		")" +
        		"WHERE rNum BETWEEN ? AND ?";

        try {
            psmt = con.prepareStatement(query);
            int parameterIndex = 1;
            if (map.containsKey("tag")) {
            	psmt.setString(parameterIndex++, "%" + map.get("tag") + "%"); // '%'를 파라미터 값에 추가
            }
            psmt.setInt(parameterIndex++, (Integer) map.get("start"));
            psmt.setInt(parameterIndex, (Integer) map.get("end"));
            
            System.out.println(query);
            
            rs = psmt.executeQuery();
            board = new ArrayList<>();
            while (rs.next()) {
                // Model 객체에 게시글 데이터 저장
            	PostDTO dto = new PostDTO();

            	PreparedStatement subPsmt=con.prepareStatement(" select imgid from img where postid = ? and img_index=1 ");
            	subPsmt.setInt(1, rs.getInt("postid"));
            	ResultSet subrs = subPsmt.executeQuery();
            	while (subrs.next())
            		dto.setContent(subrs.getString("imgid"));
            	
                dto.setPostID(rs.getInt(1));
                dto.setTitle(rs.getString(2));
                dto.setTag(rs.getString(3));
                dto.setCountry(rs.getString(4));
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                dto.setPostDate(sdf.format(rs.getDate("postDate")));
                dto.setId(rs.getString("id"));
                dto.setVcount(rs.getInt(8));

                board.add(dto); // list에 model 추가
            }
        } catch (Exception e) {
            System.out.println("게시물 태그로 조회 중 예외 발생");
            e.printStackTrace();
        }
        return board;
    }
 	// 태그 검색 후 게시물 개수 반환
    public int selectTagCount(Map<String, Object> map) {
        int totalCount = 0;
        String query = " SELECT COUNT(*) FROM post ";
        if (map.containsKey("tag")) {
            query += " WHERE tag LIKE ? ";
        }

        try {
            psmt = con.prepareStatement(query);
            if (map.containsKey("tag")) {
                psmt.setString(1, "%" + map.get("tag") + "%");
            }
            rs = psmt.executeQuery(query);
            rs.next();
            totalCount = rs.getInt(1);
        } catch (Exception e) {
            System.out.println("태그 검색 게시물 카운트 중 예외 발생");
            e.printStackTrace();
        }
        return totalCount;
    }
    
    
}
