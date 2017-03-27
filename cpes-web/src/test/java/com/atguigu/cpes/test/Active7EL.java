package com.atguigu.cpes.test;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.poi.ss.formula.eval.PercentEval;

/** 
  ^_^ 2017年3月17日 ^_^ 下午8:06:56 ^_^ 
 */
public class Active7EL {
	public static void main(String[] args) {
		// 使用流程变量
		ProcessEngine processEngine = FlowUtil.getProcessEngine();
		RepositoryService repositoryService = processEngine.getRepositoryService();
		RuntimeService runtimeService = processEngine.getRuntimeService();
		// 查询流程定义对象
		ProcessDefinition pd = repositoryService.createProcessDefinitionQuery()
				.processDefinitionKey("myProcess").latestVersion().singleResult();
		Map<String, Object> varMap = new HashMap<>();
		varMap.put("TL", "dahuang");
		varMap.put("PM", "xiaoming");
		ProcessInstance pi = runtimeService.startProcessInstanceById(pd.getId(), varMap);
		// 获取任务
		TaskService taskService = processEngine.getTaskService();
		long cnt = taskService.createTaskQuery().taskAssignee("dahuang").count();
		System.out.println("大黄的任务数量是：" + cnt);
	}
}
