package br.com.vetvision.supervisor.interfaces.controller;

import br.com.vetvision.supervisor.application.ProvimentoService;
import br.com.vetvision.supervisor.application.impl.ProvimentoServiceImpl;
import br.com.vetvision.supervisor.domain.model.exame.Exame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/exame")
@RestController
public class ExameController {

    @Autowired
    private ProvimentoService provimentoService;

    @PutMapping("/realizarExame/{codigo}")
    Exame realizarExame(@PathVariable String codigo){
        System.out.println( "exame realizado");
        return provimentoService.realizarExame(codigo);
    }



}
