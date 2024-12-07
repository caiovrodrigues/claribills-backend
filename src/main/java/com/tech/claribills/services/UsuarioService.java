package com.tech.claribills.services;

import com.tech.claribills.dtos.AuthenticationRequestDTO;
import com.tech.claribills.dtos.TokenResponse;
import com.tech.claribills.dtos.UsuarioCreateRequestDTO;
import com.tech.claribills.entity.Role;
import com.tech.claribills.entity.Usuario;
import com.tech.claribills.infrastrucure.exceptions.classes.CredenciaisInvalidasException;
import com.tech.claribills.infrastrucure.exceptions.classes.UserNotFoundException;
import com.tech.claribills.repositories.RoleRepository;
import com.tech.claribills.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtEncoder jwtEncoder;
    private final RoleRepository roleRepository;

    public List<Usuario> getAll() {
        return this.usuarioRepository.findAll();
    }

    @Transactional
    public void create(UsuarioCreateRequestDTO user) {
        Role roleUser = roleRepository.findByName(Role.Values.USER.name());
        String encodedPassword = passwordEncoder.encode(user.password());
        this.usuarioRepository.save(user.toUsuario(roleUser, encodedPassword));
    }

    public Usuario findById(Integer id) {
        return usuarioRepository.findById(id).orElseThrow();
    }

    public TokenResponse verifyAuthentication(AuthenticationRequestDTO credentials) {
        Usuario usuario = usuarioRepository.findByEmailOrUsername(credentials.login(), credentials.login()).orElseThrow(UserNotFoundException::new);
        if (!passwordEncoder.matches(credentials.password(), usuario.getPassword())) {
            throw new CredenciaisInvalidasException();
        }

        Instant now = Instant.now();
        Instant expiresAt = now.plusSeconds(60 * 60 * 2);

        String scopes = usuario.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.joining(" "));

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("claribills-api")
                .notBefore(now)
                .issuedAt(now)
                .expiresAt(now.plusSeconds(60 * 60 * 2))
                .subject(String.valueOf(usuario.getId()))
                .claim("email", usuario.getEmail())
                .claim("scope", scopes)
                .build();

        String token = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
        return new TokenResponse(token, expiresAt);

    }
}
