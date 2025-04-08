package com.yeogi.dao;

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
}
