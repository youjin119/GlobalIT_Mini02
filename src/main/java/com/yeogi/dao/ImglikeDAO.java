
package com.yeogi.dao;

import java.util.ArrayList;
import java.util.List;

import com.yeogi.dto.ImglikeDTO;

import conn.DBConnPool;

public class ImglikeDAO extends DBConnPool  {
	public int insertImg(ImglikeDTO dto) {
	    int result = 0;
	    String sql = "INSERT INTO img (imgid, img_index, postID) VALUES (?, ?, ?)";
	    
	    try {
	        psmt = con.prepareStatement(sql);
	        psmt.setString(1, dto.getImgid());
	        psmt.setInt(2, dto.getImg_index());
	        psmt.setInt(3, dto.getPostID());

	        result = psmt.executeUpdate();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return result;
	}
	
	public int deleteImagesByPostID(int postID) {
		int result= 0;
		String sql = "DELETE FROM img WHERE postID = ?";
		try {
			psmt = con.prepareStatement(sql);
			psmt.setInt(1, postID);
			result = psmt.executeUpdate();
			System.out.println("🧹 삭제된 이미지 수: " + result);
		} catch (Exception e) {
			System.out.println("게시글 이미지 전체 삭제 중 예외 발생");
			e.printStackTrace();
		}
		return result;
	}
	
	public void insertImage(String imgid, int index, int postID) {
	    String sql = "INSERT INTO img (imgid, img_index, postID) VALUES (?, ?, ?)";
	    try {
	        psmt = con.prepareStatement(sql);
	        psmt.setString(1, imgid);
	        psmt.setInt(2, index);
	        psmt.setInt(3, postID);
	        psmt.executeUpdate();
	    } catch (Exception e) {
	        System.out.println("이미지 삽입 중 예외 발생");
	        e.printStackTrace();
	    }
	}
	
	public List<ImglikeDTO> getImagesByPostID(int postID) {
	    List<ImglikeDTO> imgList = new ArrayList<>();
	    String sql = "SELECT * FROM img WHERE postid = ? ORDER BY img_index ASC";

	    try {
	        psmt = con.prepareStatement(sql);
	        psmt.setInt(1, postID);
	        rs = psmt.executeQuery();

	        while (rs.next()) {
	            ImglikeDTO dto = new ImglikeDTO();
	            dto.setImgid(rs.getString("imgid"));
	            dto.setImg_index(rs.getInt("img_index"));
	            dto.setPostID(rs.getInt("postid"));
	            imgList.add(dto);
	        }
	    } catch (Exception e) {
	        System.out.println("게시글 이미지 조회 중 오류 발생");
	        e.printStackTrace();
	    }

	    return imgList;
	}
	
	public List<String> getImgidsByPostID(int postID) {
	    List<String> imgids = new ArrayList<>();
	    try {
	        String sql = "SELECT imgid FROM img WHERE postID=?";
	        psmt = con.prepareStatement(sql);
	        psmt.setInt(1, postID);
	        rs = psmt.executeQuery();
	        while (rs.next()) {
	            imgids.add(rs.getString("imgid"));
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return imgids;
	}
}
