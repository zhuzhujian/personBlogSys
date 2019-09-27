package com.blog.service;

import java.util.Map;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blog.dao.BlogtypeDao;
import com.blog.entitys.Blogtype;
import com.blog.beans.PageBean;

@Service
@Transactional
public class BlogtypeService {

	@Autowired
	BlogtypeDao dao;
	
	// 根据id查找博客类型
	@Transactional(readOnly=true)
	public Blogtype findById(Integer id) {
		return dao.findById(id);
	}

	//全部记录
	@Transactional(readOnly=true)
	public List<Blogtype> listAll(Integer user_id){
		return dao.listAll(user_id);
	}

	// 分页查询博客类型
	@Transactional(readOnly=true)
	public PageBean list(Integer pgNo,Integer pgSize,Map<String,Object> params) {
		return dao.list(pgNo,pgSize,params);		
	}
	
	// 添加博客类型
	public boolean add(Blogtype blogtype) {
		return dao.add(blogtype);
	}
	
	// 更新博客类型
	public boolean update(Blogtype blogtype) {
		return dao.update(blogtype);
	}
	
	// 删除博客类型
	public boolean delete(Integer id) {
		return dao.delete(id);
	}

	public List getBlogtypeList(Integer user_id) {
		return dao.getBlogtypeList(user_id);
	}
}
