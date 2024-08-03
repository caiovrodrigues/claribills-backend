package com.tech.claribills.controllers;

import com.tech.claribills.dtos.*;
import com.tech.claribills.services.DividaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/dividas")
public class DividaController {

    private final DividaService dividaService;

    @GetMapping
    public ResponseEntity<List<DividaResponseDTO>> getAll(){
        return ResponseEntity.ok(this.dividaService.getAll());
    }

    @PostMapping
    public ResponseEntity<Void> post(@RequestBody DividaCreateRequestDTO data){
        this.dividaService.create(data);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/participant")
    public ResponseEntity<Void> addParticipantInADivida(@RequestBody DividaParticipantCreateRequestDTO data){
        dividaService.addParticipantEmUmaDivida(data);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<DividaAtualizarResponseDTO> atualizarDivida(@PathVariable Integer id, @RequestBody DividaAtualizarDTO dividaData){
        DividaAtualizarResponseDTO dividaAtualizarResponseDTO = dividaService.atualizarDivida(id, dividaData);
        return ResponseEntity.ok(dividaAtualizarResponseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDivida(@PathVariable("id") Integer idDivida){
        dividaService.delete(idDivida);
        return ResponseEntity.noContent().build();
    }

}
