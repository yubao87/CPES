package com.atguigu.cpes.test;

import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

/** 
  ^_^ 2017年3月17日 ^_^ 下午8:40:45 ^_^ 
 */
public class Active8Assign {

	public static void main(String[] args) {
		// 任务的分配和领取
		// 任务首先分配给组，组内再领取任务
		ProcessEngine pe = FlowUtil.getProcessEngine();
		RepositoryService repositoryService = pe.getRepositoryService();
		// 部署流程
		Deployment deployment = repositoryService.createDeployment()
				.addClasspathResource("MyProcess3EL.bpmn").deploy();
		RuntimeService runtimeService = pe.getRuntimeService();
		// 查询流程定义对象
		ProcessDefinition pd = repositoryService.createProcessDefinitionQuery()
				.processDefinitionKey("myProcess").latestVersion().singleResult();
		ProcessInstance pi = runtimeService.startProcessInstanceById(pd.getId());
		TaskService taskService = pe.getTaskService();
		// 分配任务之前 查看小明的任务数量
		long count = taskService.createTaskQuery().taskAssignee("xiaoming").count();
		System.out.println("xiaoming的任务数量是：" + count);
		// cpes小组 领取任务
		List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup("cpes").list();
		// cpes 组内循环遍历 分配任务
		for (Task task : tasks) {
			taskService.claim(task.getId(), "xiaoming");
			System.out.println("分配给小明任务。。。。。");
		}
		// 任务分配完毕 查看小明的任务数量
		count = taskService.createTaskQuery().taskAssignee("xiaoming").count();
		System.out.println("xiaoming的任务数量是：" + count);
	}

}
