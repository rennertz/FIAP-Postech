package br.com.booknrest.booknrest.controllers;

import br.com.booknrest.booknrest.entities.Restaurante;
import br.com.booknrest.booknrest.application.CadastroDeRestauranteUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/v1/restaurantes")
@Tag(name="0. Cadastro e busca de restaurantes")
public class RestauranteController {

    private static final String RESTAURANTE = """
        {
          "nome": "Restaurante Magnífico",
          "localizacao": "Rua dos bobos, 0",
          "tipoCozinha": "francesa",
          "horarioDeFuncionamento": [
            {
              "diaDaSemana": "MONDAY",
              "horaAbertura": "18:00",
              "horaFechamento": "23:00"
            }
          ],
          "capacidade": 120
        }
    """;

    final CadastroDeRestauranteUseCase cadastroDeRestauranteUseCase;

    public RestauranteController(CadastroDeRestauranteUseCase cadastroDeRestauranteUseCase) {
        this.cadastroDeRestauranteUseCase = cadastroDeRestauranteUseCase;
    }

    @PostMapping()
    @Operation(summary = "Novo restaurante", description = "Informe os dados do novo restaurante")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, content =
    @Content(mediaType = "application/json", schema =
    @Schema(implementation = Restaurante.class), examples = {
            @ExampleObject(name = "Exemplo de cadastro de novo restaurante", value = RESTAURANTE)
    }))
    public ResponseEntity<Restaurante> novoRestaurante(@RequestBody Restaurante req){

        //precoService.validar

        Restaurante restaurante = cadastroDeRestauranteUseCase.salvaRestaurante(req);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .buildAndExpand(restaurante.getId())
                .toUri();

        return ResponseEntity.created(location).body(restaurante);
    }

    @GetMapping()
    @Operation(summary = "Todos os restaurantes", description = "Obtenha todos os restaurantes")
    public ResponseEntity<List<Restaurante>> todosOsRestaurantes(){

        //precoService.validar

        List<Restaurante> restaurantes = cadastroDeRestauranteUseCase.obtemRestaurantes();

        return ResponseEntity.ok(restaurantes);
    }
}
