package com.cqu.tensorflow.utils;

import java.sql.DriverManager;
import java.sql.SQLException;
//import java.io.FileReader;
//import java.io.Reader;
import java.sql.Connection;
//import java.util.Properties;

/**
 * @author Administrator
 *
 */
public class DBUtil {
	private static String JDBC_DRIVER; // JDBC驱动
	private static String DB_URL;// JDBC's URL
	private static String USER;
	private static String PASS;
	static String label;// 全局变量 name
	static String description;// 全局变量url
	static int id;
	/**
	 * @version： v1.0
	 * @Description： 连接数据库
	 * @author: scw
	 * @Date: 
	 */
	static {
//		Properties prop = new Properties();
//		Reader in;
//		try {
//			in = new FileReader("src\\jdbcConfig.properties");
//			prop.load(in);	
//		}catch(Exception e) {
//			e.printStackTrace();
//		}
//		JDBC_DRIVER = prop.getProperty("JDBC_DRIVER");
//		DB_URL = prop.getProperty("DB_URL");
//		USER = prop.getProperty("USER");
//		PASS = prop.getProperty("PASS");
		JDBC_DRIVER = "com.mysql.jdbc.Driver";
		DB_URL = "jdbc:mysql://localhost:3306/ar_cqu?useSSL=true";
		USER = "root";
		PASS = "shichangwei";
	}
	
	/**
	 * @version： 1.0
	 * @Description： 打开数据库连接
	 * @return: Connection类型
	 * @author: scw
	 * @Date: 
	 */
	public static Connection getConnection() {
		Connection conn = null;// 设置conn对象
        //数据库的查询操作
		try {
			// 注册 JDBC 驱动
			try {
				Class.forName(JDBC_DRIVER);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			// 打开链接
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
		} catch (SQLException se) {
			// 处理 JDBC 错误
			se.printStackTrace();
		}
		return conn;
	}
	
		/**
		 * @version： 1.0
		 * @Description： 关闭数据库连接
		 * @author: scw
		 * @Date: 
		 */
		public static void close(Connection conn) {
			if(conn!=null){
	            try {
	                conn.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }	
		}			
	 }
}
