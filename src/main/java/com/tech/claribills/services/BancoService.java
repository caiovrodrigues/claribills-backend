package com.tech.claribills.services;

import com.tech.claribills.dtos.BancoCreateDTO;
import com.tech.claribills.entity.Banco;
import com.tech.claribills.infrastrucure.exceptions.ExcepConst;
import com.tech.claribills.repositories.BancoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BancoService {

    private final BancoRepository bancoRepository;

    public List<Banco> getAllBancos() {
        return bancoRepository.findAll();
    }

    public Banco getBancoById(Integer id) {
        return bancoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(ExcepConst.BANCO_NOT_FOUND.getMessage()));
    }

    public Banco createNewBanco(BancoCreateDTO banco) {
        Banco newBanco = new Banco(null, banco.name(), banco.cartaoImgUrl());
        return bancoRepository.save(newBanco);
    }

    public void deleteBancoPeloId(Integer id) {
        Banco banco = getBancoById(id);
        bancoRepository.delete(banco);
    }
}
