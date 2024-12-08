package com.tech.claribills.controllers;

import com.tech.claribills.dtos.*;
import com.tech.claribills.infrastrucure.security.RoleAdmin;
import com.tech.claribills.infrastrucure.security.RoleUser;
import com.tech.claribills.services.DividaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/dividas")
public class DividaController {

    private final DividaService dividaService;

    @RoleAdmin
    @GetMapping("/todas")
    public ResponseEntity<List<DividaResponseDTO>> getAllDividas(){
        return ResponseEntity.ok(this.dividaService.getAll());
    }

    @RoleUser
    @GetMapping
    public ResponseEntity<List<DividaResponseDTO>> getAllDividasEParticipants(JwtAuthenticationToken token){
        return ResponseEntity.ok(dividaService.getAllDividasEDividasParticipants(token));
    }

    @RoleUser
    @GetMapping("/convites")
    public ResponseEntity<List<DividasConviteResponseDTO>> getConvitesPendentes(JwtAuthenticationToken token){
        return ResponseEntity.ok(dividaService.getConvitesPendentes(token));
    }

    @RoleUser
    @PostMapping
    public ResponseEntity<Void> criarDivida(@RequestBody DividaCreateRequestDTO data, JwtAuthenticationToken token){
        this.dividaService.create(data, token);
        return ResponseEntity.noContent().build();
    }

    @RoleUser
    @PostMapping("/{dividaId}/participants/{participantId}")
    public ResponseEntity<Void> addParticipantInADivida(@PathVariable Integer dividaId, @PathVariable Integer participantId, JwtAuthenticationToken token){
        dividaService.addParticipantEmUmaDivida(dividaId, participantId, token);
        return ResponseEntity.noContent().build();
    }

    @RoleUser
    @PostMapping("/convites/{dividaConviteId}")
    public ResponseEntity<Void> responderConvitesParaDivida(@RequestBody ConviteResponseDTO conviteResponse, @PathVariable Integer dividaConviteId, JwtAuthenticationToken token){
        dividaService.respondeConviteParaDivida(dividaConviteId, conviteResponse, token);
        return ResponseEntity.noContent().build();
    }

    @RoleUser
    @PutMapping("/{id}")
    public ResponseEntity<DividaAtualizarResponseDTO> atualizarDivida(@PathVariable Integer id, @RequestBody DividaAtualizarDTO dividaData, JwtAuthenticationToken user){
        DividaAtualizarResponseDTO dividaAtualizarResponseDTO = dividaService.atualizarDivida(id, dividaData, user);
        return ResponseEntity.ok(dividaAtualizarResponseDTO);
    }

    @RoleUser
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDivida(@PathVariable("id") Integer idDivida, JwtAuthenticationToken token){
        dividaService.delete(idDivida, token);
        return ResponseEntity.noContent().build();
    }

}
