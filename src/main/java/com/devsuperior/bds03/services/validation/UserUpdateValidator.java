package com.devsuperior.bds03.services.validation;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.devsuperior.bds03.dto.UserUpdateDTO;
import com.devsuperior.bds03.repositories.UserRepository;

public class UserUpdateValidator implements ConstraintValidator<UserUpdateValid, UserUpdateDTO>{

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private UserRepository repository;

    @Override
    public void initialize(UserUpdateValid ann){
    }

    @Override
    public boolean isValid(UserUpdateDTO value, ConstraintValidatorContext context) {
        // TODO Auto-generated method stub
        return false;
    }
    
}
