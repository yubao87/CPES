package com.atguigu.cpes.dao;

import java.util.List;

import com.atguigu.cpes.bean.Permission;

public interface PermissionDao {

	List<Permission> queryAll();

	int insertPermission(Permission permission);

	Permission queryById(Integer id);

	int updatePermission(Permission permission);

	int deleteById(Integer id);

}
