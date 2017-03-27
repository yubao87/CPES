package com.atguigu.cpes.bean;

/**
 * ^_^ 2017年3月3日 ^_^ 下午7:13:14 ^_^ dabao ^_^
 * 
 */
public class User {
	
	private Integer id;
	private String loginacct;
	private String userpswd;
	private String createtime;
	private String username;
	private String email;
	
	public User(String loginacct, String userpswd) {
		this.loginacct = loginacct;
		this.userpswd = userpswd;
	}

	public User() {
	}

	public String getLoginacct() {
		return loginacct;
	}

	public void setLoginacct(String loginacct) {
		this.loginacct = loginacct;
	}

	public String getUserpswd() {
		return userpswd;
	}

	public void setUserpswd(String userpswd) {
		this.userpswd = userpswd;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", loginacct=" + loginacct + ", userpswd="
				+ userpswd + ", createtime=" + createtime + ", username="
				+ username + ", email=" + email + "]";
	}


}
