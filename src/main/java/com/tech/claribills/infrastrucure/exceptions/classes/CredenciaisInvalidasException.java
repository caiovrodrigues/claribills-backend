package com.tech.claribills.infrastrucure.exceptions.classes;

import com.tech.claribills.infrastrucure.exceptions.ExcepConst;

public class CredenciaisInvalidasException extends RuntimeException{

    public CredenciaisInvalidasException(){
        super(ExcepConst.CREDENCIAIS_INVALIDAS.getMessage());
    }

}
