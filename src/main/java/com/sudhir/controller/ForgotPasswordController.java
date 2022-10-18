package com.sudhir.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.sudhir.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
@Controller
public class ForgotPasswordController {
	
	@Autowired
	private UserService userService;
	
	/**
	 * This method is used to load forgot password form
	 * 
	 * @return String
	 */
	@GetMapping("/forgotPwd")
	public String loadForm() {
		return "forgotPwd";
	}

	/**
	 * This is method is used to handle forgot password screen submit btn
	 * 
	 * @param req
	 * @param model
	 * @return String
	 */
	@PostMapping("/forgotPwd")
	public String handleForgotPwdSubmtBtn(HttpServletRequest req, Model model) {
		String email=req.getParameter("email");
		String status=userService.recoverPassword(email);
		if(status.equals("SUCCESS")) {
			model.addAttribute("succMsg", "Password sent to your email");
		}else {
			model.addAttribute("failMsg", "Please enter valid Email");
		}
		return "forgotPwd";
	}
}