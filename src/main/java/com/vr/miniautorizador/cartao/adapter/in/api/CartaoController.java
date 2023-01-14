package com.vr.miniautorizador.cartao.adapter.in.api;

import com.vr.miniautorizador.cartao.CriarCartaoUseCase;
import com.vr.miniautorizador.cartao.CriarCartaoUseCase.CriarCartaoCmd;
import com.vr.miniautorizador.cartao.ObterSaldoUseCase;
import com.vr.miniautorizador.cartao.ObterSaldoUseCase.ObterSaldoCmd;
import com.vr.miniautorizador.cartao.adapter.in.api.openapi.CartaoControllerOpenApi;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.math.BigDecimal;

@AllArgsConstructor
@RestController
@RequestMapping(path = CartaoController.PATH)
public class CartaoController implements CartaoControllerOpenApi {
    public static final String PATH = "cartoes";

    private final CriarCartaoUseCase criarCartao;
    private final ObterSaldoUseCase obterSaldo;

    @Override
    @PostMapping
    public ResponseEntity<Void> criar(@Valid @RequestBody CriarCartaoCmd cmd) {
        var numeroCartao = criarCartao.handle(cmd);
        var uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/").path(numeroCartao).build().toUri();
        return ResponseEntity.created(uri).build();
    }

    @Override
    @GetMapping("/{numeroCartao}")
    public BigDecimal obterSaldo(@PathVariable String numeroCartao) {
        return obterSaldo.handle(ObterSaldoCmd.builder()
            .numeroCartao(numeroCartao)
            .build());
    }
}
