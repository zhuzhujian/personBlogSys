package com;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.simpleflatmapper.jdbc.DynamicJdbcMapper;
import org.simpleflatmapper.jdbc.JdbcMapperFactory;
import org.simpleflatmapper.jdbc.spring.JdbcTemplateMapperFactory;
import org.simpleflatmapper.jdbc.spring.ResultSetExtractorImpl;
import org.simpleflatmapper.jdbc.spring.RowMapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.blog.dao.BlogDao;
import com.blog.entitys.Blog;
import com.blog.entitys.Comment;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml"})

//https://www.cnblogs.com/youzhibing/p/6127250.html 

public class JdbcTemplateTest {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	BlogDao dao;

	@Test
	public void rowmapper() {
		String sql="select blog.*,blogtype.id as blogtype_id,blogtype.typeName as blogtype_typeName, blogtype.orderNum as blogtype_orderNum from blog "
				+ " LEFT JOIN blogtype on blog.type_id=blogtype.id where blog.id=1";
		
//        RowMapper<Blog> mapper = new RowMapper<Blog>() {  
//        	public Blog mapRow(ResultSet rs, int rowNum) throws SQLException {  
//        		Blog blog = new Blog();  
//        		blog.setId(rs.getInt("id")); 
//        		blog.setClickHit(rs.getInt("clickHit"));
//        		return blog;  
//        		}  
//        	};  
   		 RowMapperImpl<Blog> mapper = JdbcTemplateMapperFactory.newInstance().addKeys("id,blogtype_id").ignorePropertyNotFound().newRowMapper(Blog.class);
      	
         Blog blog = jdbcTemplate.queryForObject(sql,mapper);
         System.out.println(blog.getTitle());
         System.out.println(blog.getBlogtype().getTypeName());
	}
	
	@Test
	public void rowmapper2() {
//		String sql="select comment.*,blog.title as blog_title,blog.id as blog_id2 from comment LEFT JOIN blog on comment.blog_id = blog.id  ";		
		String sql="select comment.*,blog.title as blog_title from comment LEFT JOIN blog on comment.blog_id = blog.id  ";		
 		RowMapperImpl<Comment> mapper = JdbcTemplateMapperFactory.newInstance().addKeys("id,blog_id").ignorePropertyNotFound().newRowMapper(Comment.class);
 		
 		List<Comment> list = jdbcTemplate.query(sql, mapper);
 		for(Comment comment:list) {
          System.out.println(comment.getId()+" "+comment.getContent()+ " "+comment.getBlog_id()+" "+comment.getBlog().getId()+" " +comment.getBlog().getTitle());
	
 		}
     	
	}
	
	
	@Test
	public void mapper1() {
	  Blog blog=dao.findById(1);
      System.out.println(blog.getTitle());
      System.out.println(blog.getBlogtype().getTypeName());
	}
	
	@Test
	public void mapper2() {
	  List<Blog> list = dao.listAll();
	  System.out.println("***********");
	  for(Blog blog:list) {
	      System.out.println(blog.getId());
	      System.out.println(blog.getTitle());
	      System.out.println(blog.getBlogtype().getTypeName());
	      System.out.println(blog.getBlogtype().getId());
	  }
	}

}
