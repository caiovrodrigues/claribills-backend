package com.tech.claribills.infrastrucure;

import com.tech.claribills.entity.*;
import com.tech.claribills.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Profile(value = {"local"})
@RequiredArgsConstructor
@Component
public class DatabaseInitialization implements CommandLineRunner{

    private final UsuarioRepository usuarioRepository;
    private final DividaRepository dividaRepository;
    private final BancoRepository bancoRepository;
    private final CartaoRepository cartaoRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final ParticipanteDividasStatusRepository dividasStatusRepository;

    @Override
    public void run(String... args) {

        Banco bradesco = new Banco(null, "Bradesco", "img.url.com");
        Banco itau = new Banco(null, "Itaú", "img.url.com");
        Banco caixa = new Banco(null, "Caixa", "img.url.com");

        Cartao cartao1 = new Cartao(null, "Pessoa 1", bradesco, 8);
        Cartao cartao2 = new Cartao(null, "Pessoa 2", itau, 22);

        bancoRepository.saveAll(List.of(bradesco, itau, caixa));
        cartaoRepository.saveAll(Arrays.asList(cartao1, cartao2));

        Role roleUser = new Role(null, "USER");
        Role roleAdmin = new Role(null, "ADMIN");

        roleRepository.saveAll(List.of(roleUser, roleAdmin));

        Usuario userCaio = Usuario.builder().name("Caio").username("caio").email("caio@gmail.com").password(passwordEncoder.encode("123456")).roles(Set.of(roleUser, roleAdmin)).build();
        Usuario userLara = Usuario.builder().name("Lara").username("lara").email("lara@gmail.com").password(passwordEncoder.encode("123456")).roles(Set.of(roleUser)).build();

        usuarioRepository.save(userCaio);
        usuarioRepository.save(userLara);

        ParticipanteDividasStatus statusAccepted = dividasStatusRepository.findByStatus(ParticipanteDividasStatus.ACCEPTED);

        Divida divida = Divida.builder()
                .name("Memória ram 16gb")
                .description("Comprei mais memória ram pra poder abrir mais de 3 abas no google chrome")
                .numberInstallments(3)
                .totalAmount(new BigDecimal(299))
                .cartao(cartao1)
                .owner(userCaio)
                .participants(new HashSet<>())
                .build();
        divida.getParticipants().add(ParticipanteDividas.builder().usuario(userCaio).divida(divida).status(statusAccepted).build());

        dividaRepository.save(divida);

    }
}
