package com.atguigu.cpes.dao;

import java.util.List;

import com.atguigu.cpes.bean.Menu;

public interface MenuDao {

	Menu queryParentMenu(Integer id);

	List<Menu> queryChildMenu(Integer pid);

	List<Menu> queryAllMenu();

}
