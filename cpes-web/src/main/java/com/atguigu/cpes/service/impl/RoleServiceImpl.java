package com.atguigu.cpes.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.cpes.bean.Datas;
import com.atguigu.cpes.bean.Page;
import com.atguigu.cpes.bean.Role;
import com.atguigu.cpes.dao.RoleDao;
import com.atguigu.cpes.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {
	@Autowired
	private RoleDao roleDao;

	@Override
	public Page<Role> queryRolePage(Map<String, Object> paramMap) {
		Page<Role> page = new Page<Role>();
		//查询数据
		List<Role> roles = roleDao.queryRoleList(paramMap);
		
		//查询数量
		int count = roleDao.queryRoleCount(paramMap);
		page.setDatas(roles);
		page.setTotalsize(count);
		
		Integer pagesize = (Integer) paramMap.get("size");
		if(count % pagesize == 0) {
			page.setTotalno(count / pagesize);
		} else {
			page.setTotalno(count / pagesize + 1);
		}
		return page;
	}

	@Override
	public Role queryRoleById(Integer id) {
		return roleDao.queryRoleById(id);
	}

	@Override
	public void insertRole(Role role) {
		roleDao.insertRole(role);
	}

	@Override
	public int updateRole(Role role) {
		return roleDao.updateRole(role);
	}

	@Override
	public int deleteRoleById(Integer id) {
		return roleDao.deleteRoleById(id);
	}

	@Override
	public int deleteBatRoles(Datas ds) {
		return roleDao.deleteBatRoles(ds);
	}

	@Override
	public List<Role> queryAll() {
		return roleDao.queryAll();
	}

	@Override
	public void insertRolePermissions(Integer roleid, Datas datas) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("roleid", roleid);
		
		roleDao.deleteByRoleid(roleid);
		
		for (Integer permissionid : datas.getIds()) {
			paramMap.put("permissionid", permissionid);
			roleDao.insertRolePermissions(paramMap);
		}
	}

	@Override
	public List<Integer> queryPermissionsByRoleid(Integer roleid) {
		return roleDao.queryPermissionsByRoleid(roleid);
	}
}
