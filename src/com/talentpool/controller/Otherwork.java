package com.talentpool.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.talentpool.po.Userinfo;
import com.talentpool.service.OtherService;

@Controller
@ResponseBody
public class Otherwork {
	@Autowired
	private OtherService otherservice;
	// 登陆
	@RequestMapping(value = "/Login", method = RequestMethod.POST)
	public Map<String, String> Login(String username, String password, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, String> map=otherservice.Login(username, password, request);
		 return map;
	}
	
	@RequestMapping(value = "/testA", method = RequestMethod.GET)
	public List<Map<String, String>> Android(HttpServletResponse response) {
		response.addHeader("Access-Control-Allow-Origin", "*");
			Map<String, String> map=new HashMap<>();
			map.put("m", "2");
			map.put("n", "1");
			Map<String, String> map2=new HashMap<>();
			map2.put("m", "3");
			map2.put("n", "4");
			List<Map<String, String>> list=new ArrayList<>();
			list.add(map);
			list.add(map2);
			return list;
	}
	
	// 校验
		@RequestMapping(value = "/jiaoyan", method = RequestMethod.POST)
		public String jiaoyan(@RequestParam(value = "username", required = true) String username,
				HttpServletResponse response) {
			String result = otherservice.jiaoyan(username);
			response.addHeader("Access-Control-Allow-Origin", "*");
			return result;
		}
		
		// 注册
		@RequestMapping(value = "/registered", method = RequestMethod.POST)
		public String registered(Userinfo userinfo,HttpServletRequest request,
				HttpServletResponse response, MultipartFile user_icon,MultipartFile idcard_z,MultipartFile career,MultipartFile idcard_f) {
			response.addHeader("Access-Control-Allow-Origin", "*");
			String i=otherservice.registered(userinfo, request, user_icon,idcard_z,career,idcard_f);
			return i;
		}

}
