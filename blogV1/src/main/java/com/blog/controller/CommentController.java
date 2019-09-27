package com.blog.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.blog.entitys.Blogger;
import com.blog.entitys.Comment;
import com.blog.beans.PageBean;
import com.blog.beans.StatusBean;
import com.blog.service.CommentService;


@Controller
@RequestMapping("/admin/comment/")
@ResponseBody
public class CommentController {
	@Autowired
	private CommentService service;
	
	
	// 评论审核
	@RequestMapping("/review.do")
	public StatusBean review(Integer id,Integer state) {
		boolean success=false;
		if(id!=null && state!=null)
			success=service.review(id,state);
	
		return new StatusBean(true,"审核完成");
	}
	
	
	/**
	 * 分页查询{TABLE_CNAME}
	 */
	//接收easyui datagrid的分页数据请求,page为第几页，rows为每页多少行
	@RequestMapping("list.do")
	public PageBean list(Integer page,Integer rows, HttpSession session) {
		Blogger user = (Blogger) session.getAttribute("user");
		Map cond=new HashMap();
		cond.put("user_id", user.getId());
		return  service.list(page,rows,cond);
	}
	
	/**
	 * 删除评论
	 */
	@RequestMapping("delete.do")
	public StatusBean delete(Integer id){
		boolean success=service.delete(id);
		return new StatusBean(success,"删除状态："+success);
	}

	/**
	 * 添加提交评论
	 */
	@RequestMapping("add_submit.do")
	public StatusBean add_submit(Comment comment)  {
		boolean success=service.add(comment);
		return new StatusBean(success,"添加状态："+success);
	}

	/**
	 * 修改评论
	 */
	@RequestMapping("modify.do")
	public Comment modify(Integer id)  {
		return service.findById(id);
	}

	/**
	 * 修改提交评论
	 */
	@RequestMapping("modify_submit.do")
	public StatusBean modify_submit(Comment comment) {
		boolean success=service.update(comment);
		return new StatusBean(success,"修改状态："+success);
	}
	
	
}


