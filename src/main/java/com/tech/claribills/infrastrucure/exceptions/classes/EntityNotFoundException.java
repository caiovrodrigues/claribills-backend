package com.tech.claribills.infrastrucure.exceptions.classes;

public class EntityNotFoundException extends RuntimeException{

    public EntityNotFoundException(String msg){
        super(msg);
    }

}
