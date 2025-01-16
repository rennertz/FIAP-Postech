package br.com.pixpark.parquimetro.aplication;

import br.com.pixpark.parquimetro.domain.model.Bilhete;
import br.com.pixpark.parquimetro.infrastructure.BilheteRepository;
import br.com.pixpark.parquimetro.domain.service.TabelaPrecosService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/bilhete")
@Tag(name="1. Compra de Bilhete")
public class ParquimetroController {

    private static final Set<String> PAGAMENTOS =  Set.of("pix", "credito", "debito");
    private static final String CADASTRO_REQUEST = """
            {
              "placa": "ABC-4321",
              "tempo": "PT2H"
            }
        """;
    public static final String LISTA_DE_BILHETES_RESPONSE = """
            [
              {
                "id": "x",
                "placa": "z",
                "tempo": "PT00H",
                "momentoDaSolicitacao": "yyyy-MM-dd",
                "pago": false
              },
              {
                "id": "x",
                "placa": "z",
                "tempo": "PT00H",
                "momentoDaSolicitacao": "yyyy-MM-dd",
                "pago": true
              }
            ]
        """;
    public static final String BILHETE_RESPONSE = """
            {
              "id": "123",
              "placa": "x",
              "tempo": "PT0H",
              "momentoDaSolicitacao": "yyyy-MM-dd",
              "pago": false
            }
        """;

    public record InfoPagamento(BigDecimal valor, Set<String> meioDePagamento) {}

    public record ReciboPagamento(String bilhete, Boolean recibo) {
        public ReciboPagamento {
            recibo = true;
        }
    }

    final private BilheteRepository repo;
    final private TabelaPrecosService precoService;

    @Autowired
    public ParquimetroController(BilheteRepository repo, TabelaPrecosService precoService){
        this.repo = repo;
        this.precoService = precoService;
    }

    @PostMapping()
    @Operation(summary = "Cadastrar um Bilhete", description = "Gera um Bilhete de acordo com a placa e tempo passado")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, content =
        @Content(mediaType = "application/json", schema =
            @Schema(implementation = Bilhete.class), examples = {
                @ExampleObject(name = "Exemplo de cadastro de um bilhete", value = CADASTRO_REQUEST)}))
    public ResponseEntity<InfoPagamento> cadastraVeiculo(@RequestBody Bilhete req){

        //service.validar

        repo.save(req);
        var valor = precoService.pegarValor(req.getTempo());
        InfoPagamento res = new InfoPagamento(valor, PAGAMENTOS);
        return ResponseEntity.ok(res);
    }

    @GetMapping
    @Operation(summary = "Todos os bilhetes", description = "Retorna uma lista com todos os bilhetes disponíveis no sistema.",
            responses = {
        @ApiResponse(responseCode = "200", content =
            @Content(mediaType = "application/json", examples =
                @ExampleObject(name = "Exemplo de resposta",value = LISTA_DE_BILHETES_RESPONSE)))})
    public ResponseEntity<List<Bilhete>> listarBilhetes(){

        return ResponseEntity.ok(repo.findAll());
    }

    @GetMapping("/{placa}")
    @Operation(summary = "Buscar bilhete", description = "Retorna o Bilhete do carro referente a palca informada",
            responses = {
        @ApiResponse(responseCode = "200", content =
            @Content(mediaType = "application/json", examples =
                @ExampleObject(name = "Exemplo de resposta", value = BILHETE_RESPONSE)))})
    public ResponseEntity<Bilhete> acharBilhete(@PathVariable String placa){

        try{
            Bilhete res = repo.findByPlaca(placa);
            return ResponseEntity.ok(res);
        }catch (IncorrectResultSizeDataAccessException e){
            var todos = repo.findAllByPlaca(placa);
            Bilhete res = todos.getFirst();
            return ResponseEntity.ok(res);
        }

    }

    @PutMapping
    @Operation(summary = "Pagar bilhete", description = "Recebe o id do bilhete que deseja pagar e o recibo do pagamento")
    public ResponseEntity pagarBilhete(@RequestBody ReciboPagamento req){

        var bilhete = repo.findById(req.bilhete).orElseGet(null);

        if(req.recibo()){
            bilhete.setPago(true);
            repo.save(bilhete);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

}
