package com.tech.claribills.infrastrucure.exceptions.classes;

import com.tech.claribills.infrastrucure.exceptions.ExceptionConstants;

public class UserNotOwnerException extends EntityNotFoundException{

    public UserNotOwnerException(){
        super(ExceptionConstants.USUARIO_NOT_OWNER);
    }

}
