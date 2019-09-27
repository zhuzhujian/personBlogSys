package com.blog.service;

import java.util.Map;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blog.dao.BlogDao;
import com.blog.dao.CommentDao;
import com.blog.entitys.Blog;
import com.blog.entitys.Comment;
import com.blog.beans.PageBean;

@Service
@Transactional
public class CommentService {

	@Autowired
	CommentDao dao;
	@Autowired
	BlogDao blogdao;
	
	// 根据id查找评论
	@Transactional(readOnly=true)
	public Comment findById(Integer id) {
		return dao.findById(id);
	}

	//全部记录
	@Transactional(readOnly=true)
	public List<Comment> listAll(){
		return dao.listAll();
	}

	// 分页查询评论
	@Transactional(readOnly=true)
	public PageBean list(Integer pgNo,Integer pgSize,Map<String,Object> params) {
		return dao.list(pgNo,pgSize,params);		
	}
	
	// 添加评论
	public boolean add(Comment comment) {
		//保存评论
		dao.add(comment);
		//更新一下博客的评论次数
		blogdao.updateReplyHit(comment.getBlog_id());
		
		return true;
	}
	
	// 更新评论
	public boolean update(Comment comment) {
		return dao.update(comment);
	}
	
	// 删除评论
	public boolean delete(Integer id) {
		return dao.delete(id);
	}

	public List<Comment> getListByBlogId(Integer blogId) {
		return dao.getListByBlogId(blogId);
	}

	public boolean review(Integer id, Integer state) {
		return dao.review(id,state);
		
	}
}
