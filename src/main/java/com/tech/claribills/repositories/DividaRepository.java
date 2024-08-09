package com.tech.claribills.repositories;

import com.tech.claribills.entity.Divida;
import com.tech.claribills.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DividaRepository extends JpaRepository<Divida, Integer> {

    List<Divida> findByOwnerOrParticipantsUsuarioAndParticipantsStatusStatus(Usuario usuario, Usuario participant, String status);

}
