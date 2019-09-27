package com.blog.service;

import java.util.Map;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blog.dao.LinkDao;
import com.blog.entitys.Link;
import com.blog.beans.PageBean;

@Service
@Transactional
public class LinkService {

	@Autowired
	LinkDao dao;
	
	// 根据id查找友情链接
	@Transactional(readOnly=true)
	public Link findById(Integer id) {
		return dao.findById(id);
	}

	//全部记录
	@Transactional(readOnly=true)
	public List<Link> listAll(){
		return dao.listAll();
	}

	// 分页查询友情链接
	@Transactional(readOnly=true)
	public PageBean list(Integer pgNo,Integer pgSize,Map<String,Object> params) {
		return dao.list(pgNo,pgSize,params);		
	}
	
	// 添加友情链接
	public boolean add(Link link) {
		return dao.add(link);
	}
	
	// 更新友情链接
	public boolean update(Link link) {
		return dao.update(link);
	}
	
	// 删除友情链接
	public boolean delete(Integer id) {
		return dao.delete(id);
	}
}
