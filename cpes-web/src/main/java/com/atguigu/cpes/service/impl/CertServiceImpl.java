package com.atguigu.cpes.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.cpes.bean.Cert;
import com.atguigu.cpes.bean.Datas;
import com.atguigu.cpes.bean.Page;
import com.atguigu.cpes.dao.CertDao;
import com.atguigu.cpes.service.CertService;

@Service
public class CertServiceImpl implements CertService {
	@Autowired
	private CertDao certDao;

	@Override
	public Page<Cert> queryCertPage(Map<String, Object> paramMap) {
		Page<Cert> page = new Page<Cert>();
		//查询数据
		List<Cert> certs = certDao.queryCertList(paramMap);
		
		//查询数量
		int count = certDao.queryCertCount(paramMap);
		page.setDatas(certs);
		page.setTotalsize(count);
		
		Integer pagesize = (Integer) paramMap.get("size");
		if(count % pagesize == 0) {
			page.setTotalno(count / pagesize);
		} else {
			page.setTotalno(count / pagesize + 1);
		}
		return page;
	}

	@Override
	public Cert queryCertById(Integer id) {
		return certDao.queryCertById(id);
	}

	@Override
	public void insertCert(Cert cert) {
		certDao.insertCert(cert);
	}

	@Override
	public int updateCert(Cert cert) {
		return certDao.updateCert(cert);
	}

	@Override
	public int deleteCertById(Integer id) {
		return certDao.deleteCertById(id);
	}

	@Override
	public int deleteBatCerts(Datas ds) {
		return certDao.deleteBatCerts(ds);
	}

	@Override
	public List<Cert> queryAll() {
		return certDao.queryAll();
	}

	@Override
	public void insertAccCert(Map<String, Object> paramMap) {
		certDao.insertAccCert(paramMap);		
	}

	@Override
	public void deleteAccCert(Map<String, Object> paramMap) {
		certDao.deleteAccCert(paramMap);
	}

	@Override
	public List<Cert> queryCertNameByAcctype(String acctype) {
		return certDao.queryCertNameByAcctype(acctype);
	}


}
