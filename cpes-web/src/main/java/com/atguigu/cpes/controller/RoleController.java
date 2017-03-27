package com.atguigu.cpes.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atguigu.cpes.bean.Datas;
import com.atguigu.cpes.bean.Page;
import com.atguigu.cpes.bean.Role;
import com.atguigu.cpes.common.BaseController;
import com.atguigu.cpes.service.RoleService;

/**
 * ^_^ 2017年3月10日 ^_^ 下午10:41:44 ^_^ dabao ^_^
 * 
 */
@Controller
@RequestMapping("/role")
public class RoleController extends BaseController{
	
	@Autowired
	private RoleService roleService;

	
	@RequestMapping("/index")
	public String index() {
		return "role/index";
	}
	
	@ResponseBody
	@RequestMapping("/doAssign")
	public Object doAssign(Integer roleid, Datas datas) {
		start();
		
		try {
			roleService.insertRolePermissions(roleid,datas);
			success(true);
		} catch(Exception e) {
			e.printStackTrace();
			success(false);
		}
		
		return end();
	}
	
	/**跳转新增角色页面*/
	@RequestMapping("/add")
	public String add() {
		return "role/add";
	}
	
	/**执行新增角色操作*/
	@ResponseBody
	@RequestMapping("/insert")
	public Object insert(Role role) {
		
		start();
		
		try {
			roleService.insertRole(role);
			success(true);
		} catch(Exception e) {
			e.printStackTrace();
			success(false);
		}
		
		return end();
	}
	
	@RequestMapping("/assign/{id}")
	public String assign(@PathVariable("id")Integer id,Model model) {
		Role dbRole = roleService.queryRoleById(id);
		model.addAttribute("role", dbRole);
		return "role/assign";
	}
	
	/**分页查询*/
	@ResponseBody
	@RequestMapping("/pageQuery")
	public Object pageQuery(String querytext ,Integer pageno ,Integer pagesize) {
		
		start();
		
		try {
			
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("start", (pageno-1)*pagesize);
			paramMap.put("size", pagesize);
			paramMap.put("querytext", querytext);
			//当前面参数变了的时候，一般都在controller里面来封装，这样可以省去改后面所有的代码
			Page<Role> page = roleService.queryRolePage(paramMap);
			page.setPageno(pageno);
			page.setPagesize(pagesize);
			param("page", page);
			success(true);
		} catch (Exception e){
			e.printStackTrace();
			success(false);
		}
		
		return end();
	}
	
	/**修改之前查询角色信息edit*/
	@RequestMapping("/edit/{id}")
	public String edit(@PathVariable("id")Integer id,Model model) {
		Role dbRole = roleService.queryRoleById(id);
		model.addAttribute("role", dbRole);
		return "role/edit";
	}
	
	/**执行修改操作*/
	@ResponseBody
	@RequestMapping("/update")
	public Object update(Role role) {
		
		start();
		try{
			int count = roleService.updateRole(role);
			success(count == 1);
		} catch(Exception e) {
			success(false);
			e.printStackTrace();
		}
		
		return end();
	}
	
	/**单独删除角色	 */
	@ResponseBody
	@RequestMapping("/delete")
	public Object delete(Integer id) {
		start();
		
		try {
			int count = roleService.deleteRoleById(id);
			success(count == 1);
		} catch(Exception e) {
			e.printStackTrace();
			success(false);
		}
		return end();
	}
	
	/**
	 * 删除选择的多条用户数据
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/doDeletes")
	public Object doDeletes(Datas ds) {
		start();
		
		try {
			// 把页面传来的多个角色传递 批量删除
			int count = roleService.deleteBatRoles(ds);
			// 根据放回值 判断是否删除成功 i是删除影响多少行 size是判断页面传递的多少role
			success(count == ds.getIds().size());
		} catch(Exception e) {
			e.printStackTrace();
			success(false);
		}
		return end();
	}
}
