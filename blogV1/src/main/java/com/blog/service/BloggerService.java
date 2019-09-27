package com.blog.service;

import java.util.Map;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.blog.dao.BloggerDao;
import com.blog.entitys.Blogger;
import com.blog.beans.PageBean;

@Service
@Transactional
public class BloggerService {

	@Autowired
	BloggerDao dao;
	
	// 根据id查找博主
	@Transactional(readOnly=true)
	public Blogger findById(Integer id) {
		return dao.findById(id);
	}

	//全部记录
	@Transactional(readOnly=true)
	public List<Blogger> listAll(){
		return dao.listAll();
	}

	// 分页查询博主
	@Transactional(readOnly=true)
	public PageBean list(Integer pgNo,Integer pgSize,Map<String,Object> params) {
		return dao.list(pgNo,pgSize,params);		
	}
	
	// 更新博主
	public boolean update(Blogger blogger, String rootPath, CommonsMultipartFile imageFile) throws Exception {
		File file =new File(rootPath+blogger.getImagename());
		imageFile.transferTo(file);  //保存文件到磁盘
		
		return dao.update(blogger);
	}
	
	public boolean update(Blogger blogger) {
		return dao.update2(blogger);
	}
	
	
	// 删除博主
	public boolean delete(Integer id) {
		return dao.delete(id);
	}

	public Blogger findByName(String user) {
		return dao.findByName(user);
	}

	// 添加博主
	public boolean add(Blogger blogger, String rootPath, CommonsMultipartFile imagefile) {
		boolean success=dao.add(blogger);
		
		//如果有上传文件，则保存文件
		if(imagefile!=null) {
			System.out.println("filename="+blogger.getImagename());
			File file =new File(rootPath+blogger.getImagename());
		}
		return success;
	}

	public Blogger verify(String username, String password) {
		return dao.verify(username,password);
	}

}
