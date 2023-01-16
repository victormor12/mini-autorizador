package com.vr.miniautorizador.cartao.app;

import com.vr.miniautorizador.cartao.CriarCartaoUseCase;
import com.vr.miniautorizador.cartao.CriarCartaoUseCase.CriarCartaoCmd;
import com.vr.miniautorizador.cartao.IncluirTransacaoUseCase;
import com.vr.miniautorizador.cartao.IncluirTransacaoUseCase.IncluirTransacaoCmd;
import com.vr.miniautorizador.cartao.domain.CartaoDomainRepository;
import com.vr.miniautorizador.cartao.exception.TransacaoNegadaCartaoInexistenteException;
import com.vr.miniautorizador.cartao.exception.TransacaoNegadaSaldoInsuficienteException;
import com.vr.miniautorizador.cartao.exception.TransacaoNegadaSenhaInvalidaException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@AutoConfigureMockMvc
class IncluirTransacaoAppServiceTests {

    @Autowired
    CriarCartaoUseCase criarCartao;

    @Autowired
    IncluirTransacaoUseCase incluirTransacao;

    @Autowired
    CartaoDomainRepository repository;

    @Test
    void deveRealizarUmaTransacaoComSucesso() {
        var cmdCriar = CriarCartaoCmd.builder()
            .numeroCartao("99999999999999999999")
            .senha("1234")
            .build();

        criarCartao.handle(cmdCriar);

        var cmdIncluirTransacao = IncluirTransacaoCmd.builder()
            .numeroCartao("99999999999999999999")
            .senhaCartao("1234")
            .valor(BigDecimal.TEN)
            .build();

        incluirTransacao.handle(cmdIncluirTransacao);

        var cartao = repository.findByNumeroCartao(cmdCriar.getNumeroCartao()).orElse(null);

        assertEquals(BigDecimal.valueOf(490), cartao.getSaldo());

        repository.deleteByNumeroCartao("99999999999999999999");
    }

    @Test
    void naoDeveIncluirTransacaoComCartaoInexistente() {
        var cmdIncluirTransacao = IncluirTransacaoCmd.builder()
            .numeroCartao("99999999999999999999")
            .senhaCartao("1234")
            .valor(BigDecimal.TEN)
            .build();

        assertThrows(TransacaoNegadaCartaoInexistenteException.class, () -> incluirTransacao.handle(cmdIncluirTransacao));
    }

    @Test
    void naoDeveIncluirTransacaoComSenhaInvalida() {
        var cmdCriar = CriarCartaoCmd.builder()
            .numeroCartao("99999999999999999999")
            .senha("1234")
            .build();

        criarCartao.handle(cmdCriar);

        var cmdIncluirTransacao = IncluirTransacaoCmd.builder()
            .numeroCartao("99999999999999999999")
            .senhaCartao("12345")
            .valor(BigDecimal.TEN)
            .build();

        assertThrows(TransacaoNegadaSenhaInvalidaException.class, () -> incluirTransacao.handle(cmdIncluirTransacao));

        repository.deleteByNumeroCartao("99999999999999999999");
    }

    @Test
    void naoDeveIncluirTransacaoComSaldoInsuficiente() {
        var cmdCriar = CriarCartaoCmd.builder()
            .numeroCartao("99999999999999999999")
            .senha("1234")
            .build();

        criarCartao.handle(cmdCriar);

        var cmdIncluirTransacao = IncluirTransacaoUseCase.IncluirTransacaoCmd.builder()
            .numeroCartao("99999999999999999999")
            .senhaCartao("1234")
            .valor(BigDecimal.valueOf(600))
            .build();

        assertThrows(TransacaoNegadaSaldoInsuficienteException.class, () -> incluirTransacao.handle(cmdIncluirTransacao));

        repository.deleteByNumeroCartao("99999999999999999999");
    }

}
