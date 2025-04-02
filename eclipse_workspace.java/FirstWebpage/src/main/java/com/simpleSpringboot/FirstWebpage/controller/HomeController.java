package com.simpleSpringboot.FirstWebpage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;



@Controller
public class HomeController {
	//localhost:8080 and localhost:8080/
	
	@GetMapping({"","/"})
	public String home(){
		return "home";
	}	
}
//@RestController
//public class HomeController {
//
//    @GetMapping({"","/"})
//    public String home() {
//        return "Hello";
//    }
//}