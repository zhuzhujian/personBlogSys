package com.blog.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.blog.entitys.Blogtype;
import com.blog.beans.PageBean;


@Repository
public class BlogtypeDao {
	@Autowired
	JdbcTemplate jdbcTemplate;
	@Autowired
	NamedParameterJdbcTemplate namedJdbcTemplate;

	public Blogtype findById(Integer id) {
		return jdbcTemplate.queryForObject("select * from blogtype where id=?", new BeanPropertyRowMapper<Blogtype>(Blogtype.class),id);
	}

	//全部记录
	public List<Blogtype> listAll(Integer user_id){
		String sql="SELECT * FROM blogtype where user_id=? ";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<Blogtype>(Blogtype.class),user_id);
	}

	// 分页查询
	public PageBean list(Integer pgNo,Integer pgSize, Map<String,Object> params){
		PageBean page = new PageBean(pgNo,pgSize);
		Integer user_id=(Integer) params.get("user_id");
		String sql="SELECT * FROM blogtype where user_id=? ";
		
		//-----查询记录条数
		String sql_count="select count(*) as num from ( "+sql +" ) as ds";
		
		int total=(Integer) jdbcTemplate.queryForObject(sql_count, Integer.class,user_id);
		page.setTotal(total);
		
		sql+=" limit "+page.getStartIndex()+" ,"+ page.getPageSize();
		
		page.setRows(jdbcTemplate.query(sql, new BeanPropertyRowMapper<Blogtype>(Blogtype.class),user_id));
		return page;
	}
		
	// 添加
	public boolean add(Blogtype blogtype) {
		return jdbcTemplate.update("insert into blogtype(typeName, orderNum,user_id ) values(?,?,?)",blogtype.getTypeName(), blogtype.getOrderNum(),blogtype.getUser_id())>0;
	}
	
	// 更新
	public boolean update(Blogtype blogtype) {
		
		return jdbcTemplate.update("update blogtype SET typeName=?, orderNum=?   where id =? ",blogtype.getTypeName(), blogtype.getOrderNum(), blogtype.getId())>0;
	}
	
	// 删除
	public boolean delete(Integer id) {
		return jdbcTemplate.update("delete from blogtype where id=?",id) >0;
	}

	public List getBlogtypeList(Integer user_id) {
		String sql="SELECT blogtype.*,count(blog.id) as blogCount FROM blog right join blogtype on blogtype.id=blog.type_id where blog.user_id=? group by blog.type_id ";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<Blogtype>(Blogtype.class),user_id);
	}

}
