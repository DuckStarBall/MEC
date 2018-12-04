package com.cqu.tensorflow.utils;

import java.io.InputStream;
//import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

public class UserImageJDBC {

	/**
	 * 保存图片
	 * @version： 
	 * @Description： 
	 * @author: scw
	 * @Date:
	 */
	public static int saveUserImage(InputStream image) {
		int row = 0;
		PreparedStatement prestmt = null;
		Connection conn = DBUtil.getConnection();
		try {
		 String sql = "INSERT INTO  user_image(create_at,image) VALUES(?,?)";
	     prestmt = conn.prepareStatement(sql);
	     Timestamp time = new Timestamp(new Date().getTime());
	     prestmt.setTimestamp(1,time);
	     prestmt.setBlob(2, image);
	     row = prestmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("图片保存失败"+e.getMessage());
			e.printStackTrace();
		}finally {
			DBUtil.close(conn);
		}
		return row;
	}
	
	/**
	 * 根据传入的图片查询当前信息id
	 * @version： 
	 * @Description： 
	 * @author: scw
	 * @Date:
	 */
	public static int queryMaxUserImageId() {
		int row = 0;
		PreparedStatement prestmt = null;
		ResultSet rs = null;
		Connection conn = DBUtil.getConnection();
		try {
		 String sql = "SELECT max(id) from user_image";
	     prestmt = conn.prepareStatement(sql);
	     rs = prestmt.executeQuery();
	     while (rs.next()) {
			row = rs.getInt(1);
		}
		} catch (SQLException e) {
			System.out.println("图片查询失败"+e.getMessage());
			e.printStackTrace();
		}finally {
			DBUtil.close(conn);
		}
		return row;
	}

	/**
	 * 更新数据
	 * @version： 
	 * @Description： 
	 * @author: scw
	 * @Date:
	 */
	public static int updateUserImage(int arId,int userId) {
		int row = 0;
		PreparedStatement prestmt = null;
		Connection conn = DBUtil.getConnection();
		try {
		 String sql = "update user_image set ar_id=? WHERE id =?";
	     prestmt = conn.prepareStatement(sql);
	     prestmt.setInt(1, arId);
	     prestmt.setInt(2, userId);
	     row = prestmt.executeUpdate();
	     if (row>0) {
			System.out.println("图片更新成功");
		}else {
			System.out.println("图片更新失败");
		}
		} catch (SQLException e) {
			System.out.println("图片更新异常"+e.getMessage());
			e.printStackTrace();
		}finally {
			DBUtil.close(conn);
		}
		return row;
	}
	
}
