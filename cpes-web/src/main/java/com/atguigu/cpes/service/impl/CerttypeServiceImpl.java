package com.atguigu.cpes.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.cpes.bean.Datas;
import com.atguigu.cpes.bean.Page;
import com.atguigu.cpes.bean.Certtype;
import com.atguigu.cpes.dao.CerttypeDao;
import com.atguigu.cpes.service.CerttypeService;

@Service
public class CerttypeServiceImpl implements CerttypeService {
	@Autowired
	private CerttypeDao certtypeDao;

	@Override
	public Page<Certtype> queryCerttypePage(Map<String, Object> paramMap) {
		Page<Certtype> page = new Page<Certtype>();
		//查询数据
		List<Certtype> certtypes = certtypeDao.queryCerttypeList(paramMap);
		
		//查询数量
		int count = certtypeDao.queryCerttypeCount(paramMap);
		page.setDatas(certtypes);
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
	public Certtype queryCerttypeById(Integer id) {
		return certtypeDao.queryCerttypeById(id);
	}

	@Override
	public void insertCerttype(Certtype certtype) {
		certtypeDao.insertCerttype(certtype);
	}

	@Override
	public int updateCerttype(Certtype certtype) {
		return certtypeDao.updateCerttype(certtype);
	}

	@Override
	public int deleteCerttypeById(Integer id) {
		return certtypeDao.deleteCerttypeById(id);
	}

	@Override
	public int deleteBatCerttypes(Datas ds) {
		return certtypeDao.deleteBatCerttypes(ds);
	}

	@Override
	public List<Certtype> queryAll() {
		return certtypeDao.queryAll();
	}


}
