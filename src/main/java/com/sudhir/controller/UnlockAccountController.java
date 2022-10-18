package com.sudhir.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sudhir.model.UnlockAccount;
import com.sudhir.service.UserService;

@Controller
public class UnlockAccountController {

	@Autowired
	private UserService userService;
	/**
	 * This method is used load unlock-account form
	 * @param email
	 * @param model
	 * @return String
	 */
	@GetMapping("/unlockAcc")
	public String loadUnlockAccForm(@RequestParam("email") String email, Model model) {
		UnlockAccount account=new UnlockAccount();
		account.setEmail(email);
		model.addAttribute("userAcc", account);
		return "unlockAcc";
	}

	/**
	 * This method is used to handle unlock-account form submission
	 * @param unlockAcc
	 * @param model
	 * @return String
	 */
	@PostMapping("/unlockAccount")
	public String handleSubmitBtn(@ModelAttribute("userAcc") UnlockAccount unlockAcc, Model model) {
		boolean isValid=userService.isTempPwdValid(unlockAcc.getEmail(), unlockAcc.getTempPwd());
		if(isValid) {
			userService.unlockAccount(unlockAcc.getEmail(), unlockAcc.getNewPwd());
			model.addAttribute("succMsg", "Your Accout Unlocked Successfully.<a href=\"index\">Login Here</a>");
		}else {
			model.addAttribute("failMsg", "Please Enter Correct Temporary Password");
		}
		return "unlockAcc";
	}

}