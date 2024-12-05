package br.com.pixpark.parquimetro;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/cadastro")
public class ParquimetroController {

    private static final Set<String> pagamentos =  Set.of("pix", "credito", "debito");

    private record InfoPagamento(Double valor, Set<String> meioDePagamento) {};

    @PostMapping
    public ResponseEntity<InfoPagamento> cadastraVeiculo(@RequestBody Bilhete req){
        InfoPagamento res = new InfoPagamento(req.pegarValor(),pagamentos );
        return ResponseEntity.ok(res);
    }

}
