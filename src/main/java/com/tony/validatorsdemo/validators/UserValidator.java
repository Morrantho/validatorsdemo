package com.tony.validatorsdemo.validators;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.tony.validatorsdemo.models.User;

@Component
public class UserValidator implements Validator{
	private static final int MIN_PASSWORD_LENGTH = 8;
	private static final String EMAIL_RE = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	private Pattern pattern;
	private Matcher matcher;
	
	public UserValidator(){
		this.pattern = Pattern.compile(EMAIL_RE);
	}
	
	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.equals(clazz);
	}

	public boolean validEmail(String email){
		matcher = pattern.matcher(email);
		return matcher.matches();
	}	
	
	public boolean validDate(Date date){
		return date.before(new Date());
	}
	
	@Override
	public void validate(Object target, Errors errors){
		User user = (User) target;
		
		if(!user.getConfirm().equals(user.getPassword())){
			errors.rejectValue("confirm","Match");
		}
		
		if(user.getPassword().length() < UserValidator.MIN_PASSWORD_LENGTH){
			errors.rejectValue("password","Length");
		}
		
		if(!validEmail(user.getUsername())){
			errors.rejectValue("email","Match");
		}
		
		if(!validDate(user.getDob())){
			errors.rejectValue("dob","InvalidDate");
		}
	}
}
