package com.vr.miniautorizador.cartao.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)

@Document
public class Cartao {

    String numeroCartao;
    String senha;
    BigDecimal saldo;

    public static CartaoBuilder builder() {
        return new CartaoBuilder();
    }

    public Cartao(CartaoBuilder builder) {
        this.numeroCartao = builder.numeroCartao;
        this.senha = builder.senha;
        this.saldo = builder.saldo;
    }

}
