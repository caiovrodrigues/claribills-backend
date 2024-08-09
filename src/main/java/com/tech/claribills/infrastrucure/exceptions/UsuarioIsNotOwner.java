package com.tech.claribills.infrastrucure.exceptions;

import com.tech.claribills.infrastrucure.constants.Constants;

public class UsuarioIsNotOwner extends RuntimeException{

    public UsuarioIsNotOwner(){
        super(Constants.usuarioIsNotOwner);
    }

}
