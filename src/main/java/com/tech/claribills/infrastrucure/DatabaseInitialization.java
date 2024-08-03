package com.tech.claribills.infrastrucure;

import com.tech.claribills.entity.*;
import com.tech.claribills.repositories.BancoRepository;
import com.tech.claribills.repositories.CartaoRepository;
import com.tech.claribills.repositories.DividaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Profile(value = {"local"})
@RequiredArgsConstructor
@Component
public class DatabaseInitialization implements CommandLineRunner{

    private final DividaRepository dividaRepository;
    private final BancoRepository bancoRepository;
    private final CartaoRepository cartaoRepository;

    @Override
    public void run(String... args) {

        Banco bradesco = new Banco(null, "Bradesco", "img.url.com");
        Banco itau = new Banco(null, "Itaú", "img.url.com");
        Banco caixa = new Banco(null, "Caixa", "img.url.com");

        Cartao cartao1 = new Cartao(null, "Pessoa 1", bradesco, 8);
        Cartao cartao2 = new Cartao(null, "Pessoa 2", itau, 22);

        bancoRepository.saveAll(List.of(bradesco, itau, caixa));
        cartaoRepository.saveAll(Arrays.asList(cartao1, cartao2));

        Usuario userCaio = Usuario.builder().name("Caio").username("caio").email("caio@gmail.com").password("123456").build();

        Divida divida = Divida.builder()
                .name("Memória ram 16gb")
                .description("Comprei uma memória ram pra poder abrir mais de 3 abas no google chrome")
                .numberInstallments(3)
                .totalAmount(new BigDecimal(299))
                .cartao(cartao1)
                .owner(userCaio)
                .participants(new HashSet<>())
                .build();
        divida.getParticipants().add(new ParticipanteDividas(null, userCaio, divida));

        dividaRepository.save(divida);

    }
}
