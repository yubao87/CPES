package com.atguigu.cpes.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.atguigu.cpes.bean.Cert;
import com.atguigu.cpes.bean.Certtype;
import com.atguigu.cpes.common.BaseController;
import com.atguigu.cpes.service.CertService;
import com.atguigu.cpes.service.CerttypeService;

@Controller
@RequestMapping("/certtype")
public class CerttypeController extends BaseController {

	@Autowired
	private CertService certService;
	@Autowired
	private CerttypeService certtypeService;
	
	@RequestMapping("index")
	public String index(Model model) {
		//查询
		List<Cert> certs = certService.queryAll();
		model.addAttribute("certs", certs);
		
		//获取已经分类的资质数据的两个属性
		List<Certtype> certtypes = certtypeService.queryAll();
		model.addAttribute("certtypes", certtypes);
		return "certtype/index";
	}
	
	@RequestMapping("/insert")
	public Object insert( String acctype, Integer certid) {
		
		start();
		
		try {
			
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("acctype", acctype);
			paramMap.put("certid", certid);
			
			certService.insertAccCert(paramMap);
			
			success(true);
		} catch(Exception e) {
			e.printStackTrace();
			success(false);
		}
		
		return end();
	}
	@RequestMapping("/delete")
	public Object delete( String acctype, Integer certid) {
		
		start();
		
		try {
			
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("acctype", acctype);
			paramMap.put("certid", certid);
			
			certService.deleteAccCert(paramMap);
			
			success(true);
		} catch(Exception e) {
			e.printStackTrace();
			success(false);
		}
		
		return end();
	}
}
