package com.vr.miniautorizador.cartao;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotBlank;

public interface CriarCartaoUseCase {

    String handle(CriarCartaoCmd cmd);

    @Value
    @Builder
    class CriarCartaoCmd {

        @Schema(required = true)
        @NotBlank(message = "Campo Obrigatório")
        String numeroCartao;

        @Schema(required = true)
        @NotBlank(message = "Campo Obrigatório")
        String senha;

    }
}
