package com.atguigu.cpes.service;

import java.util.List;
import java.util.Map;

import com.atguigu.cpes.bean.Datas;
import com.atguigu.cpes.bean.Page;
import com.atguigu.cpes.bean.Role;

public interface RoleService {

	Page<Role> queryRolePage(Map<String, Object> paramMap);

	Role queryRoleById(Integer id);

	void insertRole(Role role);

	int updateRole(Role role);

	int deleteRoleById(Integer id);

	int deleteBatRoles(Datas ds);

	List<Role> queryAll();

	void insertRolePermissions(Integer roleid, Datas datas);

	List<Integer> queryPermissionsByRoleid(Integer roleid);

}
