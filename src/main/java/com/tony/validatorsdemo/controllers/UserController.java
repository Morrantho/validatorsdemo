package com.tony.validatorsdemo.controllers;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tony.validatorsdemo.models.User;
import com.tony.validatorsdemo.validators.UserValidator;

@Controller
@RequestMapping("/")
public class UserController {
	private UserValidator userValidator;
	
	public UserController(UserValidator userValidator){
		this.userValidator=userValidator;
	}
	
	@RequestMapping("")
	public String index(){
		return "index";
	}
	
	@RequestMapping("login")
	public String login(){
		return "login";
	}
	
	@RequestMapping("register")
	public String register(@Valid @ModelAttribute("user") User user){
		return "register";
	}
	
	@PostMapping("register")
	public String registerPost(@Valid @ModelAttribute("user") User user,BindingResult res){
		userValidator.validate(user,res);
		
		if(res.hasErrors()){
			return "register";
		}
		return "redirect:/login";
	}
}
