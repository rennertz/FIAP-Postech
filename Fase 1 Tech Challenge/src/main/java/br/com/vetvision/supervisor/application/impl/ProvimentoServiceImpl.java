package br.com.vetvision.supervisor.application.impl;

import br.com.vetvision.supervisor.application.ProvimentoService;
import br.com.vetvision.supervisor.application.exceptions.ExcecaoDeSistema;
import br.com.vetvision.supervisor.domain.model.exame.Exame;
import br.com.vetvision.supervisor.domain.model.exame.ExameRepository;
import br.com.vetvision.supervisor.domain.model.oferta.OfertaAtendimento;
import br.com.vetvision.supervisor.domain.model.solicitacao.Solicitacao;
import br.com.vetvision.supervisor.domain.model.solicitacao.SolicitacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProvimentoServiceImpl implements ProvimentoService {

    @Autowired
    private SolicitacaoRepository solicitacaoRepository;

    @Autowired
    private ExameRepository exameRepository;

    @Override
    public OfertaConsultorDTO ofertarAtendimento(OfertaConsultorDTO novaOferta) {

        Solicitacao solicitacao = solicitacaoRepository.consultaPorId(novaOferta.solicitacao())
                .orElseThrow(() -> new ExcecaoDeSistema(HttpStatus.NOT_FOUND, "A solicitacao não existe!"));

        //aceita se oferta nova e menor que atual, e se caso não tenha ofertaAtual
        var ofertaAtualOp = Optional.ofNullable(solicitacao.getOfertaAtual());
        Boolean aceitaOferta = ofertaAtualOp
                .map(ofertaAtual -> ofertaNovaMenorQueAtual(novaOferta, ofertaAtual))
                .orElse(true);

        if(aceitaOferta){
             return mudarOfertaAtual(novaOferta, solicitacao);
        }else {
            return new OfertaConsultorDTO(novaOferta.solicitacao(),novaOferta.consultor(), novaOferta.prazo(), false);
        }

    }

    @Override
    public OfertaAtendimento buscarOfertaAtual(Integer solicitacaoId) {
        var solicitacao = solicitacaoRepository.consultaPorId(solicitacaoId)
                .orElseThrow(() -> new ExcecaoDeSistema(HttpStatus.NOT_FOUND, "A solicitacao não existe!"));

        var ofertaAtual = Optional.ofNullable(solicitacao.getOfertaAtual())
                .orElseThrow(() -> new ExcecaoDeSistema(HttpStatus.NOT_FOUND, "Solicitação não possui oferta!"));

        return ofertaAtual;
    }

    @Override
    public OfertaAtendimento aceitarOfertaAtual(Integer solicitacaoId) {

        var solicitacao = solicitacaoRepository.consultaPorId(solicitacaoId)
                .orElseThrow(() -> new ExcecaoDeSistema(HttpStatus.NOT_FOUND, "A solicitacao não existe!"));

        var ofertaAtual = Optional.ofNullable(solicitacao.getOfertaAtual())
                .orElseThrow(() -> new ExcecaoDeSistema(HttpStatus.NOT_FOUND, "Solicitação não possui oferta!"));

        ofertaAtual.aceitarOferta();
        solicitacao.setOfertaAtual(ofertaAtual);

        var codigo = exameRepository.criarExame(solicitacao).codigoDeConfirmacao();

        System.out.println("Oferta do Consultor de cpf: " + solicitacao.getOfertaAtual().getConsultor().getCpf() + ", foi aceita!" );
        System.out.println("Codigo para atendimento do exame: " + codigo);
        return solicitacaoRepository.aceitaOferta(solicitacao);
    }

    private static boolean ofertaNovaMenorQueAtual(OfertaConsultorDTO oferta, OfertaAtendimento ofertaAtual) {
        var prazoAtual = ofertaAtual.getPrazoParaAtendimento();
        var prazoNovo = oferta.prazo();
        return prazoNovo.compareTo(prazoAtual) < 0;
    }

    private OfertaConsultorDTO mudarOfertaAtual(OfertaConsultorDTO oferta, Solicitacao solicitacao) {
        OfertaAtendimento ofertaNova = new OfertaAtendimento(oferta.consultor(), oferta.prazo(), null);
        solicitacaoRepository.mudaOferta(solicitacao.getId(), ofertaNova);
        System.out.println("Clinica de cnpj: " +solicitacao.getClinica().getCnpj() + " recebeu uma oferta nova melhor!");
        return new OfertaConsultorDTO(solicitacao.getId(), ofertaNova.getConsultor(), ofertaNova.getPrazoParaAtendimento(), true);
    }

    @Override
    public Exame realizarExame(String codigo) {
        var exame = exameRepository.exameExiste(codigo)
                .orElseThrow(()-> new ExcecaoDeSistema(HttpStatus.NOT_FOUND, "Solicitação não existe para realizar exame!"));
        var exameRealizado = exame.getMomentoAtendimento() != null;

        if(exameRealizado)
            throw new ExcecaoDeSistema(HttpStatus.BAD_REQUEST, "Exame já foi realizado!");

        return exameRepository.realizarAtendimento(exame);
    }

    @Override
    public Exame encaminharLaudo(Exame exame) {
        return null;
    }




}
