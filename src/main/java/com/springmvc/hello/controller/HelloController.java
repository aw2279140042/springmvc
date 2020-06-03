package com.springmvc.hello.controller;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.springmvc.hello.service.HelloService;

@Controller
@RequestMapping("hello")
public class HelloController {
	private Log log = LogFactory.getLog(HelloController.class); 
	@Resource(name="helloService")
	private HelloService helloService;
	@RequestMapping("sayHello")
	@ResponseBody
	public String sayHello(String name){
		log.info("name:"+name);
		return helloService.sayHello(name);
	}
}
