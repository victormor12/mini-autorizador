package com.vr.miniautorizador.cartao.domain;

import com.vr.miniautorizador.cartao.exception.TransacaoNegadaSaldoInsuficienteException;
import com.vr.miniautorizador.cartao.exception.TransacaoNegadaSenhaInvalidaException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)

@Document
public class Cartao {

    @Id
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

    public void transacionar(String senha, BigDecimal valor) {
        validarSenha(senha);
        this.saldo = novoSaldo(valor);
    }

    private void validarSenha(String senha) {
        if (!this.senha.equals(senha))
            throw new TransacaoNegadaSenhaInvalidaException();
    }

    private BigDecimal novoSaldo(BigDecimal valor) {
        var novoSaldo = this.saldo.subtract(valor);

        if (novoSaldo.compareTo(BigDecimal.ZERO) < 0)
            throw new TransacaoNegadaSaldoInsuficienteException();

        return novoSaldo;
    }

}
