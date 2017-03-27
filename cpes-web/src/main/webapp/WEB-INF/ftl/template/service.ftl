package ${packageName}.service;

import java.util.List;
import java.util.Map;

import com.atguigu.cpes.bean.Datas;
import com.atguigu.cpes.bean.Page;
import com.atguigu.cpes.bean.${className?cap_first};

public interface ${className?cap_first}Service {

	Page<${className?cap_first}> query${className?cap_first}Page(Map<String, Object> paramMap);

	${className?cap_first} query${className?cap_first}ById(Integer id);

	void insert${className?cap_first}(${className?cap_first} ${className});

	int update${className?cap_first}(${className?cap_first} ${className});

	int delete${className?cap_first}ById(Integer id);

	int deleteBat${className?cap_first}s(Datas ds);

	List<${className?cap_first}> queryAll();

}
