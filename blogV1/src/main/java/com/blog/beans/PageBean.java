package com.blog.beans;


import java.util.ArrayList;
import java.util.List;

/**
 * 存放分页相关的数据
 *
 */
public class PageBean {
	//传入参数
	int currentPage;
	int pageSize;
	
	private int startIndex;//每页开始记录的索引，计算出来的			    *

	
	public int getStartIndex() {
		return startIndex;
	}
	//返回给easyui的数据
	private Integer total;//总记录条数，数据库查出来的			    *
	private List rows;//已经分好页的结果集
	
	public PageBean(Integer currentPage, Integer pageSize) {
		this.currentPage = currentPage==null ? 1:currentPage;
		this.pageSize = pageSize==null? 5: pageSize;
		
		//计算查询记录的开始索引
		startIndex = (currentPage-1)*pageSize;
		
	}
	
	public int getCurrentPage() {
		return currentPage;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public List getRows() {
		return rows;
	}
	public void setRows(List rows) {
		this.rows = rows;
	}

	
}
