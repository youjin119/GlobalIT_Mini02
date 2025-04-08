package com.yeogi.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.yeogi.dto.MemberDTO;
import conn.DBConnPool; // Kế thừa từ DBConnPool

public class MemberDAO extends DBConnPool { // Kế thừa DBConnPool
    private static MemberDAO instance = new MemberDAO();

    private MemberDAO() {}

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
                    result = 1; // ID & Mật khẩu đúng
                } else {
                    result = 0; // Sai mật khẩu
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (psmt != null) psmt.close();
                if (rs != null) rs.close();
            } catch (Exception e) {
                e.printStackTrace();
            }}
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
        } finally {
            try {
                if (psmt != null) psmt.close();
                if (rs != null) rs.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return exists;
    }
       

    // 회원가입 (Thêm thành viên mới)
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
        } finally {
            try {
                if (psmt != null) psmt.close();
                if (rs != null) rs.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    // 회원 정보 조회 (Lấy thông tin user)
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
        } finally {
            try {
                if (psmt != null) psmt.close();
                if (rs != null) rs.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return mVo;
    }

    // 회원 정보 수정 (Cập nhật thông tin user)
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
        } finally {
            try {
                if (psmt != null) psmt.close();
                if (rs != null) rs.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
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
        } finally {
            try {
                if (psmt != null) psmt.close();
                if (rs != null) rs.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return foundId;
    }
    
    //pass
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
            e.printStackTrace();  // Xử lý lỗi nếu có
        } finally {
            try {
                if (psmt != null) psmt.close(); 
                if (rs != null) rs.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return success;
    }
     
    
}