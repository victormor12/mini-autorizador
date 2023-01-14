package com.vr.miniautorizador.cartao.app;

import com.vr.miniautorizador.cartao.CriarCartaoUseCase;
import com.vr.miniautorizador.cartao.domain.CartaoDomainRepository;
import com.vr.miniautorizador.cartao.exception.CartaoJaExisteException;
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
class CriarCartaoAppServiceTests {

    @Autowired
    CriarCartaoUseCase criarCartao;

    @Autowired
    CartaoDomainRepository repository;

    @Test
    void deveCriarUmCartao() {
        var cmd = CriarCartaoUseCase.CriarCartaoCmd.builder()
            .numeroCartao("99999999999999999999")
            .senha("1234")
            .build();

        criarCartao.handle(cmd);

        var cartaoCriado = repository.findByNumeroCartao(cmd.getNumeroCartao()).orElse(null);

        assertNotNull(cartaoCriado);
        assertEquals(cmd.getNumeroCartao(), cartaoCriado.getNumeroCartao());
        assertEquals(cmd.getSenha(), cartaoCriado.getSenha());
        assertEquals(BigDecimal.valueOf(500), cartaoCriado.getSaldo());

        repository.deleteByNumeroCartao("99999999999999999999");
    }

    @Test
    void naoDeveCriarUmCartaoRepitido() {
        var cmd = CriarCartaoUseCase.CriarCartaoCmd.builder()
            .numeroCartao("99999999999999999999")
            .senha("1234")
            .build();

        criarCartao.handle(cmd);

        assertThrows(CartaoJaExisteException.class, ()-> criarCartao.handle(cmd));

        repository.deleteByNumeroCartao("99999999999999999999");
    }

}
