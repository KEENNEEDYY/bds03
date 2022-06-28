package com.devsuperior.bds03.services.validation;

import javax.validation.Constraint;

@Constraint(validatedBy =  UserUpdateValidator.class)

public @interface UserUpdateValid {
    
}
