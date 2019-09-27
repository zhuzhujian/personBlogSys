package com.blog.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.blog.entitys.Blog;
import com.blog.entitys.Blogger;
import com.blog.entitys.Blogtype;
import com.blog.beans.PageBean;
import com.blog.beans.StatusBean;
import com.blog.service.BlogService;
import com.blog.service.BlogtypeService;


@Controller
@RequestMapping("/admin/blog/")
public class BlogController {
	@Autowired
	private BlogService service;
	@Autowired
	private BlogtypeService typeservice;
	
	
	//调出写博客页面
	@RequestMapping("writeBlog.do")
	public String writeBlog(Integer id,Map map, HttpSession session) {
		Blogger user = (Blogger) session.getAttribute("user");
		List<Blogtype> blogtypeList = typeservice.listAll(user.getId());
		map.put("blogtypeList", blogtypeList);
		
		if(id!=null) { //修改
			map.put("blogid", id);
		}
		return "/admin/writeBlog";
	}

	@RequestMapping("find.do")
	@ResponseBody
	public StatusBean find(Integer id,Map map) {
		System.out.println("id="+id);
		boolean success=false;
		if(id!=null) {
			Blog blog=service.findById(id);
			return new StatusBean(true,"查询状态："+success, blog);
		}
		else return new StatusBean(false,"id不能为空");
	}
	
	//添加和更新博客
	@RequestMapping("/save.do")
	@ResponseBody
	public StatusBean save(Blog blog,HttpSession session) throws Exception {
		System.out.println(blog.getId());
		System.out.println(blog.getBlogtype().getId());
		System.out.println(blog.getContent());
		
		Blogger user = (Blogger) session.getAttribute("user");
		if(user==null) return new StatusBean(false,"session失效");
		
		int resultTotal = 0; //接收返回结果记录数
		boolean success=false;
		if(blog.getId() == null) { //说明是第一次插入
			
			blog.setUser_id(user.getId());  //设置blog发表人
			
			blog.setReleaseDate(new Date());
			blog.setReplyHit(0);
			blog.setClickHit(0);
			success = service.add(blog);
			return new StatusBean(success,"保存状态："+success);
//			blogIndex.addIndex(blog); //添加博客的索引
		} else { //有id表示修改
			
			blog.setReleaseDate(new Date());
			success=service.update2(blog);
		}
		return new StatusBean(success,"保存状态："+success);
	}
	
	//----------------------------------------------
	
	/**
	 * 分页查询{TABLE_CNAME}
	 */
	//接收easyui datagrid的分页数据请求,page为第几页，rows为每页多少行
	@RequestMapping("list.do")
	@ResponseBody
	public PageBean list(Integer page,Integer rows,HttpSession session) {
		Blogger user = (Blogger) session.getAttribute("user");
		Map cond=new HashMap();
		cond.put("user_id", user.getId());
		return  service.list(page,rows,cond);
	}
	
	/**
	 * 删除博客
	 */
	@RequestMapping("delete.do")
	@ResponseBody
	public StatusBean delete(Integer id){
		boolean success=service.delete(id);
		return new StatusBean(success,"删除状态："+success);
	}

	/**
	 * 添加提交博客
	 */
	@RequestMapping("add_submit.do")
	@ResponseBody
	public StatusBean add_submit(Blog blog)  {
		boolean success=service.add(blog);
		return new StatusBean(success,"添加状态："+success);
	}

	/**
	 * 修改博客
	 */
	@RequestMapping("modify.do")
	@ResponseBody
	public Blog modify(Integer id)  {
		return service.findById(id);
	}

	/**
	 * 修改提交博客
	 */
	@RequestMapping("modify_submit.do")
	@ResponseBody
	public StatusBean modify_submit(Blog blog) {
		boolean success=service.update(blog);
		return new StatusBean(success,"修改状态："+success);
	}
	
	
}


