package com.blog.controller;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.blog.entitys.Blogger;
import com.blog.entitys.Blogtype;
import com.blog.beans.PageBean;
import com.blog.beans.StatusBean;
import com.blog.service.BlogtypeService;


@Controller
@RequestMapping("/admin/blogtype/")
@ResponseBody
public class BlogtypeController {
	@Autowired
	private BlogtypeService service;
	
	/**
	 * 分页查询{TABLE_CNAME}
	 */
	//接收easyui datagrid的分页数据请求,page为第几页，rows为每页多少行
	@RequestMapping("list.do")
	public PageBean list(Integer page,Integer rows, HttpSession session) {
		Blogger user = (Blogger) session.getAttribute("user");
		HashMap cond = new HashMap();
		cond.put("user_id", user.getId());
		return  service.list(page,rows,cond);
	}
	
	/**
	 * 删除博客类型
	 */
	@RequestMapping("delete.do")
	public StatusBean delete(Integer id){
		boolean success=service.delete(id);
		return new StatusBean(success,"删除状态："+success);
	}

	/**
	 * 添加提交博客类型
	 */
	@RequestMapping("add_submit.do")
	public StatusBean add_submit(Blogtype blogtype, HttpSession session)  {
		Blogger user = (Blogger) session.getAttribute("user");
		blogtype.setUser_id(user.getId());
		boolean success=service.add(blogtype);
		return new StatusBean(success,"添加状态："+success);
	}

	/**
	 * 修改博客类型
	 */
	@RequestMapping("modify.do")
	public Blogtype modify(Integer id)  {
		return service.findById(id);
	}

	/**
	 * 修改提交博客类型
	 */
	@RequestMapping("modify_submit.do")
	public StatusBean modify_submit(Blogtype blogtype) {
		boolean success=service.update(blogtype);
		return new StatusBean(success,"修改状态："+success);
	}
	
	
}


