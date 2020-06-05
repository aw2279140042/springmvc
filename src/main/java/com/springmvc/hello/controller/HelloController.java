package com.springmvc.hello.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.springmvc.common.BaseController;
import com.springmvc.entity.TUser;
import com.springmvc.hello.service.HelloService;

@Controller
@RequestMapping("hello")
public class HelloController extends BaseController {
	@Resource(name="helloService")
	private HelloService helloService;
	
	@RequestMapping("sayHello")
	@ResponseBody
	public String sayHello(Integer id) throws Exception{
		log.info("id:"+id);
		if(1==1)throw new Exception("sdfsdffs");
		return helloService.sayHello(id);
	}
	
	@RequestMapping("save")
	public void save(TUser user) throws Exception{
		if(1==1)throw new Exception("sdfsdffs");
		log.info("user:"+user.getName());
		helloService.save(user);
		super.writeJSON();
	}
}
