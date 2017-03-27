package com.atguigu.cpes.bean;

import java.util.List;

/**
 * ^_^ 2017年3月10日 ^_^ 下午8:33:26 ^_^ dabao ^_^ 该类用于封装传递的多个参数
 */
public class Datas {

	/** 传递多个用户比如id name等等 可以用它封装，然后在controller入参出接收 */
	private List<User> users;
	/** 使用ajax请求发送多个id时使用 */
	private List<Integer> ids;

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public List<Integer> getIds() {
		return ids;
	}

	public void setIds(List<Integer> ids) {
		this.ids = ids;
	}



}
