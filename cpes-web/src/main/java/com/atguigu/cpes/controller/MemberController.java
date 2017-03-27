package com.atguigu.cpes.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.atguigu.cpes.bean.Cert;
import com.atguigu.cpes.bean.Member;
import com.atguigu.cpes.bean.Ticket;
import com.atguigu.cpes.common.BaseController;
import com.atguigu.cpes.service.CertService;
import com.atguigu.cpes.service.MemberService;

/** 
  ^_^ 2017年3月19日 ^_^ 上午11:29:48 ^_^ 
 */
@Controller
@RequestMapping("/member")
public class MemberController extends BaseController {
	@Autowired
	private MemberService memberService;
	@Autowired
	private CertService certService;
	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private TaskService taskService;
	
	@RequestMapping("/cert")
	public String cert( HttpSession session, Model model ) {
		Member sessionMember = (Member)session.getAttribute("loginMember");
		// 获取审批流程单
		Ticket t = memberService.queryTicketByMemberId(sessionMember.getId());
		if ( t == null ) {
			// 如果获取不到，应该新增流程单，并且从第一步开始执行
			t = new Ticket();
			t.setMemberid(sessionMember.getId());
			t.setStatus("0");
			memberService.inserTicket(t);
			return "member/cert";
		} else {
			
//			switch (t.getStatus()) {
//				case "0":
//					
//					break;
//	
//				default:
//					break;
//			}
			
			if ( "0".equals(t.getStatus()) ) {
				return "member/cert";
			} else if ( "1".equals(t.getStatus()) ) {
				return "member/apply-1-userinfo";
			} else if ( "2".equals(t.getStatus()) ) {
				List<Cert> certs = memberService.queryCertByAcctype(sessionMember.getAcctype());
				model.addAttribute("certs", certs);
				return "member/apply-2-certfile";
			} else if ("3".equals(t.getStatus())) {
				return "member/apply-3-email";
			} else if ("4".equals(t.getStatus())) {
				return "member/apply-4-checkcode";
			} else {
				return "member";
			}
		}
	}
	
	// 1.1  更改会员账户类型
	@RequestMapping("/apply-1-userinfo")
	public String apply1userinfo(String acctype, HttpSession session) {
		
		Member sessionMember = (Member) session.getAttribute("loginMember");
		sessionMember.setAcctype(acctype);
		memberService.updateMemberAcctype(sessionMember);
		// 更改流程审批单中的状态  方便用户下次申请时不用再走这一步了（选择账户类型）
		Ticket t = memberService.queryTicketByMemberId(sessionMember.getId());
		t.setStatus("1");
		memberService.updateTicketStatus(t);
		return "member/apply-1-userinfo";
	}
	
	//1.2  更改用户信息
	@ResponseBody
	@RequestMapping("/updateInfo")
	public Object updateInfo(Member member, HttpSession session) {
		start();
		try {
			Member sessionMember = (Member) session.getAttribute("loginMember");
			sessionMember.setRealname(member.getRealname());
			sessionMember.setCardnum(member.getCardnum());
			sessionMember.setTel(member.getTel());
			int i = memberService.updateMemberInfo(sessionMember);
			// 更改用户状态为第二步 用户信息已保存 下次不用输入用户信息了 直接验证邮箱即可
			Ticket t = memberService.queryTicketByMemberId(sessionMember.getId());
			t.setStatus("2");
			memberService.updateTicketStatus(t);
			success(true);
			success(i == 1);
		} catch (Exception e) {
			success(false);
		}
		return end();
	}
	
	// 2.1进入第二部  提示用户需要上传资质图片
	@RequestMapping("/apply-2-certfile")
	public String apply2certfile(Model model,HttpSession session){
		// 根据类型 0123选择需要上传什么文件
		//select name from t_cert where id in (SELECT certid FROM t_acc_cert  WHERE acctype = '0')
		Member sessionMember = (Member) session.getAttribute("loginMember");
		List<Cert> certs = certService.queryCertNameByAcctype(sessionMember.getAcctype());
		// 在页面中循环遍历
		model.addAttribute("certs", certs);
		return "member/apply-2-certfile";
	}
	//2.2  保存用户上传的图片 uploadFile
	@ResponseBody
	@RequestMapping("/uploadFile")
	public Object uploadFile(HttpServletRequest req, HttpSession session ) {
		start();
		try {
			Member sessionMember = (Member)session.getAttribute("loginMember");
			MultipartHttpServletRequest request = (MultipartHttpServletRequest)req;
			//request.getFiles("certFile");
			//File.getName(); 获取所有的文件名
			Iterator<String> iters = request.getFileNames();
			// 开发环境是eclipse的工作空间
			// 获取文件的真实保存路径 这是tomcat的运行环境 
			String picsPath = session.getServletContext().getRealPath("pics");
//			picsPath = "d:\\";
			while ( iters.hasNext() ) {
				String filename = iters.next();
				String[] ss = filename.split("-");
				// 获取资质的id 
				Integer certid = Integer.parseInt(ss[1]);
				
				MultipartFile file = request.getFile(filename);
				InputStream in = file.getInputStream();
				// a.a.a.a.jpg
				String suffix = 
					file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
				
				// c:/test/xxx-sss-xxx.jpg
				String iconpath = UUID.randomUUID().toString().substring(0, 5) + suffix;
				String filepath = picsPath + File.separator + iconpath;
//				FileOutputStream out = new FileOutputStream(new File(filepath));
//				// 文件保存
//				int i = -1;
//				while ( (i = in.read()) != -1 ) {
//					out.write(i);
//				}
//				out.close();
				// 可以使用框架自带的 把上传文件流保存到本地文件中
				file.transferTo(new File(filepath));
				
				// 保存用户的资质数据
				Map<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put("memberid", sessionMember.getId());
				paramMap.put("certid", certid);
				paramMap.put("iconpath", iconpath);
				
				memberService.insertMemberCert(paramMap);
			}
			// 更新状态 
			Ticket t = memberService.queryTicketByMemberId(sessionMember.getId());
			t.setStatus("3");
			memberService.updateTicketStatus(t);
			
			success(true);
		} catch ( Exception e ) {
			e.printStackTrace();
			success(false);
		}
		return end();
	}
	
	//3.1  认证邮箱 并启动流程
	@ResponseBody
	@RequestMapping("/startProcess")
	public Object startProcess(String email, HttpSession session) {
		start();
		try {
			// 查询流程定义
			ProcessDefinitionQuery query =
				repositoryService.createProcessDefinitionQuery();
			ProcessDefinition pd =
				query.processDefinitionKey("cpes").latestVersion().singleResult();
			
			Member sessionMember = (Member)session.getAttribute("loginMember");
			sessionMember.setEmail(email);
			memberService.updateMemberEmail(sessionMember);
			// 启动流程实例
			Map<String, Object> varMap = new HashMap<String, Object>();
			// 流程设计中 委托人是一个字符型的临时变量  在此处需要转换一下
			varMap.put("toUser", "" + sessionMember.getId());
			varMap.put("toUserEmail", email);
			
			String s = "sfsfshdgwrwlkjr32lkjwflkjs5643lkjrslkjfdslkjflkj";
			
			// 随机认证码
			StringBuilder builder = new StringBuilder();
			for ( int i = 0; i < 4; i++ ) {
				Random r = new Random();
				int index = r.nextInt(s.length());
				
				char c = s.charAt(index);
				builder.append(c);
			}
			
			//Math.random();
			// 保存邮箱验证码 并启动流程
			varMap.put("authcode", builder.toString());
			ProcessInstance pi =
				runtimeService.startProcessInstanceById(pd.getId(), varMap);
			// 启动流程之后 将会员的信息与流程 及验证码关联在一起 流程自动发送验证码
			Ticket t = memberService.queryTicketByMemberId(sessionMember.getId());
			t.setPiid(pi.getId());
			t.setStatus("4");
			t.setAuthcode(builder.toString());
			memberService.updateTicketPIID(t);
			
			success(true);
		} catch ( Exception e ) {
			e.printStackTrace();
			success(false);
		}
		
		return end();
	}
	// 3.2 进入第三部 认证邮箱 跳转页面
	@RequestMapping("/apply-3-email")
	public String apply3email(){
		return "member/apply-3-email";
	}
	
	// 4.1 用户输入邮箱验证码后 开始校验是否正确  校验成功后返回会员首页
	@ResponseBody
	@RequestMapping("/checkAuthcode")
	public Object checkAuthcode(String authcode, HttpSession session) {
		start();
	    
		try {
			Member sessionMember = (Member)session.getAttribute("loginMember");
			// 获取审批流程单
			Ticket t = memberService.queryTicketByMemberId(sessionMember.getId());
			// 验证验证码
			if ( t.getAuthcode().equals(authcode) ) {
				// 让流程继续执行
				// 查询任务
				TaskQuery query = taskService.createTaskQuery();
				Task task = query
				    .taskAssignee("" + sessionMember.getId())
				    .processDefinitionKey("cpes")
				    .singleResult();
				
				// 完成任务
				taskService.complete(task.getId());
				
				// 更新流程审批单状态
				t.setStatus("5");
				memberService.updateTicketStatus(t);
				success(true);
			} else {
				error("认证码验证不正确");
				success(false);
			}
		} catch ( Exception e ) {
			e.printStackTrace();
			success(false);
		}
		return end();
	}
	
	// 4.2 进入邮箱验证页面
	@RequestMapping("/apply-4-checkcode")
	public String apply4success(){
		return "member/apply-4-checkcode";
	}	
/*	
	@ResponseBody
	@RequestMapping("/authemail")
	public Object authemail(String email,HttpSession session) {
		start();
		try {
			Member sessionMember = (Member) session.getAttribute("loginMember");
			sessionMember.setEmail(email);
			int i = memberService.updateMemberEmail(sessionMember);
			success(i == 1);
		} catch (Exception e) {
			success(false);
		}
		return end();
	}
*/

	
	// 进入第五部 调转页面 输入验证码
	@RequestMapping("/apply-5-code")
	public String apply5code(){
		return "member/apply-5-code";
	}
	
}
