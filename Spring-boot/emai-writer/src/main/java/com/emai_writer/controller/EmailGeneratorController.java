package com.emai_writer.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/email")
public class EmailGeneratorController {
	public ResponseEntity<String> generatorEmail(@RequestBody EmailRequest emailRequest){
		return ResponseEntity.ok("");
	}
}
