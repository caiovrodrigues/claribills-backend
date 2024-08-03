package com.tech.claribills.services;

import com.tech.claribills.dtos.*;
import com.tech.claribills.entity.Divida;
import com.tech.claribills.entity.ParticipanteDividas;
import com.tech.claribills.entity.Usuario;
import com.tech.claribills.repositories.DividaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

@RequiredArgsConstructor
@Service
public class DividaService {

    private final DividaRepository dividaRepository;
    private final UsuarioService usuarioService;

    public List<DividaResponseDTO> getAll() {
        return this.dividaRepository.findAll().stream().map(DividaResponseDTO::new).toList();
    }

    public Divida findById(Integer id){
        return dividaRepository.findById(id).orElseThrow();
    }

    public void create(DividaCreateRequestDTO data) {
        Usuario usuario = usuarioService.findById(data.idUsuario());
        Divida divida = Divida.builder()
                .name(data.name())
                .numberInstallments(data.numberInstallments())
                .totalAmount(data.totalAmount())
                .owner(usuario)
                .participants(new HashSet<>())
                .build();
        divida.getParticipants().add(new ParticipanteDividas(null, usuario, divida));
        dividaRepository.save(divida);
    }

    public void addParticipantEmUmaDivida(DividaParticipantCreateRequestDTO data) {
        System.out.println(data);
        Divida divida = findById(data.dividaId());
        Usuario usuario = usuarioService.findById(data.usuarioId());
        ParticipanteDividas participantDivida = ParticipanteDividas.builder().divida(divida).usuario(usuario).build();
        divida.getParticipants().add(participantDivida);
        dividaRepository.save(divida);
    }

    public void delete(Integer idDivida) {
        Divida divida = findById(idDivida);
        dividaRepository.delete(divida);
    }

    public DividaAtualizarResponseDTO atualizarDivida(Integer id, DividaAtualizarDTO newDivida) {
        Divida divida = findById(id);
        List<String> listaCamposNewDivida = Arrays.stream(newDivida.getClass().getDeclaredFields()).filter(field -> {
            field.setAccessible(true);
            try {
                return Objects.nonNull(field.get(newDivida));
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }).map(Field::getName).toList();

        Arrays.stream(divida.getClass().getDeclaredMethods())
                .filter(method -> listaCamposNewDivida.contains(method.getName().substring(3,4).toLowerCase().concat(method.getName().substring(4))) && method.getName().startsWith("set"))
                .forEach(method -> {
                    try {
                        Object args = newDivida.getClass().getDeclaredMethod(method.getName().substring(3, 4).toLowerCase().concat(method.getName().substring(4))).invoke(newDivida, null);
                        method.invoke(divida, args);
                    } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                });

        dividaRepository.save(divida);
        return new DividaAtualizarResponseDTO(listaCamposNewDivida);
    }

}
