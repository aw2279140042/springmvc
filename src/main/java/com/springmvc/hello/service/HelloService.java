package com.springmvc.hello.service;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.springmvc.entity.TUser;
import com.springmvc.hello.dao.UserDao;

@Service("helloService")
public class HelloService {
	private Log log = LogFactory.getLog(HelloService.class);
	@Resource(name="userDao")
	private UserDao userDao;
	public String sayHello(Integer  id){
		TUser vo = userDao.getById(id);
		return "hello "+vo.getName();
	}
	
	public void save(TUser vo){
		if(vo.getId() == null){
			userDao.insert(vo);
			log.info("TUser insert {id="+vo.getId()+",name="+vo.getName()+"}");
		}else{
			userDao.update(vo);
			log.info("TUser update {id="+vo.getId()+",name="+vo.getName()+"}");
		}	
		
	}
}
