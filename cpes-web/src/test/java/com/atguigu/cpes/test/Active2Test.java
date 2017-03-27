package com.atguigu.cpes.test;

import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.apache.poi.ss.formula.eval.PercentEval;

/** 
  ^_^ 2017年3月17日 ^_^ 下午3:12:03 ^_^ 
 */
public class Active2Test {
	
	public static void main(String[] args) {
		
		// 让流程任务继续执行
		// 所有的任务完成后 结束节点是自动完成的 所以流程也就结束了 运行时表的数据全部删除
		ProcessEngine processEngine = FlowUtil.getProcessEngine();
		
		RepositoryService repositoryService = processEngine
				.getRepositoryService();
		
		Deployment deployment = repositoryService.createDeployment()
				.addClasspathResource("MyProcess1.bpmn").deploy();
		
		ProcessDefinition pd = repositoryService.createProcessDefinitionQuery()
				.processDefinitionKey("myProcess").latestVersion().singleResult();
		
		// 启动流程
		RuntimeService runtimeService = processEngine.getRuntimeService();
		runtimeService.startProcessInstanceById(pd.getId());
		
		// 查询任务
		TaskService taskService = processEngine.getTaskService();
		TaskQuery taskQuery = taskService.createTaskQuery();
		List<Task> taks = taskQuery.taskAssignee("zhangsan").list();
		System.out.println("张三的任务数量是: " + taks.size());
		for (Task task : taks) {
			System.out.println("张三完成的任务是：" + task.getName());
			// 完成任务
			taskService.complete(task.getId());
		}
		// 查询任务
		long cnt = taskQuery.taskAssignee("zhangsan").count();
		System.out.println("张三的任务数量是：" + cnt);
		
		
	}
}
