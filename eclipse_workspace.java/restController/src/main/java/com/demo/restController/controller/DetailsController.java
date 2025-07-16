package com.demo.restController.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RestController;

import com.demo.restController.model.Details;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api")
public class DetailsController {
	
	public List<Details> detailsList = new ArrayList<>();
	
	@GetMapping("/details")
	public List<Details> getAllDetails(){
		return detailsList;
	}
	
	@PostMapping("/details")
	public String addDetails(@RequestBody Details details) {
		detailsList.add(details);
		return "Data Inserted succesfully";
	}
	
	@DeleteMapping("/details/{id}")
	public String deleteDetails(@PathVariable int id) {
		detailsList.removeIf(details -> details.getId() == id);
		return "Data Deleted Successfully";
	}
	
	
}
