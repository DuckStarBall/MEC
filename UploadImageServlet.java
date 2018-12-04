package com.cqu.tensorflow.servlet;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.cqu.tensorflow.utils.DBQuery;
import com.cqu.tensorflow.utils.LabelImageUtil;
import com.cqu.tensorflow.utils.UserImageJDBC;

import entity.ArContent;
/**
 *   上传图片服务
 * @author scw
 *  版权所有：重庆大学
 */
@WebServlet("/uploadimage")
public class UploadImageServlet extends HttpServlet {
	private static final long serialVersionUID = -80355405048307697L;
	public static String ilabel;
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try{
			//使用fileupload解析request
			DiskFileItemFactory dff = new DiskFileItemFactory();//建立DiskFileItemFactory对象
			ServletFileUpload sfu = new ServletFileUpload(dff);
			//分析请求，并得到上传文件的FileItem对象  
			List<FileItem> items = sfu.parseRequest(request);
			// 获取上传字段
			FileItem fileItem = items.get(0);
			// 更改文件名为唯一的
			String filename = fileItem.getName();
			if (filename!=null) {
				filename="timg.jpg";
			}
			
			InputStream str = fileItem.getInputStream();
			int row = UserImageJDBC.saveUserImage(str);
			int userId = 0;
			if (row<1) {
				System.out.println("图片上传失败");
			}else {
				// 根据str查询成功的数据
			    userId = UserImageJDBC.queryMaxUserImageId();
				System.out.println("图片上传成功");
			}
			
			String storeDirectory ="E:\\imagefiles";
			fileItem.write(new File(storeDirectory, filename));
			// 上传成功后，传入模型识别
			StringBuilder sb = new StringBuilder();//字符串拼接对象
			sb.append(storeDirectory).append("\\");
			sb.append(filename);
			// 调用图片,进行识别解析
		    //路径：F:\imagefiles\timg.jpg
			ilabel = LabelImageUtil.parseImage(sb.toString());
			//System.out.println(ilabel);//打印的图片标签
			int arId = 0;
			String arDesc = "";
			ArContent ar = DBQuery.SearchDesc(ilabel);
			if (ar!=null) {
				arDesc = ar.getDesc();
				System.out.println(arDesc);
				arId=ar.getId();
			}else {
				System.out.println("识别不了什么类型");
			}
			// 根据用户id更新数据
		    UserImageJDBC.updateUserImage(arId,userId);
			
		    }catch (Exception e) {
			   System.out.println("失败"+e.getMessage());
			}
	}
}
