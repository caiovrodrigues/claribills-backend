package com.tech.claribills.infrastrucure.exceptions;

import lombok.Getter;

@Getter
public enum ExcepConst {

    USUARIO_NOT_OWNER("Usuário não tem permissão sobre esse recurso"),
    CREDENCIAIS_INVALIDAS("Login ou senha inválido"),
    USUARIO_NOT_FOUND("Usuário não encontrado"),
    DIVIDA_NOT_FOUND("Dívida não encontrada"),
    BANCO_NOT_FOUND("Banco não encontrado"),
    DIVIDA_PARTICIPANT_NOT_FOUND("Dívida participante não encontrado.");

    final String message;

    ExcepConst(String msg){
        this.message = msg;
    }
}
