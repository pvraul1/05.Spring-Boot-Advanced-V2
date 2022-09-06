package com.in28minutes.springboot.firstrestapi.helloworld;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@Controller
@RestController
public class HelloWorldResource {

	@RequestMapping("/hello-world")
	//@ResponseBody
	public String helloWorld() {
		return "Hello World";
	}

	@RequestMapping("/hello-world-bean")
	//@ResponseBody
	public HelloWorldBean helloWorldBean() {
		return new HelloWorldBean("Hello World");
	}

}
