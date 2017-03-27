package com.atguigu.cpes.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.cpes.bean.Datas;
import com.atguigu.cpes.bean.Page;
import com.atguigu.cpes.bean.Permission;
import com.atguigu.cpes.bean.User;
import com.atguigu.cpes.dao.UserDao;
import com.atguigu.cpes.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;
	
	@Override
	public void insertUser(User user) {
		userDao.insertUser(user);
	}

	@Override
	public User queryUser(User user) {
		return userDao.queryUser(user);
	}

	@Override
	public Page<User> queryUserPage(Map<String, Object> paramMap) {
		Page<User> page = new Page<User>();
		//查询数据
		List<User> users = userDao.queryUserList(paramMap);
		
		//查询数量
		int count = userDao.queryUserCount(paramMap);
		page.setDatas(users);
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
	public User queryUserById(Integer id) {
		return userDao.queryUserById(id);
	}

	@Override
	public int updateUser(User user) {
		return userDao.update(user);
	}

	@Override
	public int deleteUserById(Integer id) {
		return userDao.deleteUserById(id);
	}

	@Override
	public void deleteUsers(Integer[] ids) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("ids", ids);
		userDao.deleteUsers(paramMap);
	}

	@Override
	public int deleteBatUsers(Datas ds) {
		return userDao.deleteBatUsers(ds);
	}

	@Override
	public List<Integer> queryAssignRole(Integer userid) {
		return userDao.queryAssignRole(userid);
	}

	@Override
	public void assignRoles(Integer userid, Datas ds) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userid", userid);
		for (Integer roleid : ds.getIds()) {
			paramMap.put("roleid", roleid);
			userDao.assignRoles(paramMap);
		}
	}

	@Override
	public void unAssignRoles(Integer userid, Datas ds) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userid", userid);
		paramMap.put("roleids", ds.getIds());
		userDao.unAssignRoles(paramMap);
	}

	@Override
	public List<Permission> queryAssignPermissions(User user) {
		return userDao.queryAssignPermissions(user);
	}

}
