package com.tech.claribills.infrastrucure.exceptions.classes;

import com.tech.claribills.infrastrucure.exceptions.ExceptionConstants;

public class CredenciaisInvalidasException extends RuntimeException{

    public CredenciaisInvalidasException(){
        super(ExceptionConstants.CREDENCIAIS_INVALIDAS);
    }

}
