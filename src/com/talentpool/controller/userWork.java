package com.talentpool.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.talentpool.po.Userinfo;
import com.talentpool.service.userService;

@Controller
@ResponseBody
public class userWork {
	@Autowired
	private userService users;

	// 用户更新照片
	@RequestMapping(value = "/userupdate", method = RequestMethod.POST)
	public Map<String, String> updateUser(@RequestParam MultipartFile[] photo, HttpServletRequest request,
			HttpServletResponse response, String image_name, String usernames) {
		response.addHeader("Access-Control-Allow-Origin", "*");
		Map<String, String> map = users.zcmaintenance(photo, request, image_name, usernames);
		return map;
	}

	// 用户更改信息**************************************
	@RequestMapping(value = "/userupdatex", method = RequestMethod.POST)
	public Map<String, String> updateUser(HttpServletRequest request, HttpServletResponse response, Userinfo userinfo) {
		response.addHeader("Access-Control-Allow-Origin", "*");
		Map<String, String> map = users.updateWenzi(request, response, userinfo);
		return map;
	}

	// 单个删除批量
	@RequestMapping(value = "/userdelete", method = RequestMethod.POST)
	public Map<String, Object> userdelete(String[] id, HttpServletResponse response) {
		response.addHeader("Access-Control-Allow-Origin", "*");
		String s = users.userDelete(id);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", s);
		return map;
	}

	// 批量审核
	@RequestMapping(value = "/userstatus", method = RequestMethod.POST)
	public Map<String, Object> opstatus(String[] id, HttpServletResponse response, String status) {
		response.addHeader("Access-Control-Allow-Origin", "*");
		String s = users.userShen(id, status);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", s);
		return map;
	}

	// 查询所有的用户
	@RequestMapping(value = "/userquery", method = RequestMethod.GET)
	public Map<String, Object> userquery(HttpServletResponse response) {
		response.addHeader("Access-Control-Allow-Origin", "*");
		Map<String, Object> map = new HashMap<String, Object>();
		map = users.userQuery();
		return map;
	}

	// 根据账号查询用户
	@RequestMapping(value = "/userquery/{username}", method = RequestMethod.GET)
	public Userinfo userqueryByusername(HttpServletResponse response, @PathVariable("username") String username) {
		response.addHeader("Access-Control-Allow-Origin", "*");
		Userinfo userinfo = users.userQueryByname(username);
		return userinfo;
	}

	// 更改密码
	@RequestMapping(value = "/userUpass", method = RequestMethod.POST)
	public String userUpdatePass(HttpServletResponse response, String old_password, String new_password,
			String username) {
		response.addHeader("Access-Control-Allow-Origin", "*");
		String s = users.userUpdatePass(old_password, new_password, username);
		return s;
	}

	//根据日期查询
	@RequestMapping(value = "/userBydate", method = RequestMethod.POST)
	public Map<String, Object> userQueryBydate(@DateTimeFormat(pattern = "yyyy-MM-dd") Date minDate,
			@DateTimeFormat(pattern = "yyyy-MM-dd") Date maxDate, HttpServletResponse response, String name) {
		response.addHeader("Access-Control-Allow-Origin", "*");
		Map<String, Object> map = new HashMap<String, Object>();
		map = users.userQueryBydate(minDate, maxDate, name);
		return map;
	}

}
