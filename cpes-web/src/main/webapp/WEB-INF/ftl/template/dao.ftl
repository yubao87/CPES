package ${packageName}.dao;

import java.util.List;
import java.util.Map;

import ${packageName}.bean.Datas;
import ${packageName}.bean.${className?cap_first};

public interface ${className?cap_first}Dao {

	List<${className?cap_first}> query${className?cap_first}List(Map<String, Object> paramMap);

	int query${className?cap_first}Count(Map<String, Object> paramMap);

	${className?cap_first} query${className?cap_first}ById(Integer id);

	void insert${className?cap_first}(${className?cap_first} ${className});

	int update${className?cap_first}(${className?cap_first} ${className});

	int delete${className?cap_first}ById(Integer id);

	int deleteBat${className?cap_first}s(Datas ds);

	List<${className?cap_first}> queryAll();

	void deleteBy${className?cap_first}id(Integer ${className}id);

}
