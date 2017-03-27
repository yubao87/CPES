package com.atguigu.cpes.test;

import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricProcessInstanceQuery;

/** 
  ^_^ 2017年3月17日 ^_^ 下午7:52:53 ^_^ 
 */
public class Active6History {

	public static void main(String[] args) {
		// 查询历史数据
		ProcessEngine pe = FlowUtil.getProcessEngine();
		HistoryService historyService = pe.getHistoryService();
		HistoricProcessInstanceQuery historicProcessInstanceQuery = 
				historyService.createHistoricProcessInstanceQuery();
		HistoricProcessInstance hpi = historicProcessInstanceQuery
				.processInstanceId("105").finished().singleResult();
		System.out.println(hpi);

	}

}
