package com.talentpool.serviceipm;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.talentpool.dao.LogininfoRepository;
import com.talentpool.dao.userinfoRepository;
import com.talentpool.po.Logininfo;
import com.talentpool.po.Userinfo;
import com.talentpool.service.userService;

@Service
public class userserviceIpm implements userService {

	@Autowired
	private userinfoRepository userRe;
	@Autowired
	private LogininfoRepository loginRe;

	// 用户更新
	@Transactional
	@Override
	public Map<String, String> zcmaintenance(MultipartFile[] itemspics, HttpServletRequest request, String image_name,
			String usernames) {
		Map<String, String> map = new HashMap<String, String>();
		String icon = "";
		// 插入更新
		// 上传图片长度
		int leng = itemspics.length;
		// 存取路径的文件夹
		// String picpath="F:\\testpic\\";
		String picpath = request.getSession().getServletContext().getRealPath("") + "pic\\";
		// 设置允许上传文件类型
		String suffixList = ".jpg,.png,.ico,.bmp,.jpeg";
		for (int j = 0; j < leng; j++) {
			MultipartFile file = itemspics[j];
			if (!file.isEmpty()) {
				// 原名
				String yuanshiName = file.getOriginalFilename();
				// 后缀名
				String hzm = yuanshiName.substring(yuanshiName.lastIndexOf("."));
				if (suffixList.contains(hzm.trim().toLowerCase())) {
					// 新名
					String newName = "";
					if (image_name.equals("user_icon")) {
						newName = usernames + 0 + hzm;
						icon = "3";
					} else if (image_name.equals("idcard_z")) {
						newName = usernames + 1 + hzm;
						icon = "1";
					} else if (image_name.equals("idcard_f")) {
						newName = usernames + 2 + hzm;
						icon = "1";
					} else if (image_name.equals("work")) {
						newName = usernames + 3 + hzm;
						icon = "1";
					} else {
						newName = usernames + 4 + hzm;
						icon = "1";
					}
					map.put("newname", "pic/" + newName);
					if (!new File(picpath + newName).exists()) {
						new File(picpath + newName).mkdirs();
					}
					File newfile = new File(picpath + newName);
					try {
						file.transferTo(newfile);
					} catch (IllegalStateException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else {
					return null;
				}
			}

		}

		map.put("result", icon);
		return map;
	}

	// 删除
	@Transactional
	@Override
	public String userDelete(String[] ids) {
		for (String string : ids) {
			int i = userRe.deleteUserinfo(string);
			int j = loginRe.deleteLogin(string);
			if (i < 0 && j < 0) {
				return "2";
			} 
		}

		return "1";
	}

	// 审核
	@Transactional
	@Override
	public String userShen(String[] id, String status) {
		for (String string : id) {
			int i = userRe.updateUserStatus(string, status);
			if (i < 0) {
				return "2";
		}
		}
		return "1";
	}

	// 查询所有
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	@Override
	public Map<String, Object> userQuery() {
		List<Userinfo> list = userRe.findByIdIsNotNull();
		// 当前日期
		String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		String rdate = date.substring(0, date.indexOf("-"));
		for (Userinfo userinfo : list) {
			String status = userinfo.getStatus();
			if (status.equals("1")) {
				userinfo.setStatus("已通过");
			} else if (status.equals("2")) {
				userinfo.setStatus("未通过");
			} else {
				userinfo.setStatus("待审核");
			}
			String idcard = userinfo.getIdcard();
			String year = idcard.substring(6, 10);
			Integer age = Integer.parseInt(rdate) - Integer.parseInt(year);
			userinfo.setAge(age);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		if (list.size() <= 0) {
			map.put("result", "2");
		} else {
			map.put("result", "1");
			map.put("users", list);
		}
		return map;
	}

	// 根据账号查询用户
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	@Override
	public Userinfo userQueryByname(String username) {
		// 当前日期
		String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		String rdate = date.substring(0, date.indexOf("-"));
		Userinfo userinfo = userRe.findByUsername(username);
		// 计算年龄
		String idcard = userinfo.getIdcard();
		String year = idcard.substring(6, 10);
		Integer age = Integer.parseInt(rdate) - Integer.parseInt(year);
		userinfo.setAge(age);
		return userinfo;
	}

	// 更改密码
	@Transactional
	@Override
	public String userUpdatePass(String oldpass, String newpass, String username) {
		Logininfo logininfo = loginRe.findByUsernameAndPassword(username, oldpass);
		if (logininfo == null) {
			return "3";
		} else {
			logininfo.setPassword(newpass);
			int i = userRe.updateUserinfo(username, newpass);
			logininfo.setPassword(newpass);
			Logininfo newlogininfo = loginRe.save(logininfo);
			if (i > 0 && newlogininfo.getId() != null) {
				return "1";
			} else {
				return "2";
			}
		}

	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	@Override
	public Map<String, Object> userQueryBydate(Date startdate, Date enddate, String name) {
		Map<String, Object> map = userQuery();
		List<Userinfo> list = (List<Userinfo>) map.get("users");
		Map<String, Object> map2 = new HashMap<String, Object>();

		if (startdate == null && enddate == null) {
			map2.put("result", "1");
			map2.put("users", list);
			return map2;
		}
		if (name != null) {
			List<Userinfo> newlist = new ArrayList<Userinfo>();
			for (Userinfo userinfo : list) {
				if (userinfo.getJoindate().after(startdate) && userinfo.getJoindate().before(enddate)) {
					if (userinfo.getName().contains(name)) {
						newlist.add(userinfo);
					}

				} else if (userinfo.getJoindate().compareTo(startdate) == 0
						|| userinfo.getJoindate().compareTo(enddate) == 0) {
					if (userinfo.getName().contains(name)) {
						newlist.add(userinfo);
					}
				}
			}
			if (newlist.size() > 0) {
				map2.put("result", "1");
				map2.put("users", newlist);
				return map2;
			} else {
				map2.put("result", "2");
				return map2;
			}
		} else {
			List<Userinfo> newlist = new ArrayList<Userinfo>();
			for (Userinfo userinfo : list) {
				if (userinfo.getJoindate().after(startdate) && userinfo.getJoindate().before(enddate)) {
					newlist.add(userinfo);
				} else if (userinfo.getJoindate().compareTo(startdate) == 0
						|| userinfo.getJoindate().compareTo(enddate) == 0) {
					newlist.add(userinfo);
				}
			}
			if (newlist.size() > 0) {
				map2.put("result", "1");
				map2.put("users", newlist);
				return map2;
			} else {
				map2.put("result", "2");
				return map2;
			}
		}
	}

	// 用户更改自己的文字信息
	@Transactional
	@Override
	public Map<String, String> updateWenzi(HttpServletRequest request, HttpServletResponse response,
			Userinfo userinfo) {
		Map<String, String> map = new HashMap<String, String>();
		if (userinfo.getUsername() != null) {
			String[] gzds = userinfo.getGzd().split("-");
			userinfo.setProvince(gzds[0]);
			userinfo.setCity(gzds[1]);
			userinfo.setArea(gzds[2]);
			userinfo.setId(19);
			Userinfo newu = userRe.save(userinfo);
			if (newu.getId() != null) {
				map.put("result", "1");
			} else {
				map.put("result", "2");
			}
		}
		return map;
	}

}
