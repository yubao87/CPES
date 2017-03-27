package com.atguigu.cpes.dao;

import java.util.List;
import java.util.Map;

import com.atguigu.cpes.bean.Cert;
import com.atguigu.cpes.bean.Member;
import com.atguigu.cpes.bean.Ticket;

/** 
  ^_^ 2017年3月19日 ^_^ 上午10:39:24 ^_^ 
 */
public interface MemberDao {

	Member queryMember(Member member);

	void updateMemberAcctype(Member sessionMember);

	int updateMemberInfo(Member sessionMember);

	int updateMemberEmail(Member sessionMember);

	Ticket queryTicketByMemberId(Integer id);

	void updateTicketStatus(Ticket t);

	void insertMemberCert(Map<String, Object> paramMap);

	void updateTicketPIID(Ticket t);

	List<Cert> queryCertByAcctype(String acctype);

	void inserTicket(Ticket t);

	Member queryMemberByTask(String piid);

	List<Map<String, Object>> queryCertByMemberId(Integer memberid);

	void updateMemberStatus(Integer memberid);

}
