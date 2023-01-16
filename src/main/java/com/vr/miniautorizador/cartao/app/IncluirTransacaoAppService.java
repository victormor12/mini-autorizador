package com.vr.miniautorizador.cartao.app;

import com.vr.miniautorizador.cartao.IncluirTransacaoUseCase;
import com.vr.miniautorizador.cartao.domain.CartaoDomainRepository;
import com.vr.miniautorizador.cartao.exception.TransacaoNegadaCartaoInexistenteException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class IncluirTransacaoAppService implements IncluirTransacaoUseCase {

    private final CartaoDomainRepository repository;

    @Override
    public void handle(IncluirTransacaoCmd cmd) {
        repository.findByNumeroCartao(cmd.getNumeroCartao())
            .ifPresentOrElse(cartao -> {
                cartao.transacionar(cmd.getSenhaCartao(), cmd.getValor());
                repository.save(cartao);
            }, () -> {
                throw new TransacaoNegadaCartaoInexistenteException();
            });
    }
}
