package com.atguigu.cpes.service;

import java.util.List;

import com.atguigu.cpes.bean.Permission;

public interface PermissionService {

	List<Permission> queryAll();

	int insertPermission(Permission permission);

	Permission queryById(Integer id);

	int updatePermission(Permission permission);

	int deleteById(Integer id);

}
