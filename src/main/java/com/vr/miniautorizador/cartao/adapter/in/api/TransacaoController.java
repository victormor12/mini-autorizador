package com.vr.miniautorizador.cartao.adapter.in.api;

import com.vr.miniautorizador.cartao.IncluirTransacaoUseCase;
import com.vr.miniautorizador.cartao.IncluirTransacaoUseCase.IncluirTransacaoCmd;
import com.vr.miniautorizador.cartao.adapter.in.api.openapi.TransacaoControllerOpenApi;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@AllArgsConstructor
@RestController
@RequestMapping(path = TransacaoController.PATH)
public class TransacaoController implements TransacaoControllerOpenApi {
    public static final String PATH = "transacoes";

    private final IncluirTransacaoUseCase incluirTransacao;

    @Override
    @PostMapping
    public ResponseEntity<Void> transacionar(IncluirTransacaoCmd cmd) {
        incluirTransacao.handle(cmd);
        var uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/").path(cmd.getNumeroCartao()).build().toUri();
        return ResponseEntity.created(uri).build();
    }
}
