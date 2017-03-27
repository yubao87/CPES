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
public class Active10PG {

	// 网关：多个分支同时执行，每一个分支必须都得执行完毕，流程才会结束 ，称之为并行网关
	// 		如果有一个分支执行完毕，需要等待其他分支的执行
	public static void main(String[] args) {
		// 任务的分配和领取
		// 任务首先分配给组，组内再领取任务
		ProcessEngine pe = FlowUtil.getProcessEngine();
		RepositoryService repositoryService = pe.getRepositoryService();
		
		Deployment deployment = repositoryService.createDeployment()
				.addClasspathResource("MyProcess5PG.bpmn").deploy();
		RuntimeService runtimeService = pe.getRuntimeService();
		
		// 查询流程定义对象
		ProcessDefinition pd = repositoryService.createProcessDefinitionQuery()
				.processDefinitionKey("myProcess").latestVersion().singleResult();
		
//		Map<String,Object> varMap = new HashMap<String,Object>();
//		varMap.put("days", 5);
		ProcessInstance pi = runtimeService.startProcessInstanceById(pd.getId());
		
		List<Task> tasks = pe.getTaskService().createTaskQuery().taskAssignee("zhangsan").list();
		List<Task> tasks1 = pe.getTaskService().createTaskQuery().taskAssignee("lisi").list();
		System.out.println("张三的任务数量是：" + tasks.size());
		System.out.println("李四的任务数量是：" + tasks1.size());
		System.out.println("张三完成任务。。。。。");
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
		
		System.out.println("李四完成任务。。。。。。。");
		for (Task task : tasks1) {
			pe.getTaskService().complete(task.getId());
		}
		// 再一次判断流程是否结束
		historicProcessInstanceQuery = historyService.createHistoricProcessInstanceQuery();
		hpi = historicProcessInstanceQuery.processInstanceId(pi.getId()).finished().singleResult();
		System.out.println("流程是否结束= " + (hpi != null));
	}
}
