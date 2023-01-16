package com.vr.miniautorizador.cartao.adapter.in.api.openapi;

import com.vr.miniautorizador.cartao.IncluirTransacaoUseCase.IncluirTransacaoCmd;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

public interface TransacaoControllerOpenApi {

    @PostMapping
    @Operation(description = "Realizar uma Transação")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Transação realizada com sucesso."),
        @ApiResponse(responseCode = "422", description = "Transação negada - Saldo Insuficiente."),
        @ApiResponse(responseCode = "422", description = "Transação negada - Senha inválida."),
        @ApiResponse(responseCode = "422", description = "Transação negada - Cartão inexistente."),
    })
    ResponseEntity<Void> transacionar(@RequestBody @Valid IncluirTransacaoCmd cmd);

}
