package com.atguigu.cpes.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atguigu.cpes.bean.Permission;
import com.atguigu.cpes.common.BaseController;
import com.atguigu.cpes.service.PermissionService;
import com.atguigu.cpes.service.RoleService;

/**  
 * ^_^ 2017年3月11日 ^_^ 下午3:11:42 ^_^ dabao ^_^
 * 
 */
@Controller
@RequestMapping("/permission")
public class PermissionController extends BaseController {
	
	@Autowired
	private PermissionService permissionService;
	@Autowired
	private RoleService roleService;
	
	@RequestMapping("/index")
	public String index() {
		return "permission/index";
	}
	
	/**通过单击新增按钮跳转至模态框页面*/
	@RequestMapping("/add")
	public String add() {
		return "permission/add";
	}
	
	@RequestMapping("/edit/{id}")
	public String edit(@PathVariable("id")Integer id, Model model) {
		
		Permission permission = permissionService.queryById(id);
		model.addAttribute("permission", permission);
		
		return "permission/edit";
	}
	
	@ResponseBody
	@RequestMapping("/insert")
	public Object insert(Permission permission) {
		start();
		
		try {
			int count = permissionService.insertPermission(permission);
			success(count == 1);
		} catch(Exception e) {
			e.printStackTrace();
			success(false);
		}
		
		return end();
	}
	
	@ResponseBody
	@RequestMapping("/update")
	public Object update(Permission permission) {
		start();
		
		try {
			int count = permissionService.updatePermission(permission);
			success(count == 1);
		} catch(Exception e) {
			e.printStackTrace();
			success(false);
		}
		
		return end();
	}
	
	@ResponseBody
	@RequestMapping("delete")
	public Object delete(Integer id) {
		
		start();
		
		try {
			int count = permissionService.deleteById(id);
			success(count == 1);
		} catch (Exception e) {
			e.printStackTrace();
			success(false);
		}
		
		return end();
	}
	
	@ResponseBody
	@RequestMapping("/loadDatas")
	public Object loadDatas( Integer roleid) {
		
		//查询当前角色已经分配的许可信息
		List<Integer> permissionids = roleService.queryPermissionsByRoleid(roleid);
		
		List<Permission> permissions = permissionService.queryAll();
		
		Map<Integer, Permission> permissionMap = new HashMap<Integer, Permission>();
		
		List<Permission> roots = new ArrayList<Permission>();
		
		for (Permission permission : permissions) {
			if(permissionids.contains(permission.getId())) {
				permission.setChecked(true);
			}
			permissionMap.put(permission.getId(), permission);
			
			if(permission.getPid() == 0) {
				roots.add(permission);
			} else {
				Permission parentPermission = permissionMap.get(permission.getPid());
				parentPermission.getChildren().add(permission);
			}
		}
		
		return roots;
	}
	
	@ResponseBody
	@RequestMapping("/tree")
	public Object tree() {
		start();
		
		try {
			
			List<Permission> permissions = permissionService.queryAll();
			
			Map<Integer, Permission> permissionMap = new HashMap<Integer, Permission>();
			List<Permission> roots = new ArrayList<Permission>();
			for (Permission permission : permissions) {
				permissionMap.put(permission.getId(), permission);
				
				if(permission.getPid() == 0) {
					roots.add(permission);
				} else {
					Permission parentPermission = permissionMap.get(permission.getPid());
					// 组合父子节点的关系
					parentPermission.getChildren().add(permission);
				}
			}
			param("permissions", roots);
			success(true);
		} catch(Exception e) {
			e.printStackTrace();
			success(false);
		}
		
		
		return end();
	}
}
