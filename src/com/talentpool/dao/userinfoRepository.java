package com.talentpool.dao;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.talentpool.po.Userinfo;

public interface userinfoRepository extends JpaRepository<Userinfo, Integer> {

	// 查询所有
	List<Userinfo> findByIdIsNotNull();

	// 根据账号查询用户
	Userinfo findByUsername(String username);
	// 根据账号查询用户（返回部分字段）
	// @Query("select new Userinfo(u.id,u.username,u.password) from Userinfo u where
	// u.username=?1")
	// Userinfo findByusernameb(String username);
	// @Query("select new map(u.id,u.username,u.password) from Userinfo u where
	// u.username=?1")
	// List<Map<String, Object>> findByusernameb(String username);

	// 更新部分的字段(更改密码)
	@Modifying
	@Query("UPDATE Userinfo u SET u.password =:password WHERE u.username=:username")
	int updateUserinfo(@Param("username") String username, @Param("password") String password);

	// 根据username删除用户
	@Modifying
	@Query("DELETE from Userinfo u  WHERE u.username=:username")
	int deleteUserinfo(@Param("username") String username);

	@Modifying
	@Query("UPDATE Userinfo u SET u.status =:status WHERE u.username=:username")
	int updateUserStatus(@Param("username") String username, @Param("status") String status);
}
