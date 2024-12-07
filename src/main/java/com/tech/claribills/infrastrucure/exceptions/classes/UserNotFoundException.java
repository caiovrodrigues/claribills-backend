package com.tech.claribills.infrastrucure.exceptions.classes;

import com.tech.claribills.infrastrucure.exceptions.ExceptionConstants;

public class UserNotFoundException extends EntityNotFoundException{

    public UserNotFoundException(){
        super(ExceptionConstants.USUARIO_NOT_FOUND);
    }

}
