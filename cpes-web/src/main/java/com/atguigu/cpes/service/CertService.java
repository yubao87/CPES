package com.atguigu.cpes.service;

import java.util.List;
import java.util.Map;

import com.atguigu.cpes.bean.Datas;
import com.atguigu.cpes.bean.Page;
import com.atguigu.cpes.bean.Cert;

public interface CertService {

	Page<Cert> queryCertPage(Map<String, Object> paramMap);

	Cert queryCertById(Integer id);

	void insertCert(Cert cert);

	int updateCert(Cert cert);

	int deleteCertById(Integer id);

	int deleteBatCerts(Datas ds);

	List<Cert> queryAll();

	void insertAccCert(Map<String, Object> paramMap);

	void deleteAccCert(Map<String, Object> paramMap);

	List<Cert> queryCertNameByAcctype(String acctype);

}
