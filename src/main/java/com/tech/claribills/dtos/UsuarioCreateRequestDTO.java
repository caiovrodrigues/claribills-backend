package com.tech.claribills.dtos;

import com.tech.claribills.entity.Usuario;

public record UsuarioCreateRequestDTO(String name, String username, String email, String password) {

    public Usuario toUsuario(){
        return Usuario.builder().name(name).username(username).email(email).password(password).build();
    }
}
