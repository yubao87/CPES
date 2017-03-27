package ${packageName}.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ${packageName}.bean.Datas;
import ${packageName}.bean.Page;
import ${packageName}.bean.${className?cap_first};
import ${packageName}.common.BaseController;
import ${packageName}.service.${className?cap_first}Service;

@Controller
@RequestMapping("/${className}")
public class ${className?cap_first}Controller extends BaseController{
	
	@Autowired
	private ${className?cap_first}Service ${className}Service;
	
	@RequestMapping("/add")
	public String add() {
		return "${className}/add";
	}
	
	@RequestMapping("/index")
	public String index() {
		return "${className}/index";
	}
	
	/**
	 * 新增角色数据
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/insert")
	public Object insert(${className?cap_first} ${className}) {
		
		start();
		
		try {
			${className}Service.insert${className?cap_first}(${className});
			success(true);
		} catch(Exception e) {
			e.printStackTrace();
			success(false);
		}
		
		return end();
	}
	
	@RequestMapping("/edit/{id}")
	public String edit(@PathVariable("id")Integer id,Model model) {
		${className?cap_first} db${className?cap_first} = ${className}Service.query${className?cap_first}ById(id);
		model.addAttribute("${className}", db${className?cap_first});
		return "${className}/edit";
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
			Page<${className?cap_first}> page = ${className}Service.query${className?cap_first}Page(paramMap);
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
	public Object update(${className?cap_first} ${className}) {
		
		start();
		try{
			int count = ${className}Service.update${className?cap_first}(${className});
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
			int count = ${className}Service.delete${className?cap_first}ById(id);
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
			int count = ${className}Service.deleteBat${className?cap_first}s(ds);
			success(count == ds.getIds().size());
		} catch(Exception e) {
			e.printStackTrace();
			success(false);
		}
		return end();
	}
}
