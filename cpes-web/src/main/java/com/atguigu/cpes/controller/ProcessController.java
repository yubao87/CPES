package com.atguigu.cpes.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.atguigu.cpes.bean.Page;
import com.atguigu.cpes.common.BaseController;
import com.mchange.v1.identicator.test.TestIdHashSet;

/** 
  ^_^ 2017年3月18日 ^_^ 上午10:04:48 ^_^ 
 */
@Controller
@RequestMapping("/process")
public class ProcessController extends BaseController {
	
	@Autowired
	private RepositoryService repositoryService;
	
	@RequestMapping("/index")
	public String index() {
		return "process/index";
	}
	
/*	@RequestMapping("/loadImg")//'myProcess:3:804'
	public void loadImg( String pdid, HttpServletResponse resp ) throws Exception {

		// 读取流程定义
//		ProcessDefinition pd =
//			repositoryService
//			    .createProcessDefinitionQuery()
//			    .processDefinitionId(pdid)
//			    .singleResult();
		ProcessDefinitionQuery query = repositoryService.createProcessDefinitionQuery();
		ProcessDefinitionQuery query2 = query.processDefinitionId(pdid);
		ProcessDefinition pd = query2.singleResult();
		ProcessDefinition pd1 = repositoryService.createProcessDefinitionQuery()
				.processDefinitionId(pdid).singleResult();
		InputStream in =
			repositoryService
			    .getResourceAsStream(pd.getDeploymentId(), pd.getDiagramResourceName());
		OutputStream out = resp.getOutputStream();
		int i = -1;
		while ( (i = in.read()) != -1 ) {
			out.write(i);
		}
		in.close();
	}*/
	
	// 加载流程定义图片
	@RequestMapping("/loadImg")
	public void loadImg(String pdid, HttpServletResponse resp) {
		// 读取流程定义
		ProcessDefinition pd = repositoryService.createProcessDefinitionQuery()
				.processDefinitionId(pdid).singleResult();
		InputStream in = null;
		OutputStream out = null;
		try {
			in = repositoryService
					.getResourceAsStream(pd.getDeploymentId(), pd.getDiagramResourceName());
			out = resp.getOutputStream();
			int i = -1;
			while ((i = in.read()) != -1) {
				out.write(i);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	// 展示流程定义图片
	@RequestMapping("/showImg")
	public String showImg(String pdid, Model model) {
		model.addAttribute("pdid",pdid);
		return "process/showImg";
	}
	
	// 删除单个流程定义信息 框架没有提供删除流程定义的方法，可以通过删除部署数据，级联删除流程定义
	@ResponseBody
	@RequestMapping("/delete")
	public Object delete(String id) {//myProcess:2:704
		start();
		try {
			// 查询流程定义信息
			ProcessDefinition pd = repositoryService.createProcessDefinitionQuery()
					.processDefinitionId(id).singleResult();
			// 删除流程定义数据 是否是级联删除
			repositoryService.deleteDeployment(pd.getDeploymentId(),true);
			success(true);
		} catch (Exception e) {
			success(false);
		}
		return end();
	}
	
	// 部署流程定义 即上传流程文件
	@ResponseBody
	@RequestMapping("/deployProcDef")
	public Object deployProcDef(HttpServletRequest req) {
		start();
		try {
			// 文件上传的时候，请求对象那个被框架进行了特殊的包装，用于处理文件上传
			MultipartHttpServletRequest request = (MultipartHttpServletRequest) req;
			MultipartFile file = request.getFile("procDefFile");
			// 将上传的文件部署到流程框架中(获取上传文件的流 通过流来部署流程定义)
			repositoryService.createDeployment()
				.addInputStream(file.getOriginalFilename(), file.getInputStream())
				.deploy();
			/*// 获取的是文件上传时表单元素名称（name属性值）
			System.out.println(file.getName());
			// h获取的是上传的实际文件名
			System.out.println(file.getOriginalFilename());
			// testid 在入参处传入
			System.out.println("testid = " + testid);*/
			success(true);
		} catch (Exception e) {
			success(false);
		}
		return end();
	}
	
	@ResponseBody
	@RequestMapping("/pageQuery")
	public Object pageQuery(Integer pageno, Integer pagesize) {
		start();
		try {
			// 查询流程定义的数据
			ProcessDefinitionQuery query = repositoryService.createProcessDefinitionQuery();
			List<ProcessDefinition> pds = query.listPage((pageno -1)*pagesize, pagesize);
			// 流程定义的数量 最多一般不会超过50的
			int count = (int) query.count();
//			Page<ProcessDefinition> pdPage = new Page<ProcessDefinition>();
			Page<Map<String, Object>> pdMapPage = new Page<>();
			List<Map<String, Object>> pdMaps = new ArrayList<>();
			for (ProcessDefinition pd : pds) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", pd.getId());
				map.put("key", pd.getKey());
				map.put("name", pd.getName());
				map.put("version", pd.getVersion());
				pdMaps.add(map);
			}
			pdMapPage.setDatas(pdMaps);
			pdMapPage.setTotalsize(count);
			param("page", pdMapPage);
			success(true);
		} catch (Exception e) {
			success(false);
		}
		return end();
	}

}
