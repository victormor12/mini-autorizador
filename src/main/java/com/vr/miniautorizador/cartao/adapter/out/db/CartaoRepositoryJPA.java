package com.vr.miniautorizador.cartao.adapter.out.db;

import com.vr.miniautorizador.cartao.domain.Cartao;
import com.vr.miniautorizador.cartao.domain.CartaoDomainRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CartaoRepositoryJPA extends CartaoDomainRepository, MongoRepository<Cartao, String> {

}
