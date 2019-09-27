package com.blog.dao;

import java.util.List;
import java.util.Map;

import org.simpleflatmapper.jdbc.spring.JdbcTemplateMapperFactory;
import org.simpleflatmapper.jdbc.spring.RowMapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.blog.entitys.Blog;
import com.blog.beans.PageBean;


@Repository
public class BlogDao {
	@Autowired
	JdbcTemplate jdbcTemplate;
	@Autowired
	NamedParameterJdbcTemplate namedJdbcTemplate;

	public Blog findById(Integer id) {
		
		String sql="select blog.*,blogtype.id as blogtype_id,blogtype.typeName as blogtype_typeName, blogtype.orderNum as blogtype_orderNum from blog "
				+ " LEFT JOIN blogtype on blog.type_id=blogtype.id where blog.id=? ";
		
  		 RowMapperImpl<Blog> mapper = JdbcTemplateMapperFactory.newInstance().addKeys("id,blogtype_id").ignorePropertyNotFound().newRowMapper(Blog.class);
       	
         return jdbcTemplate.queryForObject(sql,mapper,id);
	}

	//全部记录
	public List<Blog> listAll(){
		String sql="select blog.*,blogtype.id as blogtype_id,blogtype.typeName as blogtype_typeName, blogtype.orderNum as blogtype_orderNum from blog "
				+ " LEFT JOIN blogtype on blog.type_id=blogtype.id order by id desc";
 		 RowMapperImpl<Blog> mapper = JdbcTemplateMapperFactory.newInstance().addKeys("id,blogtype_id").ignorePropertyNotFound().newRowMapper(Blog.class);
		return jdbcTemplate.query(sql, mapper);
	}

	// 分页查询
	public PageBean list(Integer pgNo,Integer pgSize, Map<String,Object> params){
		PageBean page = new PageBean(pgNo,pgSize);
		
		String sql="select blog.*,blogtype.id as blogtype_id,blogtype.typeName as blogtype_typeName, blogtype.orderNum as blogtype_orderNum from blog "
				+ " LEFT JOIN blogtype on blog.type_id=blogtype.id where blog.user_id=:user_id ";
		
		Integer user_id=(Integer) params.get("user_id");
		if(params.get("typeId")!=null) sql+=" and blog.type_id=:typeId ";
		if(params.get("releaseDateStr")!=null) sql+=" and DATE_FORMAT(releaseDate,'%Y-%m')=:releaseDateStr ";
		
		sql+=" order by id desc ";
		
		//-----查询记录条数
		String sql_count="select count(*) as num from ( "+sql +" ) as ds";
		int total=(Integer) namedJdbcTemplate.queryForObject(sql_count,params,Integer.class);
		page.setTotal(total);
		
		sql+=" limit "+page.getStartIndex()+" ,"+ page.getPageSize();
		
		 RowMapperImpl<Blog> mapper = JdbcTemplateMapperFactory.newInstance().addKeys("id,blogtype_id").ignorePropertyNotFound().newRowMapper(Blog.class);
		 
		 
		page.setRows(namedJdbcTemplate.query(sql,params, mapper));
		
//		
//		Integer user_id=(Integer) params.get("user_id");
//		//-----查询记录条数
//		String sql_count="select count(*) as num from ( "+sql +" ) as ds";
//		
//		int total=(Integer) jdbcTemplate.queryForObject(sql_count, Integer.class,user_id);
//		page.setTotal(total);
//		
//		sql+=" limit "+page.getStartIndex()+" ,"+ page.getPageSize();
//		
//		 RowMapperImpl<Blog> mapper = JdbcTemplateMapperFactory.newInstance().addKeys("id,blogtype_id").ignorePropertyNotFound().newRowMapper(Blog.class);
//		 
//		 
//		page.setRows(jdbcTemplate.query(sql, mapper,user_id));
		
		
		return page;
	}
		
	// 添加
	public boolean add(Blog blog) {
		return jdbcTemplate.update("insert into blog(title, summary, releaseDate, clickHit, replyHit, content, keyWord, type_id,user_id ) values(?,?,?,?,?,?,?,?,?)",blog.getTitle(), blog.getSummary(), blog.getReleaseDate(), blog.getClickHit(), blog.getReplyHit(), blog.getContent(), blog.getKeyWord(), blog.getBlogtype().getId(),blog.getUser_id())>0;
	}
	
	// 更新
	public boolean update(Blog blog) {
		
		return jdbcTemplate.update("update blog SET title=?, summary=?, releaseDate=?, clickHit=?, replyHit=?, content=?, keyWord=?, type_id=?   where id =? ",blog.getTitle(), blog.getSummary(), blog.getReleaseDate(), blog.getClickHit(), blog.getReplyHit(), blog.getContent(), blog.getKeyWord(), blog.getBlogtype().getId(), blog.getId())>0;
	}
	
	//部分更新
	public boolean update2(Blog blog) {
		
		return jdbcTemplate.update("update blog SET title=?, summary=?, releaseDate=?, content=?, keyWord=?, type_id=?   where id =? ",blog.getTitle(), blog.getSummary(), blog.getReleaseDate(), blog.getContent(), blog.getKeyWord(), blog.getBlogtype().getId(), blog.getId())>0;
	}

	
	// 删除
	public boolean delete(Integer id) {
		return jdbcTemplate.update("delete from blog where id=?",id) >0;
	}

	public boolean updateReplyHit(Integer id) {
		
		return jdbcTemplate.update("update blog set replyHit=replyHit+1 where id=?",id) >0;
		
	}

	public Blog getPrevBlog(Integer id) {
		try {
			String sql="select * from blog where id<? and user_id=(select user_id from blog where id=?) order by id DESC limit 1";
			return jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<Blog>(Blog.class),id,id);
		} catch (DataAccessException e) {
			return null;
		}
	}

	public Blog getNextBlog(Integer id) {
		try {
			String sql="select * from blog where id>? and user_id=(select user_id from blog where id=?) order by id ASC limit 1"; 
			return jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<Blog>(Blog.class),id,id);
		} catch (DataAccessException e) {
			return null;
		}
		
	}

	// 获取博客信息，按照时间分类的
	public List getBlogTimeList(Integer user_id) {
		String sql="select DATE_FORMAT(releaseDate,'%Y-%m') as releaseDateStr,count(blog.id) as blogCount from blog where user_id= ? GROUP BY releaseDateStr ORDER BY releaseDateStr desc"; 
		return jdbcTemplate.queryForList(sql,user_id);
	}

}
