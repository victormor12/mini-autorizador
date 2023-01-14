package com.vr.miniautorizador.cartao.app;

import com.vr.miniautorizador.cartao.CriarCartaoUseCase;
import com.vr.miniautorizador.cartao.ObterSaldoUseCase;
import com.vr.miniautorizador.cartao.domain.CartaoDomainRepository;
import com.vr.miniautorizador.cartao.exception.CartaoJaExisteException;
import com.vr.miniautorizador.cartao.exception.CartaoNaoEncontratoException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@AutoConfigureMockMvc
class ObterSaldoAppServiceTests {

    @Autowired
    CriarCartaoUseCase criarCartao;

    @Autowired
    ObterSaldoUseCase obterSaldo;

    @Autowired
    CartaoDomainRepository repository;

    @Test
    void deveConsultarSaldoDoCartao() {
        var cmd = CriarCartaoUseCase.CriarCartaoCmd.builder()
            .numeroCartao("99999999999999999999")
            .senha("1234")
            .build();

        criarCartao.handle(cmd);

        var saldo = obterSaldo.handle(ObterSaldoUseCase.ObterSaldoCmd.builder()
            .numeroCartao(cmd.getNumeroCartao())
            .build());

        assertEquals(BigDecimal.valueOf(500), saldo);

        repository.deleteByNumeroCartao("99999999999999999999");
    }

    @Test
    void naoDeveCriarUmCartaoRepetido() {
        assertThrows(CartaoNaoEncontratoException.class, ()-> obterSaldo.handle(ObterSaldoUseCase.ObterSaldoCmd.builder()
            .numeroCartao("")
            .build()));
    }

}
