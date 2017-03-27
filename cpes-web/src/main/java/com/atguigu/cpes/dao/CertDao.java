package com.atguigu.cpes.dao;

import java.util.List;
import java.util.Map;

import com.atguigu.cpes.bean.Datas;
import com.atguigu.cpes.bean.Cert;

public interface CertDao {

	List<Cert> queryCertList(Map<String, Object> paramMap);

	int queryCertCount(Map<String, Object> paramMap);

	Cert queryCertById(Integer id);

	void insertCert(Cert cert);

	int updateCert(Cert cert);

	int deleteCertById(Integer id);

	int deleteBatCerts(Datas ds);

	List<Cert> queryAll();

	void deleteByCertid(Integer certid);

	void insertAccCert(Map<String, Object> paramMap);

	void deleteAccCert(Map<String, Object> paramMap);

	List<Cert> queryCertNameByAcctype(String acctype);

}
