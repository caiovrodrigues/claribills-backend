package com.tech.claribills.controllers;

import com.tech.claribills.dtos.*;
import com.tech.claribills.services.DividaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/dividas")
public class DividaController {

    private final DividaService dividaService;

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @GetMapping("/todas")
    public ResponseEntity<List<DividaResponseDTO>> getAllDividas(){
        return ResponseEntity.ok(this.dividaService.getAll());
    }

    @GetMapping
    public ResponseEntity<List<DividaResponseDTO>> getAllDividasEParticipants(JwtAuthenticationToken token){
        return ResponseEntity.ok(dividaService.getAllDividasEDividasParticipants(token));
    }

    @GetMapping("/convites")
    public ResponseEntity<List<DividasConviteResponseDTO>> getConvitesPendentes(JwtAuthenticationToken token){
        return ResponseEntity.ok(dividaService.getConvitesPendentes(token));
    }

    @PostMapping
    public ResponseEntity<Void> criarDivida(@RequestBody DividaCreateRequestDTO data, JwtAuthenticationToken token){
        this.dividaService.create(data, token);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{dividaId}/participants/{participantId}")
    public ResponseEntity<Void> addParticipantInADivida(@PathVariable Integer dividaId, @PathVariable Integer participantId, JwtAuthenticationToken token){
        dividaService.addParticipantEmUmaDivida(dividaId, participantId, token);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/convites/{dividaConviteId}")
    public ResponseEntity<Void> responderConvitesParaDivida(@RequestBody ConviteResponseDTO conviteResponse, @PathVariable Integer dividaConviteId, JwtAuthenticationToken token){
        dividaService.respondeConviteParaDivida(dividaConviteId, conviteResponse, token);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<DividaAtualizarResponseDTO> atualizarDivida(@PathVariable Integer id, @RequestBody DividaAtualizarDTO dividaData){
        DividaAtualizarResponseDTO dividaAtualizarResponseDTO = dividaService.atualizarDivida(id, dividaData);
        return ResponseEntity.ok(dividaAtualizarResponseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDivida(@PathVariable("id") Integer idDivida, JwtAuthenticationToken token){
        dividaService.delete(idDivida, token);
        return ResponseEntity.noContent().build();
    }

}
