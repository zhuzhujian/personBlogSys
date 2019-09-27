package com.blog.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.blog.entitys.Blogger;
import com.blog.beans.PageBean;


@Repository
public class BloggerDao {
	@Autowired
	JdbcTemplate jdbcTemplate;
	@Autowired
	NamedParameterJdbcTemplate namedJdbcTemplate;

	public Blogger findById(Integer id) {
		return jdbcTemplate.queryForObject("select * from blogger where id=?", new BeanPropertyRowMapper<Blogger>(Blogger.class),id);
	}

	//全部记录
	public List<Blogger> listAll(){
		String sql="SELECT * FROM blogger ";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<Blogger>(Blogger.class));
	}

	// 分页查询
	public PageBean list(Integer pgNo,Integer pgSize, Map<String,Object> params){
		PageBean page = new PageBean(pgNo,pgSize);
		
		String sql="SELECT * FROM blogger ";
		
		//-----查询记录条数
		String sql_count="select count(*) as num from ( "+sql +" ) as ds";
		
		int total=(Integer) jdbcTemplate.queryForObject(sql_count, Integer.class);
		page.setTotal(total);
		
		sql+=" limit "+page.getStartIndex()+" ,"+ page.getPageSize();
		
		page.setRows(jdbcTemplate.query(sql, new BeanPropertyRowMapper<Blogger>(Blogger.class)));
		return page;
	}
		
	// 添加
	public boolean add(Blogger blogger) {
		boolean success=false;
		KeyHolder keyHolder = new GeneratedKeyHolder();
    	MapSqlParameterSource parameters = new MapSqlParameterSource();
    	parameters.addValue("username", blogger.getUsername());
    	parameters.addValue("password", blogger.getPassword());
    	parameters.addValue("profile", blogger.getProfile());
    	parameters.addValue("nickname", blogger.getNickname());
    	parameters.addValue("sign", blogger.getSign());
//    	parameters.addValue("imagename", "");
    	
		success=namedJdbcTemplate.update("insert into blogger(username, password, profile, nickname, sign) values(:username, :password, :profile, :nickname, :sign)",parameters,keyHolder)>0;
		
		int autoIncId = keyHolder.getKey().intValue();
		
		//如果有上传文件,则更新文件名
		if(blogger.getImagename()!=null) {
			String postfix = blogger.getImagename().substring(blogger.getImagename().lastIndexOf("."));
			String imageName="u"+blogger.getImagename()+postfix;
			blogger.setImagename(imageName); //修改为新的名称
			blogger.setId(autoIncId);
			return jdbcTemplate.update("update blogger set imagename=? where id=?",imageName,autoIncId)>0;
		}
		return success;
		
	}
	
	// 更新
	public boolean update(Blogger blogger) {
		
		return jdbcTemplate.update("update blogger SET username=?, password=?, profile=?, nickname=?, sign=?, imagename=?   where id =? ",blogger.getUsername(), blogger.getPassword(), blogger.getProfile(), blogger.getNickname(), blogger.getSign(), blogger.getImagename(), blogger.getId())>0;
	}
	
	// 更新 不更新图片路径
	public boolean update2(Blogger blogger) {
		return jdbcTemplate.update("update blogger SET username=?, password=?, profile=?, nickname=?, sign=?   where id =? ",blogger.getUsername(), blogger.getPassword(), blogger.getProfile(), blogger.getNickname(), blogger.getSign(),blogger.getId())>0;
	}
	
	
	// 删除
	public boolean delete(Integer id) {
		return jdbcTemplate.update("delete from blogger where id=?",id) >0;
	}

	public Blogger findByName(String user) {
		return jdbcTemplate.queryForObject("select * from blogger where username=?", new BeanPropertyRowMapper<Blogger>(Blogger.class),user);
	}

	public Blogger verify(String username, String password) {
		Integer count=jdbcTemplate.queryForObject("select count(*) from blogger where username=? and password=?", Integer.class,username,password);
		if(count==1)
			return jdbcTemplate.queryForObject("select * from blogger where username=? and password=?", new BeanPropertyRowMapper<Blogger>(Blogger.class),username,password);
		else
			return null;
	}


}
