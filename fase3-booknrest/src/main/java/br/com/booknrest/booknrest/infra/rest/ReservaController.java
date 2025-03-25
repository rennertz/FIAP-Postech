package br.com.booknrest.booknrest.infra.rest;

import br.com.booknrest.booknrest.application.CriarReservaUseCase;
import br.com.booknrest.booknrest.entities.Cliente;
import br.com.booknrest.booknrest.entities.Reserva;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/v1/reservas")
@Tag(name="1. Criacao e busca de reservas")
public class ReservaController {

    static final String RESERVA = """
    {
      "restauranteId": 1,
      "cliente": {
        "nome": "Sting",
        "telefone": "10 99999-9999"
      },
      "dataHora": "2027-03-15 20:00",
      "quantidadePessoas": 10
    }
    """;

    final CriarReservaUseCase criarReservaUseCase;

    public ReservaController(CriarReservaUseCase criarReservaUseCase) {
        this.criarReservaUseCase = criarReservaUseCase;
    }

    @PostMapping()
    @Operation(summary = "Nova reserva", description = "Informe os dados da nova reserva")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, content =
        @Content(mediaType = "application/json", schema =
            @Schema(implementation = ReservaDTO.class), examples = {
                    @ExampleObject(name = "Exemplo de criacao de nova reserva", value = RESERVA)
            }))
    public ResponseEntity<ReservaDTO> novaReserva(@Valid @RequestBody ReservaDTO req){
        Cliente cliente = ReservaDTO.toModel(req.cliente());
        Reserva reserva = criarReservaUseCase.criarReserva(req.restauranteId(), cliente, req.dataHora(), req.quantidadePessoas());
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .buildAndExpand(reserva.getId())
                .toUri();

        return ResponseEntity.created(location)
                .body(ReservaDTO.toDTO(reserva));
    }

    @GetMapping()
    @Operation(summary = "Todas as reservas", description = "Obtenha todas as reservas")
    public ResponseEntity<List<ReservaDTO>> todasAsReservas(){
        // TODO implementar paginação
        List<ReservaDTO> reservas = criarReservaUseCase.obtemTodasAsReservas().stream()
                .map(ReservaDTO::toDTO)
                .toList();
        return ResponseEntity.ok(reservas);
    }
}
