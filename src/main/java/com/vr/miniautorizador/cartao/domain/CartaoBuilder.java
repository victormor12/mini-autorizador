package com.vr.miniautorizador.cartao.domain;

import com.vr.miniautorizador.cartao.exception.CartaoJaExisteException;

import java.math.BigDecimal;
import java.util.function.Predicate;

public class CartaoBuilder {

    protected String numeroCartao;
    protected Predicate<String> constraintNumeroCartao;
    protected String senha;
    protected BigDecimal saldo;

    public Cartao build() {
        this.saldo = BigDecimal.valueOf(500);

        existeNumeroCartao(constraintNumeroCartao);

        return new Cartao(this);
    }

    public CartaoBuilder numeroCartao(String numeroCartao, Predicate<String> constraintNumeroCartao) {
        this.numeroCartao = numeroCartao;
        this.constraintNumeroCartao = constraintNumeroCartao;
        return this;
    }

    public CartaoBuilder senha(String senha) {
        this.senha = senha;
        return this;
    }

    private void existeNumeroCartao(Predicate<String> constraintNumeroCartao) {
        if (constraintNumeroCartao.test(this.numeroCartao))
            throw new CartaoJaExisteException();
    }

}
