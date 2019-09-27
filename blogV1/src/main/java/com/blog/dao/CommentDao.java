package com.blog.dao;

import java.util.List;
import java.util.Map;

import org.simpleflatmapper.jdbc.spring.JdbcTemplateMapperFactory;
import org.simpleflatmapper.jdbc.spring.RowMapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.blog.entitys.Blog;
import com.blog.entitys.Comment;
import com.blog.beans.PageBean;


@Repository
public class CommentDao {
	@Autowired
	JdbcTemplate jdbcTemplate;
	@Autowired
	NamedParameterJdbcTemplate namedJdbcTemplate;

	public Comment findById(Integer id) {
		return jdbcTemplate.queryForObject("select * from comment where id=?", new BeanPropertyRowMapper<Comment>(Comment.class),id);
	}

	//全部记录
	public List<Comment> listAll(){
		String sql="SELECT * FROM comment ";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<Comment>(Comment.class));
	}
	
	public List<Comment> getListByBlogId(Integer blogId) {
		String sql="SELECT * FROM comment where blog_id=? and state=1 ORDER BY commentDate asc";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<Comment>(Comment.class),blogId);
	}
	

	// 分页查询
	public PageBean list(Integer pgNo,Integer pgSize, Map<String,Object> params){
		PageBean page = new PageBean(pgNo,pgSize);
		
		String sql="select comment.*,blog.title as blog_title from blog right JOIN comment on comment.blog_id = blog.id where blog.user_id=? ";		
		Integer user_id=(Integer) params.get("user_id");
		
		//-----查询记录条数
		String sql_count="select count(*) as num from ( "+sql +" ) as ds";
		
		int total=(Integer) jdbcTemplate.queryForObject(sql_count, Integer.class,user_id);
		page.setTotal(total);
		
		sql+=" limit "+page.getStartIndex()+" ,"+ page.getPageSize();

 		RowMapperImpl<Comment> mapper = JdbcTemplateMapperFactory.newInstance().addKeys("id,blog_id").ignorePropertyNotFound().newRowMapper(Comment.class);
	
		page.setRows(jdbcTemplate.query(sql, mapper,user_id));
		return page;
	}
		
	// 添加
	public boolean add(Comment comment) {
		
		
		return jdbcTemplate.update("insert into comment(userIp, content, commentDate, state, blog_id ) values(?,?,?,?,?)",comment.getUserIp(), comment.getContent(), comment.getCommentDate(), comment.getState(), comment.getBlog_id())>0;
	}
	
	// 更新
	public boolean update(Comment comment) {
		
		return jdbcTemplate.update("update comment SET userIp=?, content=?, commentDate=?, state=?, blog_id=?   where id =? ",comment.getUserIp(), comment.getContent(), comment.getCommentDate(), comment.getState(), comment.getBlog_id(), comment.getId())>0;
	}
	
	// 删除
	public boolean delete(Integer id) {
		return jdbcTemplate.update("delete from comment where id=?",id) >0;
	}

	public boolean review(Integer id, Integer state) {
		return jdbcTemplate.update("update comment SET state=?   where id =? ",state,id)>0;
	}


}
