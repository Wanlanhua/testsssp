package com.talentpool.serviceipm;

import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.talentpool.dao.LogininfoRepository;
import com.talentpool.dao.OperatorinfoRepository;
import com.talentpool.dao.userinfoRepository;
import com.talentpool.po.Logininfo;
import com.talentpool.po.Operatorinfo;
import com.talentpool.po.Userinfo;
import com.talentpool.service.opService;

@Service
public class opServiceIpm implements opService {

	@Autowired
	private OperatorinfoRepository opRe;
	@Autowired
	private LogininfoRepository loginRe;
	
	@Autowired
	private userinfoRepository userRe;

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	@Override
	public Map<String, Object> Opquery() {
		List<Operatorinfo> list = opRe.findByIdIsNotNull();
		Map<String, Object> map = new HashMap<String, Object>();
		if (list.size() <= 0) {
			map.put("result", "2");
			return map;
		} else {
			String status = "";
			for (Operatorinfo operatorinfo : list) {
				status = operatorinfo.getStatus();
				if (status.equals("1")) {
					operatorinfo.setStatus("已启用");
				} else {
					operatorinfo.setStatus("已停用");
				}
			}

			map.put("opertors", list);
			map.put("result", "1");
			return map;
		}
	}

	@Transactional
	@Override
	public String Opdelete(String[] ids) {
		for (String string : ids) {
			int i = loginRe.deleteLogin(string);
			int j = opRe.deleteOp(string);
			if (i < 0 && j < 0) {
				return "2";
			} 
		

		}
		return "1";
	}

	// 添加操作员
	@Transactional
	@Override
	public String Opadd(Operatorinfo operatorinfo) {
		// 操作表
		operatorinfo.setStatus("2");
		Operatorinfo newop = opRe.save(operatorinfo);
		// 插入登陆表
		Logininfo login = new Logininfo();
		login.setUsername(operatorinfo.getUsername());
		login.setPassword(operatorinfo.getPassword());
		login.setRole("2");
		Logininfo newlogin = loginRe.saveAndFlush(login);
		if (newop.getId() != null && newlogin.getId() != null) {
			return "1";
		} else {
			return "2";
		}

	}

	// 修改员工
	@Transactional
	@Override
	public String Opupdate(Operatorinfo operatorinfo) {
		Operatorinfo o = opRe.findByUsername(operatorinfo.getUsername());
		o.setPassword(operatorinfo.getPassword());
		o.setContact(operatorinfo.getContact());
		o.setName(operatorinfo.getName());
		if (!operatorinfo.getArea().equals("null")) {
			o.setArea(operatorinfo.getArea());
		}
		loginRe.updateLoginPass(operatorinfo.getPassword(), operatorinfo.getUsername());
		return "1";
	}

	// 修改状态
	@Transactional
	@Override
	public String Opstatus(Integer id, String status) {
		int i = opRe.updateOpStatus(status, id);
		if (i > 0) {
			return "1";
		} else {
			return "2";
		}

	}

	// 查询区域内的用户
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	@Override
	public Map<String, Object> OpUser(String username) {
		String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		String rdate = date.substring(0, date.indexOf("-"));
		List<Map<String, Object>> list = opRe.findAreaByusername(username);
		// 获取操作员的区域
		String area = (String) list.get(0).get("area");
		// 获得所有的用户
		List<Userinfo> userinfoList = userRe.findByIdIsNotNull();
		List<Userinfo> newlist = new ArrayList<Userinfo>();
		for (Userinfo userinfo : userinfoList) {
			if (area.contains(userinfo.getProvince())) {
				String idcard = userinfo.getIdcard();
				String year = idcard.substring(6, 10);
				Integer age = Integer.parseInt(rdate) - Integer.parseInt(year);
				userinfo.setAge(age);
				String status = userinfo.getStatus();
				if (status.equals("1")) {
					userinfo.setStatus("已通过");
				} else if (status.equals("2")) {
					userinfo.setStatus("未通过");
				} else {
					userinfo.setStatus("待审核");
				}
				newlist.add(userinfo);
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		if (newlist.size() > 0) {
			map.put("result", "1");
			map.put("users", newlist);
		} else {
			map.put("result", "2");
		}
		return map;
	}
	
	@Override
	public Map<String, Object> opuserDate(Date startdate, Date enddate, String name, String username) {
		return null;
	}

}
