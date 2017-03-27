package com.atguigu.cpes.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atguigu.cpes.bean.Member;
import com.atguigu.cpes.bean.Page;
import com.atguigu.cpes.bean.Ticket;
import com.atguigu.cpes.common.BaseController;
import com.atguigu.cpes.service.MemberService;

@Controller
@RequestMapping("/auth")
public class AuthController extends BaseController {

	@Autowired
	private TaskService taskService;
	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	private MemberService memberService;
	
	@RequestMapping("/index")
	public String index() {
		return "auth/index";
	}
	
	@RequestMapping("/show")
	public String show( Integer memberid, String taskid, Model model ) {
		
		// 查询会员提供的资质图片
		List<Map<String, Object>> memberCerts =
			memberService.queryCertByMemberId(memberid);
		model.addAttribute("memberCerts", memberCerts);
		model.addAttribute("taskid", taskid);
		model.addAttribute("memberid", memberid);
		return "auth/show";
	}
	
	@ResponseBody
	@RequestMapping("/ok")
	public Object ok( Integer memberid, String taskid ) {
		start();
		
		try {
			
			// 完成任务
			taskService.complete(taskid);
			
			// 更改会员的状态 为1  已实名认证
			memberService.updateMemberStatus(memberid);
			
			// 流程审批单完成
			Ticket t = memberService.queryTicketByMemberId(memberid);
			t.setStatus("6");
			memberService.updateTicketStatus(t);
			
			success(true);
		} catch ( Exception e ) {
			e.printStackTrace();
			success(false);
		}
		
		return end();
	}
	
    @ResponseBody
    @RequestMapping("/pageQuery")
    public Object pageQuery( Integer pageno, Integer pagesize ) {
        
        start();
        
        try {
            
        	TaskQuery query = taskService.createTaskQuery();
        	
        	List<Task> tasks = query.taskCandidateGroup("manager").listPage((pageno-1)*pagesize, pagesize);
        	int cnt = (int)query.taskCandidateGroup("manager").count();
            
            Page<Map<String, Object>> page = new Page<Map<String, Object>>();
            List<Map<String, Object>> taskMaps = new ArrayList<Map<String, Object>>();
            
            for ( Task t : tasks ) {
            	Map<String, Object> taskMap = new HashMap<String, Object>();
            	taskMap.put("id", t.getId());
            	taskMap.put("name", t.getName());
            	
            	String pdid = t.getProcessDefinitionId();
            	// 查询流程定义
            	ProcessDefinition pd =
            		repositoryService
            		    .createProcessDefinitionQuery()
            		    .processDefinitionId(pdid)
            		    .singleResult();
            	// 保存流程定义的名称
            	taskMap.put("pdname", pd.getName());
            	
            	// task ==> processinstance ==> ticket ==> user ==> name
            	// 通过任务得到流程实例  这个流程实例是某个申请人创建出来的
            	Member m = memberService.queryMemberByTask(t.getProcessInstanceId());
            	if (m != null) {
            		taskMap.put("username", m.getName());
            		taskMap.put("memberid", m.getId());
            		taskMaps.add(taskMap);
				}
            	
            }
            page.setDatas(taskMaps);
            page.setTotalsize(cnt);

            param("page", page);
            success(true);
        } catch ( Exception e ) {
            e.printStackTrace();
            success(false);
        }

        return end();
    }
}
