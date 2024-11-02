package br.com.vetvision.supervisor.application.impl;

import br.com.vetvision.supervisor.application.AtendimentoService;
import br.com.vetvision.supervisor.domain.model.exame.Exame;
import br.com.vetvision.supervisor.domain.model.oferta.OfertaAtendimento;
import br.com.vetvision.supervisor.domain.model.plano.PlanoVeterinario;
import br.com.vetvision.supervisor.domain.model.plano.PlanoVeterinarioRepository;
import br.com.vetvision.supervisor.domain.model.solicitacao.Clinica;
import br.com.vetvision.supervisor.domain.model.solicitacao.ClinicaRepository;
import br.com.vetvision.supervisor.domain.model.solicitacao.Solicitacao;
import br.com.vetvision.supervisor.domain.model.solicitacao.SolicitacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AtendimentoServiceImpl implements AtendimentoService {

    private final SolicitacaoRepository solicitacaoRepository;
    private final ClinicaRepository clinicaRepository;
    private final PlanoVeterinarioRepository planoVeterinarioRepository;

    @Autowired
    public AtendimentoServiceImpl(SolicitacaoRepository solicitacaoRepository, ClinicaRepository clinicaRepository, PlanoVeterinarioRepository planoVeterinarioRepository) {
        this.solicitacaoRepository = solicitacaoRepository;
        this.clinicaRepository = clinicaRepository;
        this.planoVeterinarioRepository = planoVeterinarioRepository;
    }

    @Override
    public Solicitacao solicitarExame(Solicitacao solicitacao) {
        verificaSolicitacao(solicitacao);
        return solicitacaoRepository.criaSolicitacao(solicitacao);

    }

    private void verificaSolicitacao(Solicitacao solicitacao) {
        //verifica se a clinica existe antes de criar uma nova
        Clinica clinica = solicitacao.getClinica();
        if(!clinicaRepository.clinicaExiste(clinica.getCnpj())){
            clinica = clinicaRepository.criarClinica(clinica);
        };
        solicitacao.setClinica(clinica);

        //verifica se o plano existe antes de criar um novo
        PlanoVeterinario plano = solicitacao.getPlano();
        if(!planoVeterinarioRepository.planoExiste(plano.getCnpj())){
            plano = planoVeterinarioRepository.criarPlano(plano);
        };
        solicitacao.setPlano(plano);
    }


    @Override
    public Exame aceitarOferta(OfertaAtendimento ofertaAtendimento) {
        return null;
    }

}
