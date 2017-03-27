package com.atguigu.cpes.interceptor;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.atguigu.cpes.bean.Permission;
import com.atguigu.cpes.bean.User;

public class AuthInterceptor extends HandlerInterceptorAdapter{

	/**
	 * 目标控制器运行之前
	 */
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
		String uri = request.getRequestURI();
		
		List<String> urls = new ArrayList<String>();
		
		urls.add("/dologin.do");
		
		if(!urls.contains(uri)) {
			//判断会话中的用户数据是否存在
			HttpSession httpSession = request.getSession();
			User user = (User)httpSession.getAttribute("user");
			//如果不存在，跳转回登录页面
			if(user == null) {
				response.sendRedirect("/");
				return false;
			}
			
			if(!uri.equals("/main.htm")) {
				//如果存在继续执行
				@SuppressWarnings("unchecked")
				List<Permission> permissions = (List<Permission>) request.getAttribute("permissions");
				List<String> authURLs = new ArrayList<>();
				for (Permission permission : permissions) {
					authURLs.add(permission.getUrl());
					for (Permission permission2 : permission.getChildren()) {
						authURLs.add(permission2.getUrl());
					}
				}
				
				//判断当前用户的权限是否合法
				if(!authURLs.contains(uri)) {
					response.sendRedirect("/error.jsp");
					return false;
				}
			}
			
		}
		
		return true;
	}

	
	
}
