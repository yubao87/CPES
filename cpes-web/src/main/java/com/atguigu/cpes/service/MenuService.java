package com.atguigu.cpes.service;

import java.util.List;

import com.atguigu.cpes.bean.Menu;

public interface MenuService {

	Menu queryParentMenu(Integer id);

	List<Menu> queryChildMenu(Integer pid);

	List<Menu> queryAllMenu();

}
