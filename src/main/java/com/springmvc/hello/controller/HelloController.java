package com.springmvc.hello.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.springmvc.common.BaseController;
import com.springmvc.hello.service.HelloService;

@Controller
@RequestMapping("hello")
public class HelloController extends BaseController {
	@Resource(name="helloService")
	private HelloService helloService;
	@RequestMapping("sayHello")
	@ResponseBody
	public String sayHello(String name){
		log.info("name:"+name);
		return helloService.sayHello(name);
	}
}
