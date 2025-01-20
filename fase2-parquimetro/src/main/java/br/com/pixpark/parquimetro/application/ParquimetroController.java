package br.com.pixpark.parquimetro.application;

import br.com.pixpark.parquimetro.application.dtos.DTOGerarBilheteRequest;
import br.com.pixpark.parquimetro.domain.model.Bilhete;
import br.com.pixpark.parquimetro.domain.model.TabelaPrecos;
import br.com.pixpark.parquimetro.domain.service.BilheteService;
import br.com.pixpark.parquimetro.domain.service.TabelaPrecosService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;

@RestController
@RequestMapping("/v1/bilhete")
@Tag(name="Parquimetro urbano")
public class ParquimetroController {

    private final BilheteService bilheteService;
    private final TabelaPrecosService precosService;

    @Autowired
    public ParquimetroController(BilheteService bilheteService, TabelaPrecosService precoService){
        this.bilheteService = bilheteService;
        this.precosService = precoService;
    }

    @GetMapping("/precos")
    @Operation(
            summary = "Tabela de precos",
            description = "Retorna a tabela de precos usado no parquimetro"
    )
    public ResponseEntity<TabelaPrecos> mostraTabelaPrecos(){
        return ResponseEntity.ok(precosService.getValoresVigentes());
    }

    @PostMapping
    @Operation(
            summary = "Gerar Bilhete",
            description = "Cadastra uma placa e gera um bilhete do parquimetro"
    )
    public ResponseEntity<Bilhete> gerarBilhete(@RequestBody DTOGerarBilheteRequest req){
        Duration tempo = Duration.ofHours(req.tempoEmHoras());
        return ResponseEntity.ok(bilheteService.getNovoBilhete(req.placa(), tempo));
    }

    @GetMapping("/{placa}")
    @Operation(
            summary = "Verificar Carro",
            description = "Verifica o carro, e mostra seu bilhete"
    )
    public ResponseEntity<Bilhete> verificarCarro(@PathVariable String placa){
        var bilhete = bilheteService.getBilheteBy(placa)
            .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND ,"Placa não cadastrada!"));

        return ResponseEntity.ok(bilhete);
    }
}
