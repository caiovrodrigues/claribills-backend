package com.tech.claribills.controllers;

import com.tech.claribills.dtos.AuthenticationRequestDTO;
import com.tech.claribills.dtos.TokenResponse;
import com.tech.claribills.dtos.UsuarioCreateRequestDTO;
import com.tech.claribills.entity.Usuario;
import com.tech.claribills.infrastrucure.security.RoleAdmin;
import com.tech.claribills.services.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @RoleAdmin
    @GetMapping
    public ResponseEntity<List<Usuario>> getAllUsers(){
        return ResponseEntity.ok(this.usuarioService.getAll());
    }

    @PostMapping
    public ResponseEntity<Void> createUser(@Valid @RequestBody UsuarioCreateRequestDTO user){
        this.usuarioService.create(user);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/auth")
    public ResponseEntity<TokenResponse> authentication(@Valid @RequestBody AuthenticationRequestDTO credentials){
        TokenResponse tokenResponse = usuarioService.verifyAuthentication(credentials);
        return ResponseEntity.ok(tokenResponse);
    }

}
