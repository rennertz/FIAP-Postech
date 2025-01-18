package br.com.pixpark.parquimetro.aplication;

import br.com.pixpark.parquimetro.domain.model.Bilhete;
import br.com.pixpark.parquimetro.domain.model.TabelaPrecos;
import br.com.pixpark.parquimetro.domain.service.BilheteService;
import br.com.pixpark.parquimetro.infrastructure.BilheteRepository;
import br.com.pixpark.parquimetro.infrastructure.TabelaPrecoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/estacionamento")
@Tag(name="Estacionamento pago")
public class ParquimetroController {

    private BilheteRepository repo;
    private TabelaPrecoRepository tabelaPrecoRepository;



    @Autowired
    public ParquimetroController(BilheteRepository repo, TabelaPrecoRepository tabelaPrecoRepository){
        this.repo = repo;
        this.tabelaPrecoRepository = tabelaPrecoRepository;
    }


    @GetMapping
    @Operation(
            summary = "Tabela de precos",
            description = "Retorna a tabela de precos usado no parquimetro"
    )
    public ResponseEntity<TabelaPrecos> mostraTabelaPrecos(){

        TabelaPrecos tabela = tabelaPrecoRepository.findAll().getLast();

        return ResponseEntity.ok(tabela);

    }


    @PostMapping
    @Operation(
            summary = "Gerar Bilhete",
            description = "Cadastra uma placa e gera um bilhete do parquimetro"
    )
    public ResponseEntity gerarBilhete(@RequestBody DTOgerarBilheteRequest req){
        Bilhete bilhete = new Bilhete();
        bilhete.setPlaca(req.placa());
        bilhete.setTempo(req.tempo());
        bilhete = repo.save(bilhete);
        return ResponseEntity.ok(bilhete);
    }



    @GetMapping
    @Operation(
            summary = "Verificar Carro",
            description = "Verifica o carro, e mostra seu bilhete"
    )
    public ResponseEntity verificarCarro(@PathVariable String placa){
        var lista = repo.findAllByPlaca(placa);
        if(lista.isEmpty()){
           return ResponseEntity.ok("Placa não cadastrada!");
        }
        var bilhete = lista.getLast();
        return ResponseEntity.ok(bilhete);
    }



    //dtos usado no controller
    private record DTOgerarBilheteRequest(@NotBlank String placa,@NotBlank Duration tempo) {};
}


