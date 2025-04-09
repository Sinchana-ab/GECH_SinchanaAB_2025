package com.spring.security.controller;

//import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
	//c2luY2hhbmE6c2luY2hhbmFAMTIz
	@GetMapping({"/",""})
	public String home() {
		return "Hello world";
	}
}

