package com.atguigu.cpes.dao;

import java.util.List;
import java.util.Map;

import com.atguigu.cpes.bean.Datas;
import com.atguigu.cpes.bean.Role;

public interface RoleDao {

	List<Role> queryRoleList(Map<String, Object> paramMap);

	int queryRoleCount(Map<String, Object> paramMap);

	Role queryRoleById(Integer id);

	void insertRole(Role role);

	int updateRole(Role role);

	int deleteRoleById(Integer id);

	int deleteBatRoles(Datas ds);

	List<Role> queryAll();

	void insertRolePermissions(Map<String, Object> paramMap);

	void deleteByRoleid(Integer roleid);

	List<Integer> queryPermissionsByRoleid(Integer roleid);

}
