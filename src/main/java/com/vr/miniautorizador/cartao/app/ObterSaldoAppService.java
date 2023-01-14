package com.vr.miniautorizador.cartao.app;

import com.vr.miniautorizador.cartao.ObterSaldoUseCase;
import com.vr.miniautorizador.cartao.domain.CartaoDomainRepository;
import com.vr.miniautorizador.cartao.exception.CartaoNaoEncontratoException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Transactional
@Service
public class ObterSaldoAppService implements ObterSaldoUseCase {

    private final CartaoDomainRepository repository;

    @Override
    public BigDecimal handle(ObterSaldoCmd cmd) {
        return repository.findByNumeroCartao(cmd.getNumeroCartao())
            .orElseThrow(CartaoNaoEncontratoException::new).getSaldo();
    }
}
