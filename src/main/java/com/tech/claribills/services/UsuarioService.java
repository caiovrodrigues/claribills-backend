package com.tech.claribills.services;

import com.tech.claribills.dtos.UsuarioCreateRequestDTO;
import com.tech.claribills.entity.Usuario;
import com.tech.claribills.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public List<Usuario> getAll() {
        return this.usuarioRepository.findAll();
    }

    public void create(UsuarioCreateRequestDTO user) {
        this.usuarioRepository.save(user.toUsuario());
    }

    public Usuario findById(Integer id) {
        return usuarioRepository.findById(id).orElseThrow();
    }

}
