package com.example.jwtsecuritydemo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 模拟user的页面,访问本页面需要登陆
 */
@RestController
public class MainController {
	
	@GetMapping("/user")
	public String accessUserPage() {
		return "access success!";
	}
}
