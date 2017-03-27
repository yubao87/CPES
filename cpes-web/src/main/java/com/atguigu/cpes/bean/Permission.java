package com.atguigu.cpes.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * ^_^ 2017年3月10日 ^_^ 下午9:45:08 ^_^ dabao ^_^
 * 
 */
public class Permission {
	private Integer id, pid;
	/***/
	private String url, name, icon;
	/** 此处一定要new，否则出现空指针异常*/
	private List<Permission> children = new ArrayList<Permission>();
	/** 菜单树默认不是打开的*/
	private boolean open = true;
	/** 默认不是选中的*/
	private boolean checked = false;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Permission> getChildren() {
		return children;
	}

	public void setChildren(List<Permission> children) {
		this.children = children;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

}
