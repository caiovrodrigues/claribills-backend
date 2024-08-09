package com.tech.claribills.controllers;

import com.tech.claribills.dtos.BancoCreateDTO;
import com.tech.claribills.entity.Banco;
import com.tech.claribills.services.BancoService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/bancos")
public class BancoController {

    private final BancoService bancoService;

    @GetMapping
    public ResponseEntity<List<Banco>> getAllBancos(){
        return ResponseEntity.ok(bancoService.getAllBancos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Banco> getBancoById(@PathVariable Integer id){
        return ResponseEntity.ok(bancoService.getBancoById(id));
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @PostMapping
    public ResponseEntity<Banco> postBanco(@RequestBody BancoCreateDTO banco, UriComponentsBuilder uriBuilder, HttpServletRequest request){
        Banco newBanco = bancoService.createNewBanco(banco);
        URI uri = uriBuilder.path(request.getServletPath().concat("/{id}")).buildAndExpand(newBanco.getId()).toUri();
        return ResponseEntity.created(uri).body(newBanco);
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBanco(@PathVariable Integer id){
        bancoService.deleteBancoPeloId(id);
        return ResponseEntity.noContent().build();
    }

}
