package com.talentpool.dao;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.talentpool.po.Logininfo;

public interface LogininfoRepository extends JpaRepository<Logininfo, Integer> {

	// 根据用户名
	Logininfo getByUsername(String username);

	// 根据账号密码查询用户
	Logininfo findByUsernameAndPassword(String username, String password);

	// 根据账号删除用户
	@Modifying
	@Query("DELETE from Logininfo l  WHERE l.username=:username")
	int deleteLogin(@Param("username") String username);

	// 根据用户名更新密码
	@Modifying
	@Query("UPDATE Logininfo l SET l.password =:passwords WHERE l.username=:usernames")
	int updateLoginPass(@Param("passwords") String passowrd, @Param("usernames") String username);
}
