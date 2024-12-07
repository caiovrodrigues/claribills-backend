package com.tech.claribills.services;

import com.tech.claribills.dtos.*;
import com.tech.claribills.entity.Divida;
import com.tech.claribills.entity.ParticipanteDividas;
import com.tech.claribills.entity.ParticipanteDividasStatus;
import com.tech.claribills.entity.Usuario;
import com.tech.claribills.entity.mapper.DividaMapper;
import com.tech.claribills.infrastrucure.exceptions.classes.DividaNotFoundException;
import com.tech.claribills.infrastrucure.exceptions.classes.UserNotOwnerException;
import com.tech.claribills.repositories.DividaRepository;
import com.tech.claribills.repositories.ParticipanteDividasRepository;
import com.tech.claribills.repositories.ParticipanteDividasStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

@Transactional
@RequiredArgsConstructor
@Service
public class DividaService {

    private final DividaRepository dividaRepository;
    private final UsuarioService usuarioService;
    private final ParticipanteDividasRepository participanteDividasRepository;
    private final ParticipanteDividasStatusRepository dividasStatusRepository;

    public List<DividaResponseDTO> getAll() {
        return this.dividaRepository.findAll().stream().map(DividaResponseDTO::new).toList();
    }

    public Divida findById(Integer id){
        return dividaRepository.findById(id).orElseThrow(DividaNotFoundException::new);
    }

    public void create(DividaCreateRequestDTO data, JwtAuthenticationToken token) {
        Usuario usuario = usuarioService.findById(Integer.valueOf(token.getName()));
        ParticipanteDividasStatus statusAccepted = dividasStatusRepository.findByStatus(ParticipanteDividasStatus.ACCEPTED);
        Divida divida = Divida.builder()
                .name(data.name())
                .numberInstallments(data.numberInstallments())
                .totalAmount(data.totalAmount())
                .owner(usuario)
                .participants(new HashSet<>())
                .build();

        divida.getParticipants().add(ParticipanteDividas.builder().usuario(usuario).divida(divida).status(statusAccepted).build());
        dividaRepository.save(divida);
    }

    public void addParticipantEmUmaDivida(Integer dividaId, Integer participantId, JwtAuthenticationToken token) {
        Divida divida = findById(dividaId);
        if (!divida.getOwner().getId().equals(Integer.valueOf(token.getName()))) {
            throw new UserNotOwnerException();
        }
        Usuario participant = usuarioService.findById(participantId);

        ParticipanteDividasStatus pending = dividasStatusRepository.findByStatus(ParticipanteDividasStatus.PENDING);

        ParticipanteDividas participantDivida = ParticipanteDividas.builder().divida(divida).usuario(participant).status(pending).build();
        divida.getParticipants().add(participantDivida);
        dividaRepository.save(divida);
    }

    public void delete(Integer idDivida, JwtAuthenticationToken token) {
        Usuario usuario = usuarioService.findById(Integer.valueOf(token.getName()));
        Divida divida = findById(idDivida);
        System.out.println(token.getAuthorities());
        if (!(divida.getOwner().equals(usuario) || token.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("SCOPE_ADMIN")))) {
            throw new UserNotOwnerException();
        }
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

    public List<DividaResponseDTO> getAllDividasEDividasParticipants(JwtAuthenticationToken token) {
        Usuario usuario = usuarioService.findById(Integer.valueOf(token.getName()));
        return dividaRepository.findByOwnerOrParticipantsUsuarioAndParticipantsStatusStatus(usuario, usuario, ParticipanteDividasStatus.ACCEPTED)
                .stream().map(DividaResponseDTO::new).toList();
    }

    public List<DividasConviteResponseDTO> getConvitesPendentes(JwtAuthenticationToken token) {
        Usuario usuario = usuarioService.findById(Integer.valueOf(token.getName()));
        return DividaMapper.INSTANCE.convertParticipanteDividasToDto(usuario.getDividasParticipant())
                .stream().filter(d -> !d.divida().owner().id().equals(Integer.valueOf(token.getName()))).toList();
    }

    public void respondeConviteParaDivida(Integer dividaConviteId, ConviteResponseDTO response, JwtAuthenticationToken token) {

        ParticipanteDividas participanteDivida = participanteDividasRepository.findById(dividaConviteId).orElseThrow();
        Usuario usuario = usuarioService.findById(Integer.valueOf(token.getName()));

        if (!participanteDivida.getUsuario().equals(usuario)) {
            throw new UserNotOwnerException();
        }

        ParticipanteDividasStatus newStatus = dividasStatusRepository.findByStatus(response.action());
        participanteDivida.setStatus(newStatus);
        participanteDividasRepository.save(participanteDivida);
    }

}
