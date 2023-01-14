package com.vr.miniautorizador.cartao.adapter.in.api.openapi;

import com.vr.miniautorizador.cartao.CriarCartaoUseCase.CriarCartaoCmd;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.math.BigDecimal;

public interface CartaoControllerOpenApi {

    @PostMapping
    @Operation(description = "Criar novo cartão")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Cartão criado com sucesso."),
        @ApiResponse(responseCode = "422", description = "Cartão já existe.")
    })
    ResponseEntity<Void> criar(@Valid @RequestBody CriarCartaoCmd cmd);

    @GetMapping("/{numeroCartao}")
    @Operation(description = "Obter saldo do Cartão")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Saldo do cartão consultado com sucesso."),
        @ApiResponse(responseCode = "404", description = "Cartão não existe.")
    })
    BigDecimal obterSaldo(@Schema(description = "Número do cartão") @PathVariable String numeroCartao);

}
