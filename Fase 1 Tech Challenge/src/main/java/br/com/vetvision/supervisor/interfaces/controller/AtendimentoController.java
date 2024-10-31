package br.com.vetvision.supervisor.interfaces.controller;

import br.com.vetvision.supervisor.application.AtendimentoService;
import br.com.vetvision.supervisor.domain.model.plano.PlanoVeterinario;
import br.com.vetvision.supervisor.domain.model.solicitacao.Clinica;
import br.com.vetvision.supervisor.domain.model.solicitacao.Pet;
import br.com.vetvision.supervisor.domain.model.solicitacao.Solicitacao;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/solicitacao")
@Tag(name = "1. Atendimento", description = "Atendimento na visao da Clinica")
public class AtendimentoController {

    private static final String SOLICITACAO_EXEMPLO = """
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
              "plano": {
                "cnpj": "00.000.000/0002-02",
                "nome": "Plano mock",
                "examesCobertos": [
                  {}
                ]
              }
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
        Solicitacao solicitacao = new Solicitacao(body.clinica, body.pet, body.plano);
        return atendimentoService.solicitarExame(solicitacao);
    }

    public record SolicitacaoDTO(Clinica clinica, Pet pet, PlanoVeterinario plano) {}
}