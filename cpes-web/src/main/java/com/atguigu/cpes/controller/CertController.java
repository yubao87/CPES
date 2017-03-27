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
import com.atguigu.cpes.bean.Cert;
import com.atguigu.cpes.common.BaseController;
import com.atguigu.cpes.service.CertService;

@Controller
@RequestMapping("/cert")
public class CertController extends BaseController{
	
	@Autowired
	private CertService certService;
	
	@RequestMapping("/add")
	public String add() {
		return "cert/add";
	}
	
	@RequestMapping("/index")
	public String index() {
		return "cert/index";
	}
	
	/**
	 * 新增角色数据
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/insert")
	public Object insert(Cert cert) {
		
		start();
		
		try {
			certService.insertCert(cert);
			success(true);
		} catch(Exception e) {
			e.printStackTrace();
			success(false);
		}
		
		return end();
	}
	
	@RequestMapping("/edit/{id}")
	public String edit(@PathVariable("id")Integer id,Model model) {
		Cert dbCert = certService.queryCertById(id);
		model.addAttribute("cert", dbCert);
		return "cert/edit";
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
			Page<Cert> page = certService.queryCertPage(paramMap);
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
	 * 修改用户数据
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/update")
	public Object update(Cert cert) {
		
		start();
		try{
			int count = certService.updateCert(cert);
			success(count == 1);
		} catch(Exception e) {
			success(false);
			e.printStackTrace();
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
			int count = certService.deleteCertById(id);
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
			int count = certService.deleteBatCerts(ds);
			success(count == ds.getIds().size());
		} catch(Exception e) {
			e.printStackTrace();
			success(false);
		}
		return end();
	}
}
