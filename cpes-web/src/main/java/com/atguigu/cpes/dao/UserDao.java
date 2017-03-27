package com.atguigu.cpes.dao;

import java.util.List;
import java.util.Map;

import com.atguigu.cpes.bean.Datas;
import com.atguigu.cpes.bean.Permission;
import com.atguigu.cpes.bean.User;

public interface UserDao {

	User queryUser(User user);

	List<User> queryUserList(Map<String, Object> paramMap);

	int queryUserCount(Map<String, Object> paramMap);

	void insertUser(User user);

	User queryUserById(Integer id);

	int update(User user);

	int deleteUserById(Integer id);

	void deleteUsers(Map<String, Object> paramMap);

	int deleteBatUsers(Datas ds);

	List<Integer> queryAssignRole(Integer userid);

	void assignRoles(Map<String, Object> paramMap);

	void unAssignRoles(Map<String, Object> paramMap);

	List<Permission> queryAssignPermissions(User user);

}
