package com.atguigu.cpes.service;

import java.util.List;
import java.util.Map;

import com.atguigu.cpes.bean.Datas;
import com.atguigu.cpes.bean.Page;
import com.atguigu.cpes.bean.Certtype;

public interface CerttypeService {

	Page<Certtype> queryCerttypePage(Map<String, Object> paramMap);

	Certtype queryCerttypeById(Integer id);

	void insertCerttype(Certtype certtype);

	int updateCerttype(Certtype certtype);

	int deleteCerttypeById(Integer id);

	int deleteBatCerttypes(Datas ds);

	List<Certtype> queryAll();

}
