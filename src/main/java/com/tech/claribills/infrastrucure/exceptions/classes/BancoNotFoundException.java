package com.tech.claribills.infrastrucure.exceptions.classes;

import com.tech.claribills.infrastrucure.exceptions.ExceptionConstants;

public class BancoNotFoundException extends EntityNotFoundException{

    public BancoNotFoundException(){
        super(ExceptionConstants.BANCO_NOT_FOUND);
    }

}
