package com.atguigu.cpes.test;

import org.activiti.engine.ProcessEngine;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/** 
  ^_^ 2017年3月17日 ^_^ 下午12:08:46 ^_^ 
 */
public class FlowUtil {

	public static ProcessEngine getProcessEngine() {
		// 加载Spring环境 
		ApplicationContext ioc = new ClassPathXmlApplicationContext("classpath:spring/spring-*.xml");
		// 获取核心对象
		ProcessEngine pe =  (ProcessEngine) ioc.getBean("processEngine");
		return pe;
	}

}
