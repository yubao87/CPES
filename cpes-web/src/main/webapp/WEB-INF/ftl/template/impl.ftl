package ${packageName}.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ${packageName}.bean.Datas;
import ${packageName}.bean.Page;
import ${packageName}.bean.${className?cap_first};
import ${packageName}.dao.${className?cap_first}Dao;
import ${packageName}.service.${className?cap_first}Service;

@Service
public class ${className?cap_first}ServiceImpl implements ${className?cap_first}Service {
	@Autowired
	private ${className?cap_first}Dao ${className}Dao;

	@Override
	public Page<${className?cap_first}> query${className?cap_first}Page(Map<String, Object> paramMap) {
		Page<${className?cap_first}> page = new Page<${className?cap_first}>();
		//查询数据
		List<${className?cap_first}> ${className}s = ${className}Dao.query${className?cap_first}List(paramMap);
		
		//查询数量
		int count = ${className}Dao.query${className?cap_first}Count(paramMap);
		page.setDatas(${className}s);
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
	public ${className?cap_first} query${className?cap_first}ById(Integer id) {
		return ${className}Dao.query${className?cap_first}ById(id);
	}

	@Override
	public void insert${className?cap_first}(${className?cap_first} ${className}) {
		${className}Dao.insert${className?cap_first}(${className});
	}

	@Override
	public int update${className?cap_first}(${className?cap_first} ${className}) {
		return ${className}Dao.update${className?cap_first}(${className});
	}

	@Override
	public int delete${className?cap_first}ById(Integer id) {
		return ${className}Dao.delete${className?cap_first}ById(id);
	}

	@Override
	public int deleteBat${className?cap_first}s(Datas ds) {
		return ${className}Dao.deleteBat${className?cap_first}s(ds);
	}

	@Override
	public List<${className?cap_first}> queryAll() {
		return ${className}Dao.queryAll();
	}


}
