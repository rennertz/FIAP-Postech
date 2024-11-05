package br.com.vetvision.supervisor.interfaces.controller;

import br.com.vetvision.supervisor.application.AtendimentoService;
import br.com.vetvision.supervisor.application.ProvimentoService;
import br.com.vetvision.supervisor.application.ProvimentoService.OfertaConsultorDTO;
import br.com.vetvision.supervisor.domain.model.oferta.OfertaAtendimento;
import br.com.vetvision.supervisor.domain.model.solicitacao.Solicitacao;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/oferta")
@Tag(name = "2. Ofertas para atendimento", description = "Gerenciar ofertas de atendimentos dos consultores")
public class OfertaController {

    static final String OFERTA_NOVA = """
            {"solicitacao": 1,
              "consultor": {
                "cpf":"12345678900",
                "nome": "Dr. Exemplo",
                "endereco": " Rua Exemplo",
                "contaBancaria": "12345-6",
                "contato": "+5561999998888"
              },
              "prazo": "PT3H"}
            """;

    @Autowired
    private ProvimentoService service;

    @PostMapping
    @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, content =
    @Content(mediaType = "application/json", schema =
    @Schema(implementation = ProvimentoService.OfertaConsultorDTO.class), examples = {
            @ExampleObject(name = "Oferta exemplo", value = OFERTA_NOVA),
    }))
    OfertaConsultorDTO aceitaOfertaAtual(@RequestBody OfertaConsultorDTO oferta){
        return service.ofertarAtendimento(oferta);
    }

    @PutMapping("/ofertaAtual/{id}")
    OfertaAtendimento aceitaOfertaAtual(@PathVariable Integer id){
        return service.aceitarOfertaAtual(id);
    }

    @GetMapping("/ofertaAtual/{id}")
    OfertaAtendimento pegarOfertaAtual(@PathVariable Integer id){
        return service.buscarOfertaAtual(id);
    }
}
