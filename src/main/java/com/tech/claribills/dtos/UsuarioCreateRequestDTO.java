package com.tech.claribills.dtos;

import com.tech.claribills.entity.Role;
import com.tech.claribills.entity.Usuario;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.util.Set;

public record UsuarioCreateRequestDTO(String name, String username, String email, @Min(6) @NotBlank String password) {

    public Usuario toUsuario(Role roleUser, String encodedPassword){
        return Usuario.builder().name(name).username(username).email(email).password(encodedPassword).roles(Set.of(roleUser)).build();
    }
}
