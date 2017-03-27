package com.atguigu.cpes.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
import com.atguigu.cpes.bean.User;
import com.atguigu.cpes.common.BaseController;
import com.atguigu.cpes.service.RoleService;
import com.atguigu.cpes.service.UserService;
import com.atguigu.cpes.util.MD5Util;

/**  
 * ^_^ 2017年3月3日 ^_^ 下午7:14:21 ^_^ dabao ^_^
 * 
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController{

	@Autowired
	private UserService userviService;
	
	@Autowired
	private RoleService roleService;
	
	@RequestMapping("/add")
	public String add() {
		return "user/add";
	}
	
	@RequestMapping("/edit/{id}")
	public String edit(@PathVariable("id")Integer id,Model model) {
		User dbUser = userviService.queryUserById(id);
		model.addAttribute("user", dbUser);
		return "user/edit";
	}
	
	@RequestMapping("/assign/{id}")
	public String assign(@PathVariable("id")Integer id,Model model) {
		// 根据用户id查询数据
		User dbUser = userviService.queryUserById(id);
		model.addAttribute("user", dbUser);
		
		//查询所有的角色的数据
		List<Role> roles = roleService.queryAll();
		
		//查询当前用户已经分配的角色
		List<Integer> roleids = userviService.queryAssignRole(id);
		
		List<Role> assignList = new ArrayList<Role>();
		List<Role> unAssignList = new ArrayList<Role>();
		
		//获取未分配的角色
		for (Role role : roles) {
			if(roleids.contains(role.getId())) {
				assignList.add(role);
			} else {
				unAssignList.add(role);
			}
		}
		model.addAttribute("assignList", assignList);
		model.addAttribute("unAssignList", unAssignList);
		
		return "user/assign";
	}
	
	/**
	 * 修改用户数据
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/update")
	public Object update(User user) {
		start();
		try{
			int count = userviService.updateUser(user);
			success(count == 1);
		} catch(Exception e) {
			success(false);
			e.printStackTrace();
		}
		
		return end();
	}
	
	/**
	 * 新增用户数据
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/insert")
	public Object insert(User user) {
		start();
		
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String date = sdf.format(new Date());
			user.setCreatetime(date);
			user.setUserpswd(MD5Util.digest(user.getUserpswd()));
			userviService.insertUser(user);
			success(true);
		} catch(Exception e) {
			e.printStackTrace();
			success(false);
		}
		return end();
	}
	
	@RequestMapping("/index")
	public String index() {
		
		//查询用户的分页对象
//		Page<User> page = userviService.queryUserPage(pageno, pagesize);
//		page.setPageno(pageno);
//		page.setPagesize(pagesize);
//		model.addAttribute("userpage", page);
		return "user/index";
	}
	
	@ResponseBody
	@RequestMapping("/assignRoles")
	public Object assignRoles(Integer userid, Datas ds) {
		start();
		
		try {
			userviService.assignRoles(userid, ds);
			success(true);
		} catch(Exception e) {
			e.printStackTrace();
			success(false);
		}
		return end();
	}
	
	@ResponseBody
	@RequestMapping("/unAssignRoles")
	public Object unAssignRoles(Integer userid, Datas ds) {
		start();
		
		try {
			userviService.unAssignRoles(userid, ds);
			success(true);
		} catch(Exception e) {
			e.printStackTrace();
			success(false);
		}
		return end();
	}
	
	
	/**
	 * 查询用户数据
	 * @param querytext
	 * @param pageno
	 * @param pagesize
	 * @return
	 */
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
			Page<User> page = userviService.queryUserPage(paramMap);
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
	
	
	/**
	 * 删除用户数据
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/delete")
	public Object delete(Integer id) {
		start();
		
		try {
			int count = userviService.deleteUserById(id);
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
			int count = userviService.deleteBatUsers(ds);
			success(count == ds.getUsers().size());
		} catch(Exception e) {
			e.printStackTrace();
			success(false);
		}
		return end();
	}
	
}
