package com.talentpool.serviceipm;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.talentpool.dao.LogininfoRepository;
import com.talentpool.dao.userinfoRepository;
import com.talentpool.po.Logininfo;
import com.talentpool.po.Userinfo;
import com.talentpool.service.OtherService;

@Service
public class OtherServiceIpm implements OtherService {
	@Autowired
	LogininfoRepository loginRe;
	@Autowired
	userinfoRepository userRe;

	// 登陆
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public Map<String, String> Login(String username, String password, HttpServletRequest request) {
		Logininfo logininfo = loginRe.findByUsernameAndPassword(username, password);
		Map<String, String> map = new HashMap<String, String>();
		if (logininfo != null) {
			map.put("result", "1");
			map.put("role", logininfo.getRole());
			HttpSession session = request.getSession();
			session.setAttribute("username", username);
			return map;
		} else {
			map.put("result", "2");
			return map;
		}
	}

	// 校验
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	@Override
	public String jiaoyan(String username) {
		Logininfo logininfo = loginRe.getByUsername(username);
		if (logininfo != null) {
			return "1";
		} else {
			return "0";
		}
	}

	// 注册
	@Transactional
	@Override
	public String registered(Userinfo userinfo, HttpServletRequest request, MultipartFile user_icon,
			MultipartFile idcard_z, MultipartFile career, MultipartFile idcard_f) {
		// 1.***********先将用户名和密码插入登陆表中
		Logininfo logininfo = new Logininfo();
		logininfo.setUsername(userinfo.getUsername());
		logininfo.setPassword(userinfo.getPassword());
		logininfo.setRole("3");
		// logininfoMapper.insertSelective(logininfo);
		// 保存实体
		Logininfo newlogin = loginRe.save(logininfo);
		String picpath = request.getSession().getServletContext().getRealPath("") + "pic\\";
		// 原名
		String user_iconName = user_icon.getOriginalFilename();
		String idcard_zName = idcard_z.getOriginalFilename();
		String idcard_fName = idcard_f.getOriginalFilename();
		String careerName = career.getOriginalFilename();
		// 后缀名
		String user_iconhzm = user_iconName.substring(user_iconName.lastIndexOf("."));
		String idcard_zhzm = idcard_zName.substring(idcard_zName.lastIndexOf("."));
		String idcard_fhzm = idcard_fName.substring(idcard_fName.lastIndexOf("."));
		String careerhzm = careerName.substring(careerName.lastIndexOf("."));
		// 新名
		String newuser_icon = userinfo.getUsername() + 0 + user_iconhzm;
		String newidcard_z = userinfo.getUsername() + 1 + idcard_zhzm;
		String newidcard_f = userinfo.getUsername() + 2 + idcard_fhzm;
		String newcareer = userinfo.getUsername() + 4 + careerhzm;
		if (!new File(picpath).exists()) {
			new File(picpath).mkdirs();
		}
		File newfile1 = new File(picpath + newuser_icon);
		File newfile2 = new File(picpath + newidcard_z);
		File newfile3 = new File(picpath + newidcard_f);
		File newfile5 = new File(picpath + newcareer);
		try {
			user_icon.transferTo(newfile1);
			idcard_z.transferTo(newfile2);
			idcard_f.transferTo(newfile3);
			career.transferTo(newfile5);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		userinfo.setHead(newuser_icon);
		userinfo.setIdcardphotoz(newidcard_z);
		userinfo.setIdcardphotof(newidcard_f);
		userinfo.setJobphoto(newcareer);

		// 3************工作地的划分
		String gzd = userinfo.getGzd();
		String[] gzds = gzd.split("-");
		userinfo.setProvince(gzds[0]);
		userinfo.setCity(gzds[1]);
		userinfo.setArea(gzds[2]);
		userinfo.setStatus("3");
		userinfo.setJoindate(new Date());
		// 保存userinfo
		Userinfo newuserinfo = userRe.save(userinfo);
		if (newlogin.getId() == null && newuserinfo.getId() == null) {
			return "2";
		} else {
			return "1";
		}

	}

}
