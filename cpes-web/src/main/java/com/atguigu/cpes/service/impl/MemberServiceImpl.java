package com.atguigu.cpes.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.cpes.bean.Cert;
import com.atguigu.cpes.bean.Member;
import com.atguigu.cpes.bean.Ticket;
import com.atguigu.cpes.dao.MemberDao;
import com.atguigu.cpes.service.MemberService;

/** 
  ^_^ 2017年3月19日 ^_^ 上午10:40:11 ^_^ 
 */
@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	private MemberDao memberDao;
	@Override
	public Member queryMember(Member member) {
		return memberDao.queryMember(member);
	}
	@Override
	public void updateMemberAcctype(Member sessionMember) {
		memberDao.updateMemberAcctype(sessionMember);
	}
	@Override
	public int updateMemberInfo(Member sessionMember) {
		return memberDao.updateMemberInfo(sessionMember);
	}
	@Override
	public int updateMemberEmail(Member sessionMember) {
		// TODO Auto-generated method stub
		return memberDao.updateMemberEmail(sessionMember);
	}
	@Override
	public Ticket queryTicketByMemberId(Integer id) {
		return memberDao.queryTicketByMemberId(id);
	}
	@Override
	public void updateTicketStatus(Ticket t) {
		memberDao.updateTicketStatus(t);
	}
	@Override
	public void insertMemberCert(Map<String, Object> paramMap) {
		memberDao.insertMemberCert(paramMap);
	}
	@Override
	public void updateTicketPIID(Ticket t) {
		memberDao.updateTicketPIID(t);
	}
	@Override
	public List<Cert> queryCertByAcctype(String acctype) {
		return memberDao.queryCertByAcctype(acctype);
	}
	@Override
	public void inserTicket(Ticket t) {
		memberDao.inserTicket(t);
		
	}
	public Member queryMemberByTask(String piid) {
		return memberDao.queryMemberByTask(piid);
	}

	public List<Map<String, Object>> queryCertByMemberId(Integer memberid) {
		return memberDao.queryCertByMemberId(memberid);
	}

	public void updateMemberStatus(Integer memberid) {
		memberDao.updateMemberStatus(memberid);
	}

}
