package br.com.vetvision.supervisor.interfaces.controller;

import br.com.vetvision.supervisor.application.AdminService;
import br.com.vetvision.supervisor.application.AdminService.PlanoVeterinarioDTO;
import br.com.vetvision.supervisor.domain.model.plano.PlanoVeterinario;
import br.com.vetvision.supervisor.domain.model.plano.TipoExame;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/admin")
@Tag(name = "0. Administrativo", description = "Operacoes administrativas do sistema")
public class AdminController {

    private static final String PLANO_EXEMPLOS = """
        {
        "cnpj": "00.000.000/0002-02",
        "nome": "Plano mock",
        "examesCobertos": [
            {
              "nome": "Ultrassom Gato",
              "valor": 49.99,
              "comissao": 0.15
            },
            {
            "nome": "Ultrassom Cachorro",
            "valor": 89.99,
            "comissao": 0.15
            }
        ]
        }
        """;


    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/planos")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, content =
        @Content(mediaType = "application/json", schema =
            @Schema(implementation = PlanoVeterinarioDTO.class), examples = {
                    @ExampleObject(name = "Planos de exemplo", value = PLANO_EXEMPLOS),
            }))
    public PlanoVeterinario adicionaPlano(@RequestBody PlanoVeterinarioDTO body) {
        return adminService.adicionaPlano(body);
    }

    @GetMapping("/planos")
    public List<AdminService.PlanoVeterinarioExamesSimplesDTO> listaPlanos() {
        return adminService.listaPlanos();
    }

    @GetMapping("/exames")
    public Set<TipoExame> listaTipoExames() {
        return adminService.listaTipoExames();
    }
}