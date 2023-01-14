package com.vr.miniautorizador.cartao.app;

import com.vr.miniautorizador.cartao.CriarCartaoUseCase;
import com.vr.miniautorizador.cartao.domain.Cartao;
import com.vr.miniautorizador.cartao.domain.CartaoDomainRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class CriarCartaoAppService implements CriarCartaoUseCase {

    private final CartaoDomainRepository repository;

    @Override
    public String handle(CriarCartaoCmd cmd) {
        var cartao = Cartao.builder()
            .numeroCartao(cmd.getNumeroCartao(), repository::existsByNumeroCartao)
            .senha(cmd.getSenha())
            .build();

        var cartaoCriado = repository.save(cartao);

        return cartaoCriado.getNumeroCartao();
    }

}
