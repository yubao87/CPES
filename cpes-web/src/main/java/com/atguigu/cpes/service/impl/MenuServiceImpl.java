package com.atguigu.cpes.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.cpes.bean.Menu;
import com.atguigu.cpes.dao.MenuDao;
import com.atguigu.cpes.service.MenuService;

@Service
public class MenuServiceImpl implements MenuService {
	
	@Autowired
	private MenuDao menuDao;
	
	@Override
	public Menu queryParentMenu(Integer id) {
		return menuDao.queryParentMenu(id);
	}

	@Override
	public List<Menu> queryChildMenu(Integer pid) {
		return menuDao.queryChildMenu(pid);
	}

	@Override
	public List<Menu> queryAllMenu() {
		return menuDao.queryAllMenu();
	}

}
