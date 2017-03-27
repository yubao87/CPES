package com.atguigu.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

public class FreeMaker {
	
	private static String path = "E:\\workspace\\cpes-web\\src\\main\\java\\com\\atguigu\\cpes\\";
	public static void main(String[] args) throws Exception {
		// 创建Freemarker对象的配置对象
		Configuration cfg = new Configuration();
		// 设定默认的位置（路径）
		cfg.setDirectoryForTemplateLoading(new File("E:\\workspace\\cpes-web\\src\\main\\webapp\\WEB-INF\\ftl\\template"));
		cfg.setDefaultEncoding("UTF-8");
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

		// 数据
		Map<String, Object> paramMap = new HashMap<String, Object>();

		paramMap.put("packageName", "com.atguigu.cpes");
		paramMap.put("className", "certtype");
		
		List<Attribute> attrs = new ArrayList<Attribute>();
		Attribute a1 = new Attribute("Integer","id");
		Attribute a2 = new Attribute("String", "acctype");
		Attribute a3 = new Attribute("Integer", "certid");
				
		attrs.add(a1);
		attrs.add(a2);
		attrs.add(a3);
		
		
		paramMap.put("attrs", attrs);
		
		// bean
		makeBeanFile(cfg, paramMap);

		// controller
		makeControllerFile(cfg, paramMap);

		// service
		makeServiceAndImplFile(cfg, paramMap);

		// dao
		makeDaoFile(cfg, paramMap);
	}

	private static void makeBeanFile(Configuration cfg, Map<String, Object> paramMap) throws Exception {
		String className = (String) paramMap.get("className");
		className = className.substring(0, 1).toUpperCase() + className.substring(1) + ".java";
		Template t = cfg.getTemplate("bean.ftl");
		
		outFile(t,paramMap, path + "bean\\" + className);
	}

	private static void makeControllerFile(Configuration cfg, Map<String, Object> paramMap) throws Exception {
		String className = (String) paramMap.get("className");
		className = className.substring(0, 1).toUpperCase() + className.substring(1) + "Controller.java";
		Template t = cfg.getTemplate("controller.ftl");
		outFile(t,paramMap, path + "controller\\" + className);
	}

	private static void makeServiceAndImplFile(Configuration cfg, Map<String, Object> paramMap)
			throws Exception {
		String className = (String) paramMap.get("className");
		className = className.substring(0, 1).toUpperCase() + className.substring(1);
		Template t = cfg.getTemplate("service.ftl");
		outFile(t,paramMap, path + "service\\" + className + "Service.java");
		
		t = cfg.getTemplate("impl.ftl");
		outFile(t,paramMap, path + "service\\impl\\" + className + "ServiceImpl.java");
	}

	private static void makeDaoFile(Configuration cfg, Map<String, Object> paramMap) throws Exception {
		String className = (String) paramMap.get("className");
		className = className.substring(0, 1).toUpperCase() + className.substring(1) + "Dao.java";
		Template t = cfg.getTemplate("dao.ftl");
		outFile(t,paramMap, path + "dao\\" + className);
	}

	private static void outFile(Template t, Map<String, Object> paramMap, String path) throws Exception {
		// 组合生成
		Writer out = new OutputStreamWriter(new FileOutputStream(new File(path)), "UTF-8");
		t.process(paramMap, out);
	}
}
