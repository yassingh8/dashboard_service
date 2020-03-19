package com.sapient.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DashBoardControllers {
	
	@GetMapping("/dashboard")
	public String getDashboard() {
		return "Hello from DashBoard";
	}
}
