package com.tech.claribills.repositories;

import com.tech.claribills.entity.Divida;
import com.tech.claribills.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
}
