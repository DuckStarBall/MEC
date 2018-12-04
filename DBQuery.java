package com.cqu.tensorflow.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import entity.ArContent;

public class DBQuery {

	/**
	 * @version：1.0
	 * @Description： 在数据库中根据标签搜索描述内容
	 * @param： plabel：图片标签
	 * @return: desc: 上传图片的描述内容
	 * @author: scw
	 * @Date: 18.11.10
	 */

	public static ArContent SearchDesc(String plabel) {
		Connection conn = DBUtil.getConnection();
		PreparedStatement pst = null;
		ResultSet rs = null;
		ArContent ar = new ArContent();
		try {
			String sql = "select id,description from ar_content where label=?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, plabel);
			rs = pst.executeQuery();
			while(rs.next()) {
			     ar.setId(rs.getInt(1));
			     ar.setDesc(rs.getString(2));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
		      rs.close();
		      pst.close();
		      conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return ar;
	}

}
