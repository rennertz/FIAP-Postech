package br.com.pixpark.parquimetro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/bilhete")
public class ParquimetroController {

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

    @PostMapping("/cadastra")
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

    @GetMapping("/{placa}")
    public ResponseEntity<Bilhete> acharBilhete(@PathVariable String placa){

        Bilhete res = repo.findByPlaca(placa);
        return ResponseEntity.ok(res);
    }

    @PostMapping
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
