package com.talentpool.service;

import java.util.Date;
import java.util.Map;

import com.talentpool.po.Operatorinfo;

public interface opService {
	// 查询所有的操作员
		public Map<String, Object> Opquery();

		// 删除一个op
		public String Opdelete(String[] ids);

		// 添加一个员工
		public String Opadd(Operatorinfo operatorinfo);

		// 修改员工
		public String Opupdate(Operatorinfo operatorinfo);

		// 修改员工的状态
		public String Opstatus(Integer id, String status);
		// 管理区域内的用户
		public Map<String, Object> OpUser(String username);
		//区域 日期
		public Map<String, Object> opuserDate(Date startdate,Date enddate,String name,String username);
}
