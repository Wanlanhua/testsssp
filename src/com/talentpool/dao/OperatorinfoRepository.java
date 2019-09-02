package com.talentpool.dao;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.talentpool.po.Operatorinfo;

public interface OperatorinfoRepository extends JpaRepository<Operatorinfo, Integer> {

	// 根据用户名查找操作员
	Operatorinfo findByUsername(String username);

	// 查询所有的操作员
	List<Operatorinfo> findByIdIsNotNull();

	// 删除操作员
	@Modifying
	@Query("DELETE from Operatorinfo o  WHERE o.username=:username")
	int deleteOp(@Param("username") String username);

	// 更改员工的状态
	@Modifying
	@Query("UPDATE Operatorinfo o SET o.status =:status WHERE o.id=:id")
	int updateOpStatus(@Param("status") String status, @Param("id") Integer id);

	// 查询操作员的区域
	@Query("select new map(o.area) from Operatorinfo o where o.username=?1")
	List<Map<String, Object>> findAreaByusername(String username);

	// 查询员工的id
	@Query("select new map(o.id) from Operatorinfo o where o.username=?1")
	List<Map<String, Object>> findIdByusername(String username);

}
