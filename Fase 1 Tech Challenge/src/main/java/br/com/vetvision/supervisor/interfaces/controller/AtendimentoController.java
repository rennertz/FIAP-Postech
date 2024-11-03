package br.com.vetvision.supervisor.interfaces.controller;

import br.com.vetvision.supervisor.application.AtendimentoService;
import br.com.vetvision.supervisor.application.AtendimentoService.SolicitacaoDTO;
import br.com.vetvision.supervisor.domain.model.solicitacao.Solicitacao;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/solicitacao")
@Tag(name = "1. Atendimento", description = "Atendimento na visao da Clinica")
public class AtendimentoController {

    static final String SOLICITACAO_EXEMPLO = """
            {
              "clinica": {
                "cnpj": "00.000.000/0001-01",
                "nome": "Clinica Mock",
                "endereco": "Rua Mock, No 0",
                "contato": "01 00000-0001"
              },
              "pet": {
                "nome": "MiaMock",
                "especie": "Gato",
                "dataNascimento": "2024-01-01",
                "nomeResponsavel": "Responsavel Mock",
                "cpfResponsavel": "000.000.001-01"
              },
              "tipoExame": "Ultrassom Gato",
              "cnpjPlano": "00.000.000/0000-01"
            }
            """;


    private final AtendimentoService atendimentoService;

    @Autowired
    public AtendimentoController(AtendimentoService atendimentoService) {
        this.atendimentoService = atendimentoService;
    }

    @PostMapping
    @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, content =
        @Content(mediaType = "application/json", schema =
        @Schema(implementation = SolicitacaoDTO.class), examples = {
                @ExampleObject(name = "Solicitacao exemplo", value = SOLICITACAO_EXEMPLO),
        }))
    public Solicitacao criaSolicitacao(@RequestBody SolicitacaoDTO body) {
        return atendimentoService.solicitarExame(body);
    }

    @GetMapping("/{id}")
    public Solicitacao getSolicitacao(@PathVariable Integer id) {
        return atendimentoService.consultarSolicitacao(id);
    }

    @GetMapping
    public List<Solicitacao> listaSolicitacoes(@RequestParam String clinicaCnpj) {
        return atendimentoService.consultarSolicitacoes(clinicaCnpj);
    }
}