package com.atguigu.cpes.test;

import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;

/** 
  ^_^ 2017年3月17日 ^_^ 下午12:10:58 ^_^ 
 */
public class ActiveTestData {

	public static void main(String[] args) {
		// 将流程定义图形加载到流程框架中，将这个过程称之为部署流程
		ProcessEngine pe = FlowUtil.getProcessEngine();
		// 获取持久化服务对象
		RepositoryService repositoryService = pe.getRepositoryService();
		Deployment deployment = repositoryService.createDeployment()
				.addClasspathResource("MyProcess.bpmn").deploy();
		System.out.println("*****************"+deployment);
		ProcessDefinitionQuery query = repositoryService.createProcessDefinitionQuery();
		
		List<ProcessDefinition> list = query.list();
		for (ProcessDefinition pd : list) {
			System.err.println(pd.getKey());
			System.err.println(pd.getName());
		}

	}

}
