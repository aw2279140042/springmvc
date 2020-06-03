package com.springmvc.hello.service;

import org.springframework.stereotype.Service;

@Service("helloService")
public class HelloService {
	public String sayHello(String name){
		return "hello "+name;
	}
}
