package br.com.pixpark.parquimetro.aplication;

import br.com.pixpark.parquimetro.domain.model.Bilhete;
import br.com.pixpark.parquimetro.domain.model.OrdemPagamento;
import br.com.pixpark.parquimetro.domain.service.BilheteService;
import br.com.pixpark.parquimetro.infrastructure.BilheteRepository;
import br.com.pixpark.parquimetro.infrastructure.OrdemPagamentoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.aggregation.BooleanOperators;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/estacionamento")
@Tag(name="Estacionamento pago")
public class EstacionamentoController {

    private BilheteRepository repo;
    private BilheteService service;
    private OrdemPagamentoRepository ordemPagamentoRepository;


    @Autowired
    public EstacionamentoController(BilheteRepository repo, BilheteService service, OrdemPagamentoRepository ordemPagamentoRepository){
        this.repo = repo;
        this.service = service;
        this.ordemPagamentoRepository = ordemPagamentoRepository;
    }


    @PostMapping
    @Operation(
            summary = "registrar entrada de veiculo",
            description = "gera um bilhete para todo carro que entra no estacionamento"
    )
    public ResponseEntity<Bilhete> registraEntradaVeiculo(DTOregistraVeiculo dadosEntrada){
        Bilhete bilhete = new Bilhete();
        bilhete.setPlaca(dadosEntrada.placa);
        bilhete.setMomentoDaSolicitacao(dadosEntrada.momentoEntrada());
        bilhete = repo.save(bilhete);
        return ResponseEntity.ok(bilhete);
    }


    @GetMapping
    @Operation(
            summary = "verificar veiculo a ser liberado",
            description = "verifica se bilhete foi pago pra liberar o veiculo"
    )
    public ResponseEntity verificaSaidaVeiculo(String id){

        Bilhete bilhete;
        var verifica = repo.findById(id);
        if (!verifica.isPresent()){
            return ResponseEntity.badRequest().body("bilhete não cadastrado no sistema");
        }
        bilhete = verifica.get();

        //verifica se entrou entre 10 mim
        var momentoEntrado = bilhete.getMomentoDaSolicitacao();
        if(Duration.between(momentoEntrado, LocalDateTime.now()).toMinutes() < 10){
            return ResponseEntity.ok().build();
        }

        //verifica se ta pago
        if(!bilhete.getPago()){
            return ResponseEntity.badRequest().body("bilhete não foi pago");
        }

        //verifica se foi pago entre 10mim
        var momentoPago = bilhete.getOrdemPagamento().getMomentoCriado();
        if(Duration.between(momentoPago, LocalDateTime.now()).toMinutes() > 10){
            return ResponseEntity.badRequest().body("Tempo para sair do estacionamento excedido, volte pague");
        }else{
            return ResponseEntity.ok().build();
        }
    }



    @PutMapping
    @Operation(
            summary = "liberar veiculo",
            description = "liberacao do veiculo pelos fiscalizadores"
    )
    public ResponseEntity liberarVeiculo(String placa){
        var verifica = repo.findAllByPlaca(placa);
        Bilhete bilhete;
        if (verifica.isEmpty()){
            bilhete = new Bilhete();
            bilhete.setPlaca(placa);
            bilhete.setOrdemPagamento(ordemPagamentoRepository.save(new OrdemPagamento()));
            bilhete.getOrdemPagamento().setValor(0.0);
            bilhete.getOrdemPagamento().setMomentoPago(LocalDateTime.now());
            bilhete.setPago(true);
            bilhete = repo.save(bilhete);
            return ResponseEntity.ok("veiculo liberado");
        } else {
            bilhete = verifica.getLast();
            bilhete.setOrdemPagamento(ordemPagamentoRepository.save(new OrdemPagamento()));
            bilhete.getOrdemPagamento().setValor(0.0);
            bilhete.getOrdemPagamento().setMomentoPago(LocalDateTime.now());
            bilhete.setPago(true);
            bilhete = repo.save(bilhete);
            return ResponseEntity.ok("veiculo liberado");
        }

    }



    //dtos usado no controller
    private record DTOregistraVeiculo(String placa, LocalDateTime momentoEntrada) {};
}

