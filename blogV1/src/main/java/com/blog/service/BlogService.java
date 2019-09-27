package com.blog.service;

import java.util.Map;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blog.dao.BlogDao;
import com.blog.entitys.Blog;
import com.blog.beans.PageBean;

@Service
@Transactional
public class BlogService {

	@Autowired
	BlogDao dao;
	
	// 根据id查找博客
	@Transactional(readOnly=true)
	public Blog findById(Integer id) {
		return dao.findById(id);
	}

	//全部记录
	@Transactional(readOnly=true)
	public List<Blog> listAll(){
		return dao.listAll();
	}

	// 分页查询博客
	@Transactional(readOnly=true)
	public PageBean list(Integer pgNo,Integer pgSize,Map<String,Object> params) {
		return dao.list(pgNo,pgSize,params);		
	}
	
	// 添加博客
	public boolean add(Blog blog) {
		return dao.add(blog);
	}
	
	// 更新博客
	public boolean update(Blog blog) {
		return dao.update(blog);
	}
	
	// 部分更新博客
	public boolean update2(Blog blog) {
		return dao.update2(blog);
	}
	
	// 删除博客
	public boolean delete(Integer id) {
		return dao.delete(id);
	}

	public Blog getPrevBlog(Integer id) {
		
		return dao.getPrevBlog(id);
	}

	public Blog getNextBlog(Integer id) {
		return dao.getNextBlog(id);
	}

	public List getBlogTimeList(Integer id) {
		return dao.getBlogTimeList(id);
	}
}
