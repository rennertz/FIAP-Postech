package br.com.pixpark.parquimetro;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/bilhete")
@Tag(name="0. Compra de Bilhete")
public class ParquimetroController {

    private static final String CADASTRO_EXEMPLO = """
            {
              "placa": "ABC-4321",
              "tempo": "PT2H"
            }
        """;

    private static final Set<String> pagamentos =  Set.of("pix", "credito", "debito");

    private record InfoPagamento(Double valor, Set<String> meioDePagamento) {};

    private record ReciboPagamento(String bilhete, Boolean recibo) {public ReciboPagamento {
            recibo = true;
    }};

    private BilheteRepository repo;

    @Autowired
    public ParquimetroController(BilheteRepository repo){
        this.repo = repo;
    }

    @PostMapping()
    @Operation(
            summary = "Cadastrar um Bilhete",
            description = "Gera um Bilhete de acordo com a placa e tempo passado")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Bilhete.class),
                    examples = {
                            @ExampleObject(
                                    name = "Exemplo de cadastro de um bilhete",
                                    value = CADASTRO_EXEMPLO)}))
    public ResponseEntity<InfoPagamento> cadastraVeiculo(@RequestBody Bilhete req){

        //service.validar

        repo.save(req);
        var valor = req.pegarValor();
        InfoPagamento res = new InfoPagamento(valor,pagamentos );
        return ResponseEntity.ok(res);
    }

    @GetMapping
    @Operation(
            summary = "Todos os bilhetes",
            description = "Retorna uma lista com todos os bilhetes disponíveis no sistema.",
        responses = {
        @ApiResponse(
                responseCode = "200",
                content = @Content(
                        mediaType = "application/json",
                        examples = @ExampleObject(
                                name = "Exemplo de resposta",
                                value = """
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
                    """
                        )
                )
        )})
    public ResponseEntity<List<Bilhete>> listarBilhetes(){

        return ResponseEntity.ok(repo.findAll());
    }

    @GetMapping("/{placa}")
    @Operation(
            summary = "Buscar bilhete",
            description = "Retorna o Bilhete do carro referente a palca informada",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Exemplo de resposta",
                                            value = """
                                            {
                                              "id": "123",
                                              "placa": "x",
                                              "tempo": "PT0H",
                                              "momentoDaSolicitacao": "yyyy-MM-dd",
                                              "pago": false
                                            }
                                            """)))})
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

    @Operation(
            summary = "Pagar bilhete",
            description = "Recebe o id do bilhete que deseja pagar e o recibo do pagamento")
    @PutMapping
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
