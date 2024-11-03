package br.com.vetvision.supervisor.application.impl;

import br.com.vetvision.supervisor.application.AtendimentoService;
import br.com.vetvision.supervisor.application.exceptions.ExcecaoDeSistema;
import br.com.vetvision.supervisor.domain.model.exame.Exame;
import br.com.vetvision.supervisor.domain.model.oferta.OfertaAtendimento;
import br.com.vetvision.supervisor.domain.model.plano.PlanoVeterinario;
import br.com.vetvision.supervisor.domain.model.plano.PlanoVeterinarioRepository;
import br.com.vetvision.supervisor.domain.model.plano.TipoExame;
import br.com.vetvision.supervisor.domain.model.solicitacao.Clinica;
import br.com.vetvision.supervisor.domain.model.solicitacao.ClinicaRepository;
import br.com.vetvision.supervisor.domain.model.solicitacao.Solicitacao;
import br.com.vetvision.supervisor.domain.model.solicitacao.SolicitacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

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

    // TODO Impedir a duplicação de solicitação no mesmo dia
    // TODO ocultar exames cobertos pelo plano
    @Override
    public Solicitacao solicitarExame(SolicitacaoDTO solicitacaoDTO) {

        // verifica se a clínica existe, antes de criar uma nova
        Clinica clinica = clinicaRepository.clinicaExiste(solicitacaoDTO.clinica().getCnpj())
                .orElseGet(()-> clinicaRepository.criarClinica(solicitacaoDTO.clinica()));

        // Verifica se o plano existe. Caso não, lança exceção
        PlanoVeterinario plano = planoVeterinarioRepository.planoExiste(solicitacaoDTO.cnpjPlano())
                .orElseThrow(()-> new ExcecaoDeSistema(HttpStatus.BAD_REQUEST,"Plano não cadastrado no sistema"));

        // Verifica se o exame é atendido pelo plano. Caso não, lança exceção
        TipoExame tipoExame = plano.getExamesCobertos().stream()
                .filter(exame -> exame.getNome().equalsIgnoreCase(solicitacaoDTO.tipoExame()))
                .findAny()
                .orElseThrow(() -> new ExcecaoDeSistema(HttpStatus.BAD_REQUEST,"Exame não atendido pelo plano selecionado"));

        Solicitacao solicitacao = new Solicitacao(clinica, solicitacaoDTO.pet(), tipoExame, plano);
        return solicitacaoRepository.criaSolicitacao(solicitacao);
    }

    @Override
    public Exame aceitarOferta(OfertaAtendimento ofertaAtendimento) {
        return null;
    }

}
