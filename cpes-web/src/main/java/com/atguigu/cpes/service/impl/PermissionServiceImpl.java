package com.atguigu.cpes.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.cpes.bean.Permission;
import com.atguigu.cpes.dao.PermissionDao;
import com.atguigu.cpes.service.PermissionService;

@Service
public class PermissionServiceImpl implements PermissionService {
	
	@Autowired
	private PermissionDao permissionDao;

	@Override
	public List<Permission> queryAll() {
		return permissionDao.queryAll();
	}

	@Override
	public int insertPermission(Permission permission) {
		return permissionDao.insertPermission(permission);
	}

	@Override
	public Permission queryById(Integer id) {
		return permissionDao.queryById(id);
	}

	@Override
	public int updatePermission(Permission permission) {
		return permissionDao.updatePermission(permission);
	}

	@Override
	public int deleteById(Integer id) {
		return permissionDao.deleteById(id);
	}
}
