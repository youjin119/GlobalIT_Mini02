package com.yeogi.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.yeogi.dto.MemberDTO;
import conn.DBConnPool; // Kế thừa từ DBConnPool

public class MemberDAO extends DBConnPool { 
    private static MemberDAO instance = new MemberDAO();

    public MemberDAO() {}

    public static MemberDAO getInstance() {
        return instance;
    }

    // ID & Password 확인 (로그인)
    public int userCheck(String id, String pw) {
        int result = -1;
        String sql = "SELECT pw FROM member WHERE id=?";
        
        try {
            psmt = con.prepareStatement(sql);
            psmt.setString(1, id);
            rs = psmt.executeQuery();
            
            if (rs.next()) {
                if (rs.getString("pw").equals(pw)) {
                    result = 1; // ID & 비밀번호 전확함
                } else {
                    result = 0; // 비밀번호 틀림
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } 
        return result;
    }
 
    
    
 // ID 중복 체크
    public boolean isIdExist(String id) {
        boolean exists = false;
        String sql = "SELECT id FROM member WHERE id=?";
        
        try {
            psmt = con.prepareStatement(sql);
            psmt.setString(1, id);
            rs = psmt.executeQuery();
            
            if (rs.next()) {
                exists = true; // ID 이미 존재
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return exists;
    }
       

    // 회원가입 
    public int insertMember(MemberDTO mVo) {
        int result = -1;
        String sql = "INSERT INTO member VALUES(?, ?, ?, ?, ?, ?)";

        try {
            psmt = con.prepareStatement(sql);
            psmt.setString(1, mVo.getId());
            psmt.setString(2, mVo.getPw());
            psmt.setString(3, mVo.getName());
            psmt.setString(4, mVo.getPhonenum());
            psmt.setInt(5, mVo.getAdminid());
            psmt.setDate(6, mVo.getJdate());

            result = psmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } 
        return result;
    }

    // 회원 정보 조회 
    public MemberDTO getMember(String id) {
        MemberDTO mVo = null;
        String sql = "SELECT * FROM member WHERE id=?";
        
        try {
            psmt = con.prepareStatement(sql);
            psmt.setString(1, id);
            rs = psmt.executeQuery();
            
            if (rs.next()) {
                mVo = new MemberDTO();
                mVo.setId(rs.getString("id"));
                mVo.setPw(rs.getString("pw"));
                mVo.setName(rs.getString("name"));
                mVo.setPhonenum(rs.getString("phonenum"));
                mVo.setAdminid(rs.getInt("adminid"));
                mVo.setJdate(rs.getDate("jdate"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } 
        return mVo;
    }

    // 회원 정보 수정 
    public int updateMember(MemberDTO mVo) {
        int result = -1;
        String sql = "UPDATE member SET name=?, pw=?, phonenum=? WHERE id=?";
        
        try {
            psmt = con.prepareStatement(sql);
            psmt.setString(1, mVo.getName());
            psmt.setString(2, mVo.getPw());
            psmt.setString(3, mVo.getPhonenum());
            psmt.setString(4, mVo.getId());

            result = psmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } 
        return result;
    }
    
    // 전화번호로 아이디 찾기 (Find ID by Phone Number)
    public String findIdByPhone(String phonenum) {
        String foundId = null;
        String sql = "SELECT id FROM member WHERE phonenum = ?";

        try {
            psmt = con.prepareStatement(sql);
            psmt.setString(1, phonenum);
            rs = psmt.executeQuery();

            if (rs.next()) {
                foundId = rs.getString("id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } 
        return foundId;
    }
    
    // ID와 전화번호 알면 새빌밀번호 재설정 가능
    public boolean resetPassword(String id, String phonenum, String newPw) {
        boolean success = false;
        String sql = "UPDATE member SET pw = ? WHERE id = ? AND phonenum = ?";

        try {
            psmt = con.prepareStatement(sql);
            psmt.setString(1, newPw);
            psmt.setString(2, id);
            psmt.setString(3, phonenum);

            int result = psmt.executeUpdate();
            success = result > 0;
        } catch (Exception e) {
            e.printStackTrace();  //에러가 있으면 처리
        } 
        return success;
    }
    
    public boolean isAdmin(String id) {
    	boolean result = false;
    	String sql = " select adminid from member where id = ? ";
    	try {
            psmt = con.prepareStatement(sql);
            psmt.setString(1, id);
            
            rs = psmt.executeQuery();
            rs.next();
            int qResult = rs.getInt(1);
            System.out.println(qResult);
            result = qResult == 1 ? true : false;
    	}catch (Exception e) {
			e.printStackTrace();
		}
    	return result;
    }
     
//  Cuối class MemberDAO
    public void close() {
        super.close(); // Gọi close() từ DBConnPool để đóng kết nối
    }
//    
}