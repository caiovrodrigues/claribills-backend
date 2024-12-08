package com.tech.claribills.controllers;

import com.tech.claribills.dtos.BancoCreateDTO;
import com.tech.claribills.entity.Banco;
import com.tech.claribills.infrastrucure.security.RoleAdmin;
import com.tech.claribills.infrastrucure.security.RoleUser;
import com.tech.claribills.services.BancoService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/bancos")
public class BancoController {

    private final BancoService bancoService;

    @RoleUser
    @GetMapping
    public ResponseEntity<List<Banco>> getAllBancos(){
        return ResponseEntity.ok(bancoService.getAllBancos());
    }

    @RoleUser
    @GetMapping("/{id}")
    public ResponseEntity<Banco> getBancoById(@PathVariable Integer id){
        return ResponseEntity.ok(bancoService.getBancoById(id));
    }

    @RoleAdmin
    @PostMapping
    public ResponseEntity<Banco> postBanco(@RequestBody BancoCreateDTO banco, UriComponentsBuilder uriBuilder, HttpServletRequest request){
        Banco newBanco = bancoService.createNewBanco(banco);
        URI uri = uriBuilder.path(request.getServletPath().concat("/{id}")).buildAndExpand(newBanco.getId()).toUri();
        return ResponseEntity.created(uri).body(newBanco);
    }

    @RoleAdmin
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBanco(@PathVariable Integer id){
        bancoService.deleteBancoPeloId(id);
        return ResponseEntity.noContent().build();
    }

}
