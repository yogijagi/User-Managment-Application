package com.sudhir.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sudhir.property.AppProperties;

@RestController
public class DemoRestController {

	@Autowired
	private AppProperties appProps;

	@GetMapping("/welcome")
	public String getWelcomeMsg() {
		String welcomeMsg = appProps.getMessages().get("welcomeMsg");
		return welcomeMsg;
	}

	@GetMapping("/greet")
	public String greetMsg() {
		String greetMsg = appProps.getMessages().get("greetMsg");
		return greetMsg;
	}

}
