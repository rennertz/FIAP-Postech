package br.com.pixpark.parquimetro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/cadastro")
public class ParquimetroController {

    private static final Set<String> pagamentos =  Set.of("pix", "credito", "debito");

    private record InfoPagamento(Double valor, Set<String> meioDePagamento) {};

    private BilheteRepository repo;

    @Autowired
    public ParquimetroController(BilheteRepository repo){
        this.repo = repo;
    }

    @PostMapping
    public ResponseEntity<InfoPagamento> cadastraVeiculo(@RequestBody Bilhete req){

        //service.validar

        repo.save(req);

        InfoPagamento res = new InfoPagamento(req.pegarValor(),pagamentos );
        return ResponseEntity.ok(res);
    }

    @GetMapping
    public ResponseEntity<List<Bilhete>> listarBilhetes(){

        return ResponseEntity.ok(repo.findAll());
    }

}
