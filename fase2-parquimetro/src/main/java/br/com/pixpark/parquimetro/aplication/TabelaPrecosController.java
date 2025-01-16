package br.com.pixpark.parquimetro.aplication;

import br.com.pixpark.parquimetro.domain.model.TabelaPrecos;
import br.com.pixpark.parquimetro.domain.service.TabelaPrecosService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/precos")
@Tag(name="0. Consulta e alteração de valores")
public class TabelaPrecosController {

    private static final String VALORES = """
            {
              "precos": {
                "1": 1.5,
                "2": 2.5,
                "3": 3.5
              }
            }
            """;

    public record Preco(Map<Integer, BigDecimal> precos) {}

    final private TabelaPrecosService precoService;

    @Autowired
    public TabelaPrecosController(TabelaPrecosService precoService){
        this.precoService = precoService;
    }

    @PostMapping()
    @Operation(summary = "Alterar preços", description = "Informe preços por horário para a nova vigência")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, content =
        @Content(mediaType = "application/json", schema =
            @Schema(implementation = Preco.class), examples = {
                @ExampleObject(name = "Exemplo de cadastro de tabela de valores", value = VALORES)
        }))
    public ResponseEntity<TabelaPrecos> alteraValores(@RequestBody Preco req){

        //precoService.validar

        TabelaPrecos tabelaPrecos = precoService.saveTable(new TabelaPrecos(req.precos));
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/vigente")
                .buildAndExpand(tabelaPrecos.getId())
                .toUri();

        return ResponseEntity.created(location).body(tabelaPrecos);
    }

    @GetMapping
    @Operation(summary = "Histórico de precos", description = "Retorna o histórico de valores utilizados no parquímetro.",
            responses = {
        @ApiResponse(responseCode = "200", content =
            @Content(mediaType = "application/json"
//                    examples = @ExampleObject(name = "Exemplo de resposta",value = LISTA_DE_BILHETES_RESPONSE)
            ))})
    public ResponseEntity<List<TabelaPrecos>> historicoDePrecos(){
        return ResponseEntity.ok(precoService.getHistoricoValores());
    }

    @GetMapping("/vigente")
    @Operation(summary = "Tabela de preços vigente", description = "Retorna o a tabela de preços vigente",
            responses = {
        @ApiResponse(responseCode = "200", content =
            @Content(mediaType = "application/json"
//                examples = @ExampleObject(name = "Exemplo de resposta", value = BILHETE_RESPONSE)
            ))})
    public ResponseEntity<TabelaPrecos> getValoresVigentes(){
        return ResponseEntity.ok(precoService.getValoresVigentes());
    }
}
