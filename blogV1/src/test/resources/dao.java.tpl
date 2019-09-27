package ${PACKAGE_PREFIX}${DAO_LAYER_NAME};

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import ${PACKAGE_PREFIX}${MODEL_LAYER_NAME}.${TABLE_NAME?cap_first};
import ${PACKAGE_PREFIX}${BEANS_LAYER_NAME}.PageBean;


@Repository
public class ${TABLE_NAME?cap_first}Dao {
	@Autowired
	JdbcTemplate jdbcTemplate;
	@Autowired
	NamedParameterJdbcTemplate namedJdbcTemplate;

	public ${TABLE_NAME?cap_first} findById(${DGFIELDS[0].ColumnType} ${DGFIELDS[0].ColumnName}) {
		return jdbcTemplate.queryForObject("select * from ${TABLE_NAME} where ${DGFIELDS[0].ColumnName}=?", new BeanPropertyRowMapper<${TABLE_NAME?cap_first}>(${TABLE_NAME?cap_first}.class),${DGFIELDS[0].ColumnName});
	}

	//全部记录
	public List<${TABLE_NAME?cap_first}> listAll(){
		String sql="SELECT * FROM ${TABLE_NAME} ";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<${TABLE_NAME?cap_first}>(${TABLE_NAME?cap_first}.class));
	}

	// 分页查询
	public PageBean list(Integer pgNo,Integer pgSize, Map<String,Object> params){
		PageBean page = new PageBean(pgNo,pgSize);
		
		String sql="SELECT * FROM ${TABLE_NAME} ";
		
		//-----查询记录条数
		String sql_count="select count(*) as num from ( "+sql +" ) as ds";
		
		int total=(Integer) jdbcTemplate.queryForObject(sql_count, Integer.class);
		page.setTotal(total);
		
		sql+=" limit "+page.getStartIndex()+" ,"+ page.getPageSize();
		
		page.setRows(jdbcTemplate.query(sql, new BeanPropertyRowMapper<${TABLE_NAME?cap_first}>(${TABLE_NAME?cap_first}.class)));
		return page;
	}
		
	// 添加
	public boolean add(${TABLE_NAME?cap_first} ${TABLE_NAME}) {
		<#assign n = DGFIELDS?size />
		<#assign insert_fields= ""/>
		<#assign wenhao_fields= ""/>
		<#assign value_fields= ""/>
		
		<#list 1..(n-1) as i>
			<#if i lt n-1 >
				<#assign insert_fields= insert_fields+ DGFIELDS[i].ColumnName+ ", "/>
				<#assign wenhao_fields= wenhao_fields+ "?,"/>
				<#assign value_fields= value_fields + TABLE_NAME+".get"+DGFIELDS[i].ColumnName?cap_first+ "(), "/>
			<#else>
				<#assign insert_fields= insert_fields+ DGFIELDS[i].ColumnName+ " "/>
				<#assign wenhao_fields= wenhao_fields+ "?"/>
				<#assign value_fields= value_fields + TABLE_NAME+".get"+DGFIELDS[i].ColumnName?cap_first+ "()"/>
			</#if>
		</#list>
		
		return jdbcTemplate.update("insert into ${TABLE_NAME}(${insert_fields}) values(${wenhao_fields})",${value_fields})>0;
	}
	
	// 更新
	public boolean update(${TABLE_NAME?cap_first} ${TABLE_NAME}) {
		<#assign n = DGFIELDS?size />
		<#assign update_fields= ""/>
		<#assign value_fields= ""/>
		
		<#list 1..(n-1) as i>
			<#assign value_fields= value_fields + TABLE_NAME+".get"+DGFIELDS[i].ColumnName?cap_first+ "(), "/>
			<#if i lt n-1 >
				<#assign update_fields= update_fields+ DGFIELDS[i].ColumnName+ "=?, "/>
			<#else>
				<#assign update_fields= update_fields+ DGFIELDS[i].ColumnName+ "=? "/>
			</#if>
		</#list>
		<#assign value_fields= value_fields + TABLE_NAME+".get"+DGFIELDS[0].ColumnName?cap_first+ "()"/>
		return jdbcTemplate.update("update ${TABLE_NAME} SET ${update_fields}  where ${DGFIELDS[0].ColumnName} =? ",${value_fields})>0;
	}
	
	// 删除
	public boolean delete(${DGFIELDS[0].ColumnType} ${DGFIELDS[0].ColumnName}) {
		return jdbcTemplate.update("delete from ${TABLE_NAME} where ${DGFIELDS[0].ColumnName}=?",${DGFIELDS[0].ColumnName}) >0;
	}

}
