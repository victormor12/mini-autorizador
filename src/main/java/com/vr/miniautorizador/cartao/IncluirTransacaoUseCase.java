package com.vr.miniautorizador.cartao;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;

public interface IncluirTransacaoUseCase {

    void handle(IncluirTransacaoCmd cmd);

    @Value
    @Builder
    class IncluirTransacaoCmd {

        @Schema(required = true)
        @NotEmpty(message = "Campo Obrigatório")
        String numeroCartao;

        @Schema(required = true)
        @NotEmpty(message = "Campo Obrigatório")
        String senhaCartao;

        @Schema(required = true)
        @NotEmpty(message = "Campo Obrigatório")
        BigDecimal valor;

    }
}
