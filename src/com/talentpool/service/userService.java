package com.talentpool.service;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.talentpool.po.Userinfo;

public interface userService {
	// 注册用户对自己的信息维护(照片)
		public Map<String, String> zcmaintenance( MultipartFile[] photo, HttpServletRequest request, String image_name,
				String usernames);
		// 注册用户对自己的信息维护(文字)
		public Map<String, String> updateWenzi( HttpServletRequest request,
				HttpServletResponse response,Userinfo userinfo);
		// 单个删除批量
		public String userDelete(String[] ids);

		// 批量审核
		public String userShen(String id[], String status);

		// 查询所有的用户
		public Map<String, Object> userQuery();

		// 根据用户名查找用户
		public Userinfo userQueryByname(String username);

		// 更改密码
		public String userUpdatePass(String oldpass, String newpass, String username);
		
		//根据日期范围进行查找用户 以及模糊查询
		public Map<String, Object> userQueryBydate(Date startdate,Date enddate,String name);
}
