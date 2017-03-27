package com.atguigu.cpes.bean;

import java.util.ArrayList;
import java.util.List;

public class Menu {
	private Integer id;
	private String menuName;
	private String url;
	private List<Menu> childMenus = new ArrayList<>();
	private Integer pid;
	
	public Menu() {
	}

	public Menu(Integer id, String menuName, String url, List<Menu> childMenus) {
		this.id = id;
		this.menuName = menuName;
		this.url = url;
		this.childMenus = childMenus;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<Menu> getChildMenus() {
		return childMenus;
	}

	public void setChildMenus(List<Menu> childMenus) {
		this.childMenus = childMenus;
	}

	
	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	@Override
	public String toString() {
		return "Menu [id=" + id + ", menuName=" + menuName + ", url=" + url
				+ ", childMenus=" + childMenus + ", pid=" + pid + "]";
	}

	

}
