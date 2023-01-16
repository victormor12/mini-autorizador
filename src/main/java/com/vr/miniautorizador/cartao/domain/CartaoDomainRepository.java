package com.vr.miniautorizador.cartao.domain;

import java.util.Optional;

public interface CartaoDomainRepository {

    Cartao save(Cartao cartao);

    Optional<Cartao> findByNumeroCartao(String numeroCartao);

    boolean existsByNumeroCartao(String numeroCartao);

    void deleteByNumeroCartao(String numeroCartao);

}
