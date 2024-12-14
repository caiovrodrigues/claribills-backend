package com.tech.claribills.infrastrucure.exceptions.classes;

import com.tech.claribills.infrastrucure.exceptions.ExcepConst;

public class UserNotOwnerException extends RuntimeException {

    public UserNotOwnerException(){
        super(ExcepConst.USUARIO_NOT_OWNER.getMessage());
    }

}
