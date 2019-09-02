package com.talentpool.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.talentpool.po.Operatorinfo;
import com.talentpool.service.opService;

@Controller
@ResponseBody
public class opwork {

	@Autowired
	
	private opService opsevice;
	// 获得所有的op
		@RequestMapping(value = "/op", method = RequestMethod.GET)
		public Map<String, Object> opquery(HttpServletResponse response) {
			response.addHeader("Access-Control-Allow-Origin", "*");
			Map<String, Object> map = opsevice.Opquery();
			return map;
		}

		// 删除op
		@RequestMapping(value = "/opdelete", method = RequestMethod.POST)
		public Map<String, Object> opdelete(String[] id, HttpServletResponse response) {
			response.addHeader("Access-Control-Allow-Origin", "*");
			Map<String, Object> map = new HashMap<String, Object>();
			String result = opsevice.Opdelete(id);
			map.put("result", result);
			return map;
		}

		// 添加员工
		@RequestMapping(value = "/op", method = RequestMethod.POST)
		public String opadd(Operatorinfo operatorinfo, HttpServletResponse response) {
			response.addHeader("Access-Control-Allow-Origin", "*");
			String result = opsevice.Opadd(operatorinfo);
			return result;
		}

		// 修改员工
		@RequestMapping(value = "/opUpdate", method = RequestMethod.POST)
		public Map<String, Object> opupdate(Operatorinfo operatorinfo, HttpServletResponse response) {
			response.addHeader("Access-Control-Allow-Origin", "*");
			String result = opsevice.Opupdate(operatorinfo);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("result", result);
			return map;
		}

		// 员工状态改变
		@RequestMapping(value = "/status", method = RequestMethod.POST)
		public String opstatus(Integer id, HttpServletResponse response, String status) {
			response.addHeader("Access-Control-Allow-Origin", "*");
			String result = opsevice.Opstatus(id, status);
			return result;
		}
		
		//查询区域内所有用户
		@RequestMapping(value = "/opusers", method = RequestMethod.POST)
		public Map<String, Object> opusers( HttpServletResponse response, String username) {
			response.addHeader("Access-Control-Allow-Origin", "*");
			Map<String, Object> result = opsevice.OpUser(username);
			return result;
		}
		
		//查询日期方位内的区域内所有用户
			@RequestMapping(value = "/opusersDate", method = RequestMethod.POST)
			public Map<String, Object> opusersDate(@DateTimeFormat(pattern = "yyyy-MM-dd")Date minDate,@DateTimeFormat(pattern = "yyyy-MM-dd")Date maxDate,HttpServletResponse response,String name,String username) {
				response.addHeader("Access-Control-Allow-Origin", "*");
				Map<String, Object> result=opsevice.opuserDate(minDate, maxDate, name, username);
				return result;
			}
}
