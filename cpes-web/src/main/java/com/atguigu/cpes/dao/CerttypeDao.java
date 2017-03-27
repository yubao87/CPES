package com.atguigu.cpes.dao;

import java.util.List;
import java.util.Map;

import com.atguigu.cpes.bean.Datas;
import com.atguigu.cpes.bean.Certtype;

public interface CerttypeDao {

	List<Certtype> queryCerttypeList(Map<String, Object> paramMap);

	int queryCerttypeCount(Map<String, Object> paramMap);

	Certtype queryCerttypeById(Integer id);

	void insertCerttype(Certtype certtype);

	int updateCerttype(Certtype certtype);

	int deleteCerttypeById(Integer id);

	int deleteBatCerttypes(Datas ds);

	List<Certtype> queryAll();

	void deleteByCerttypeid(Integer certtypeid);

}
