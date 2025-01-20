package br.com.pixpark.parquimetro.application;

import br.com.pixpark.parquimetro.domain.model.TabelaPrecos;
import br.com.pixpark.parquimetro.domain.service.TabelaPrecosService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v2/bilhete")
@Tag(name="Parquimetro urbano v2")
public class ParquimetroV2Controller {

    private final TabelaPrecosService precosService;

    @Autowired
    public ParquimetroV2Controller(TabelaPrecosService precoService){
        this.precosService = precoService;
    }

    @GetMapping("/precos")
    @Operation(
            summary = "Tabela de precos",
            description = "Retorna a tabela de precos usado no parquimetro"
    )
    public ResponseEntity<TabelaPrecos> mostraTabelaPrecos(){
        return ResponseEntity.ok(precosService.getValoresVigentesCached());
    }
}
