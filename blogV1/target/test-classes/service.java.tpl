package ${PACKAGE_PREFIX}${SERVICE_LAYER_NAME};

import java.util.Map;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ${PACKAGE_PREFIX}${DAO_LAYER_NAME}.${TABLE_NAME?cap_first}Dao;
import ${PACKAGE_PREFIX}${MODEL_LAYER_NAME}.${TABLE_NAME?cap_first};
import ${PACKAGE_PREFIX}${BEANS_LAYER_NAME}.PageBean;

@Service
@Transactional
public class ${TABLE_NAME?cap_first}Service {

	@Autowired
	${TABLE_NAME?cap_first}Dao dao;
	
	// 根据id查找${TABLE_CNAME}
	@Transactional(readOnly=true)
	public ${TABLE_NAME?cap_first} findById(${DGFIELDS[0].ColumnType} ${DGFIELDS[0].ColumnName}) {
		return dao.findById(${DGFIELDS[0].ColumnName});
	}

	//全部记录
	@Transactional(readOnly=true)
	public List<${TABLE_NAME?cap_first}> listAll(){
		return dao.listAll();
	}

	// 分页查询${TABLE_CNAME}
	@Transactional(readOnly=true)
	public PageBean list(Integer pgNo,Integer pgSize,Map<String,Object> params) {
		return dao.list(pgNo,pgSize,params);		
	}
	
	// 添加${TABLE_CNAME}
	public boolean add(${TABLE_NAME?cap_first} ${TABLE_NAME}) {
		return dao.add(${TABLE_NAME});
	}
	
	// 更新${TABLE_CNAME}
	public boolean update(${TABLE_NAME?cap_first} ${TABLE_NAME}) {
		return dao.update(${TABLE_NAME});
	}
	
	// 删除${TABLE_CNAME}
	public boolean delete(${DGFIELDS[0].ColumnType} ${DGFIELDS[0].ColumnName}) {
		return dao.delete(${DGFIELDS[0].ColumnName});
	}
}
