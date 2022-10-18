package com.sudhir.controller;

import java.util.Map;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.sudhir.model.UserAccount;
import com.sudhir.service.UserService;

@Controller
public class RegistrationController {
	
	@Autowired
	private UserService userService;
	
	@ModelAttribute
	public void loadFormData(Model model) {
		UserAccount userAccountObj=new UserAccount();
		model.addAttribute("userAcc",userAccountObj);
		
		Map<Integer, String> countriesMap = userService.loadCountries();
		model.addAttribute("countries", countriesMap);
	}
	
	@GetMapping("/register")
	public String loadRegForm(Model model) {
		
		return "Registration";
	}

	@GetMapping("/uniqueMailCheck")
	public @ResponseBody String isEmailUnique(@RequestParam("email") String email) {
		return userService.isUniqueEmail(email) ? "UNIQUE" : "DUPLICATE";
	}

	@GetMapping("/countryChange")
	public @ResponseBody Map<Integer, String> handleCountryChangeEvnt(@RequestParam("countryId") Integer countryId) {
		return userService.loadStatesByCountryId(countryId);
	}

	@GetMapping("/stateChange")
	public @ResponseBody Map<Integer, String> handleStateChangeEvnt(@RequestParam("stateId") Integer stateId) {
		return userService.loadCitiesByStateId(stateId);
	}
	
	@PostMapping("/userRegistration")
	public String handleRegisterBtn(UserAccount userAcc, Model model) {
		boolean isSaved=userService.saveUserAccount(userAcc);
		if(isSaved) {
			model.addAttribute("succMsg", "Your Registration Almost completed.Please check your email to Unlock Your Account");
		}else {
			model.addAttribute("failMsg", "Registration Failed");
		}
		return "Registration";
	}

}