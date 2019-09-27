package ${PACKAGE_PREFIX}${CONTROLLER_LAYER_NAME};

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ${PACKAGE_PREFIX}${MODEL_LAYER_NAME}.${TABLE_NAME?cap_first};
import ${PACKAGE_PREFIX}${BEANS_LAYER_NAME}.PageBean;
import ${PACKAGE_PREFIX}${BEANS_LAYER_NAME}.StatusBean;
import ${PACKAGE_PREFIX}${SERVICE_LAYER_NAME}.${TABLE_NAME?cap_first}Service;


@Controller
@RequestMapping("${URL_PREFIX}/${TABLE_NAME}/")
@ResponseBody
public class ${TABLE_NAME?cap_first}Controller {
	@Autowired
	private ${TABLE_NAME?cap_first}Service service;
	
	/**
	 * 分页查询{TABLE_CNAME}
	 */
	//接收easyui datagrid的分页数据请求,page为第几页，rows为每页多少行
	@RequestMapping("list.do")
	public PageBean list(Integer page,Integer rows) {
		return  service.list(page,rows,null);
	}
	
	/**
	 * 删除${TABLE_CNAME}
	 */
	@RequestMapping("delete.do")
	public StatusBean delete(${DGFIELDS[0].ColumnType} ${DGFIELDS[0].ColumnName}){
		boolean success=service.delete(${DGFIELDS[0].ColumnName});
		return new StatusBean(success,"删除状态："+success);
	}

	/**
	 * 添加提交${TABLE_CNAME}
	 */
	@RequestMapping("add_submit.do")
	public StatusBean add_submit(${TABLE_NAME?cap_first} ${TABLE_NAME})  {
		boolean success=service.add(${TABLE_NAME});
		return new StatusBean(success,"添加状态："+success);
	}

	/**
	 * 修改${TABLE_CNAME}
	 */
	@RequestMapping("modify.do")
	public ${TABLE_NAME?cap_first} modify(${DGFIELDS[0].ColumnType} ${DGFIELDS[0].ColumnName})  {
		return service.findById(${DGFIELDS[0].ColumnName});
	}

	/**
	 * 修改提交${TABLE_CNAME}
	 */
	@RequestMapping("modify_submit.do")
	public StatusBean modify_submit(${TABLE_NAME?cap_first} ${TABLE_NAME}) {
		boolean success=service.update(${TABLE_NAME});
		return new StatusBean(success,"修改状态："+success);
	}
	
	
}


