package com.blog.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.blog.entitys.Link;
import com.blog.beans.PageBean;


@Repository
public class LinkDao {
	@Autowired
	JdbcTemplate jdbcTemplate;
	@Autowired
	NamedParameterJdbcTemplate namedJdbcTemplate;

	public Link findById(Integer id) {
		return jdbcTemplate.queryForObject("select * from link where id=?", new BeanPropertyRowMapper<Link>(Link.class),id);
	}

	//全部记录
	public List<Link> listAll(){
		String sql="SELECT * FROM link order by  orderNum asc ";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<Link>(Link.class));
	}

	// 分页查询
	public PageBean list(Integer pgNo,Integer pgSize, Map<String,Object> params){
		PageBean page = new PageBean(pgNo,pgSize);
		
		String sql="SELECT * FROM link ";
		
		//-----查询记录条数
		String sql_count="select count(*) as num from ( "+sql +" ) as ds";
		
		int total=(Integer) jdbcTemplate.queryForObject(sql_count, Integer.class);
		page.setTotal(total);
		
		sql+=" limit "+page.getStartIndex()+" ,"+ page.getPageSize();
		
		page.setRows(jdbcTemplate.query(sql, new BeanPropertyRowMapper<Link>(Link.class)));
		return page;
	}
		
	// 添加
	public boolean add(Link link) {
		
		
		return jdbcTemplate.update("insert into link(linkname, linkurl, orderNum ) values(?,?,?)",link.getLinkname(), link.getLinkurl(), link.getOrderNum())>0;
	}
	
	// 更新
	public boolean update(Link link) {
		
		return jdbcTemplate.update("update link SET linkname=?, linkurl=?, orderNum=?   where id =? ",link.getLinkname(), link.getLinkurl(), link.getOrderNum(), link.getId())>0;
	}
	
	// 删除
	public boolean delete(Integer id) {
		return jdbcTemplate.update("delete from link where id=?",id) >0;
	}

}
