package com.atguigu.cpes.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atguigu.cpes.bean.Member;
import com.atguigu.cpes.bean.Menu;
import com.atguigu.cpes.bean.Permission;
import com.atguigu.cpes.bean.User;
import com.atguigu.cpes.common.BaseController;
import com.atguigu.cpes.service.MemberService;
import com.atguigu.cpes.service.MenuService;
import com.atguigu.cpes.service.UserService;
import com.atguigu.cpes.util.MD5Util;
/**  
 * ^_^ 2017年3月4日 ^_^ 下午8:30:38 ^_^ dabao ^_^
 * 
 */
@Controller
public class ForwardController extends BaseController{
	@Autowired
	private UserService userService;
	@Autowired
	private MemberService memberService;
	@Autowired
	private MenuService menuService;
	
	// web默认首页调转至指定ftl首页
	@RequestMapping("/index")
	public String index() {
		return "index";
	}
	
/*	@RequestMapping("/login")
	public String login() {
		return "login";
	}
*/	
	// 如果是0  调转至会员首页
	@RequestMapping("/member")
	public String member() {
		return "member";
	}
	// 如果是1 调转至后台管理页面
	@RequestMapping("/main")
	public String main(HttpSession session) {
		
		//查询当前用户的权限菜单
		User user = (User)session.getAttribute("loginUser");
		List<Permission> permissions = userService.queryAssignPermissions(user);
		
//		List<Permission> parentPermissions = new ArrayList<Permission>();
		Permission parentPermissions = null;
		
		//用Map集合组合菜单
		Map<Integer, Permission> permissionMap = new HashMap<Integer, Permission>();
		for (Permission permission : permissions) {
			permissionMap.put(permission.getId(), permission);
			if(permission.getPid() == 0) {
//				parentPermissions.add(permission);
				parentPermissions = permission;
			} else {
				// 父菜单
				Permission parent = permissionMap.get(permission.getPid());
				// 组合父子菜单
				parent.getChildren().add(permission);
			}
		}
		session.setAttribute("permissions", parentPermissions.getChildren());
		return "main";
	}

	// ajax 页面判断是0 验证会员是否存在
	@ResponseBody
	@RequestMapping("/dologinByMember")
	public Object dologinByMember( Member member, HttpSession session,HttpServletResponse response) {
		start();
		try {
			// 设置MD5密码 并查找该用户
			member.setLoginacct(member.getLoginacct().trim());
			member.setUserpswd(MD5Util.digest(member.getUserpswd().trim()));
			Member dbm = memberService.queryMember(member);
			if (dbm != null) {
				param("msg", "登录成功");
				Cookie nameCook = new Cookie("nameCook", dbm.getLoginacct());
				Cookie pswdCook = new Cookie("pswdCook", dbm.getUserpswd());
				nameCook.setMaxAge(60 * 60 * 24 * 7);
				response.addCookie(nameCook);
				pswdCook.setMaxAge(60 * 60 * 24 * 7);
				response.addCookie(pswdCook);
				session.setAttribute("loginMember", dbm);
				success(true);
			} else {
				success(false);
			}
		} catch (Exception e) {
			success(false);
		}
		return end();
	}
	
	// ajax 页面判断是1  后台管理 验证用户密码
	@RequestMapping("/dologin")
	@ResponseBody
	public Object doLogin(User user,String code, String rememberme,HttpSession session,HttpServletResponse response) {
		Map<String, Object> map = new HashMap<>();
		//获取页面输入框中的验证码	获取谷歌生成的验证码
		/*String sessionToken = (String) session.getAttribute(Constants.KAPTCHA_SESSION_KEY);
		//获取之后 移除session中的验证码
		session.removeAttribute(Constants.KAPTCHA_SESSION_KEY);
		if (sessionToken == null) 	sessionToken = "";
//		判断验证码是否正确
		if (!sessionToken.equals(code)) {
			map.put("tf", false);
			map.put("msg","验证码错误，请重新输入");
			return map;
		} */
		// 从页面获取请求参数 	 判断参数是否为空
		user.setLoginacct(user.getLoginacct().trim());
		user.setUserpswd(user.getUserpswd().trim());
		user.setUserpswd(MD5Util.digest(user.getUserpswd()));
		// 调用service 查询
		User dbUser = userService.queryUser(user);
		// 判断返回结果
		if (dbUser == null ) {
			map.put("success", false);
			map.put("msg","用户名或密码错误");
		} else {
			map.put("msg", "登录成功");
			map.put("success", true);
			session.setAttribute("loginUser", dbUser);
			Cookie nameCook = new Cookie("nameCook", dbUser.getLoginacct());
			nameCook.setMaxAge(60 * 60 * 24 * 7);
			response.addCookie(nameCook);
		}
		return map;
	}
	
	// 退出
	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
	
	//采用递归的方式查询菜单
	public void queryChildMenu(Menu parentMenu) {
		List<Menu> childMenus = menuService.queryChildMenu(parentMenu.getId());
		
		for (Menu childmenu : childMenus) {
			queryChildMenu(childmenu);
		}
		
		parentMenu.setChildMenus(childMenus);
	}
}
