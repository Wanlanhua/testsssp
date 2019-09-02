package com.talentpool.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;
import com.talentpool.po.Userinfo;

public interface OtherService {
	// 登陆
	public  Map<String, String> Login(String username, String password, HttpServletRequest request);
	// 注册
	public String registered(Userinfo userinfo, HttpServletRequest request,MultipartFile user_icon,MultipartFile idcard_z,MultipartFile career,MultipartFile idcard_f);
	// 校验
	public String jiaoyan(String username);
}
