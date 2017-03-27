package com.atguigu.cpes.service;

import java.util.List;
import java.util.Map;

import com.atguigu.cpes.bean.Datas;
import com.atguigu.cpes.bean.Page;
import com.atguigu.cpes.bean.Permission;
import com.atguigu.cpes.bean.User;

public interface UserService {
	
	public void insertUser(User user);

	public User queryUser(User user);

	public Page<User> queryUserPage(Map<String, Object> paramMap);

	public User queryUserById(Integer id);

	public int updateUser(User user);

	public int deleteUserById(Integer id);

	public void deleteUsers(Integer[] ids);

	public int deleteBatUsers(Datas ds);

	public List<Integer> queryAssignRole(Integer id);

	public void assignRoles(Integer userid, Datas ds);

	public void unAssignRoles(Integer userid, Datas ds);

	public List<Permission> queryAssignPermissions(User user);

}
