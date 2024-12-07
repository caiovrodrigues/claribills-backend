package com.tech.claribills.infrastrucure.exceptions.classes;

import com.tech.claribills.infrastrucure.exceptions.ExceptionConstants;

public class DividaNotFoundException extends EntityNotFoundException{

    public DividaNotFoundException(){
        super(ExceptionConstants.DIVIDA_NOT_FOUND);
    }

}
