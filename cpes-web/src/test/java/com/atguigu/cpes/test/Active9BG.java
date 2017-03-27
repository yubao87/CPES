package com.atguigu.cpes.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricActivityInstanceQuery;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

/** 
  ^_^ 2017年3月17日 ^_^ 下午9:42:05 ^_^ 
 */
public class Active9BG {

	public static void main(String[] args) {
		// 网关：多个分支同时只能执行一个分支，不能执行多条分支，称之为排他网关
		// 任务的分配和领取
		// 任务首先分配给组，组内再领取任务
		ProcessEngine pe = FlowUtil.getProcessEngine();
		RepositoryService repositoryService = pe.getRepositoryService();
		
		Deployment deployment = repositoryService.createDeployment()
				.addClasspathResource("MyProcess4BG.bpmn").deploy();
		RuntimeService runtimeService = pe.getRuntimeService();
		
		// 查询流程定义对象
		ProcessDefinition pd = repositoryService.createProcessDefinitionQuery()
				.processDefinitionKey("myProcess").latestVersion().singleResult();
		
		Map<String,Object> varMap = new HashMap<String,Object>();
		varMap.put("days", 5);
		ProcessInstance pi = runtimeService.startProcessInstanceById(pd.getId(),varMap);
		
		List<Task> tasks = pe.getTaskService().createTaskQuery().taskAssignee("zhangsan").list();
		
		for (Task task : tasks) {
			pe.getTaskService().complete(task.getId());
		}
		 
		// 查看历史流程
		HistoryService historyService = pe.getHistoryService();
		HistoricProcessInstanceQuery historicProcessInstanceQuery = 
				historyService.createHistoricProcessInstanceQuery();
		
		HistoricProcessInstance hpi = historicProcessInstanceQuery
				.processInstanceId(pi.getId()).finished().singleResult();
		System.out.println("流程是否结束= " + (hpi != null));
		
	}
}
