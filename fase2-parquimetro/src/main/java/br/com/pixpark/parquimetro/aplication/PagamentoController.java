package br.com.pixpark.parquimetro.aplication;

import br.com.pixpark.parquimetro.domain.model.Bilhete;
import br.com.pixpark.parquimetro.domain.model.OrdemPagamento;
import br.com.pixpark.parquimetro.domain.model.TabelaPrecos;
import br.com.pixpark.parquimetro.domain.service.BilheteService;
import br.com.pixpark.parquimetro.infrastructure.BilheteRepository;
import br.com.pixpark.parquimetro.infrastructure.EstacionamentoInterface;
import br.com.pixpark.parquimetro.infrastructure.OrdemPagamentoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/pagamento")
@Tag(name="pagamentos")
public class PagamentoController {

    private BilheteRepository repo;
    private BilheteService service;
    private EstacionamentoInterface pagamento;
    private OrdemPagamentoRepository ordemPagamentoRepository;


    @Autowired
    public PagamentoController(BilheteRepository repo, BilheteService service,OrdemPagamentoRepository ordemPagamentoRepository){
        this.repo = repo;
        this.service = service;
        this.ordemPagamentoRepository = ordemPagamentoRepository;
        this.pagamento = new EstacionamentoInterface() {};
    }

    @GetMapping
    @Operation(
            summary = "iniciar pagamento do bilhete",
            description = "pega o id do bilhete, e envia o pagamento para ser realizado"
    )
    public ResponseEntity inicarPagamento(String id){
        long minutosEstacionado = 0;
        Double valorCobrado= 0.;

        //pegar bilhete no bd
        Bilhete bilhete;
        var verifica = repo.findById(id);
        if (!verifica.isPresent()){
            return ResponseEntity.badRequest().body("bilhete não cadastrado no sistema");
        }
        bilhete = verifica.get();

        //bilhete ja foi pago alguma vez
        if(bilhete.getPago()){
            var momentoPago = bilhete.getOrdemPagamento().getMomentoPago();
            var momentoAtual = LocalDateTime.now();
            var diferenca = Duration.between(momentoPago, momentoAtual).toMinutes();
            if(diferenca> 10){
                valorCobrado = TabelaPrecos.getPreco(diferenca);
                minutosEstacionado = diferenca;
                bilhete.getOrdemPagamento().setValor(valorCobrado);
            }
        }else {

            minutosEstacionado = service.tempoEstacionado(bilhete);
            valorCobrado = TabelaPrecos.getPreco(minutosEstacionado);

            var ordemPagamento = ordemPagamentoRepository.save(new OrdemPagamento());
            ordemPagamento.setValor(valorCobrado);
            bilhete.setOrdemPagamento(ordemPagamento);
        }
        bilhete = repo.save(bilhete);

        var dadosPagamento = new DTOdadosPagamento(bilhete.getOrdemPagamento().id, Duration.ofMinutes(minutosEstacionado), valorCobrado, bilhete.getMomentoDaSolicitacao());
        return ResponseEntity.ok(dadosPagamento);

    }

    @PutMapping
    @Operation(
            summary = "pagamento do bilhete",
            description = "pega o id do bilhete, e envia o pagamento para ser realizado"
    )
    public ResponseEntity pagarBilhete(String id){
        Bilhete bilhete;
        var verifica = repo.findById(id);
        if (!verifica.isPresent()){
            return ResponseEntity.badRequest().body("bilhete não cadastrado no sistema");
        }
        bilhete = verifica.get();

        var pagou = pagamento.validarPagamento(bilhete);
        if(pagou){
            bilhete.setPago(true);
            bilhete.getOrdemPagamento().setMomentoPago(LocalDateTime.now());
            repo.save(bilhete);
            return ResponseEntity.ok(new DTOpagamentoRealizado(bilhete.getId(), bilhete.getOrdemPagamento().getId(), bilhete.getOrdemPagamento().getValor(), true));
        }else{
            return ResponseEntity.badRequest().body("pagamento não realizado");
        }
    }


    private record DTOdadosPagamento(String idPagamento, Duration tempoEstacionado, Double precoCobrado, LocalDateTime momentoEntrada){}
    private record DTOpagamentoRealizado(String idBilhete, String idPagamento, Double valorPago, Boolean pagamentoRealizado){};
}
