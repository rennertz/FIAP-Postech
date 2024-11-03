package br.com.vetvision.supervisor.application.impl;

import br.com.vetvision.supervisor.application.AtendimentoService;
import br.com.vetvision.supervisor.application.exceptions.ExcecaoDeSistema;
import br.com.vetvision.supervisor.domain.model.exame.Exame;
import br.com.vetvision.supervisor.domain.model.oferta.OfertaAtendimento;
import br.com.vetvision.supervisor.domain.model.plano.PlanoVeterinario;
import br.com.vetvision.supervisor.domain.model.plano.PlanoVeterinarioRepository;
import br.com.vetvision.supervisor.domain.model.plano.TipoExame;
import br.com.vetvision.supervisor.domain.model.solicitacao.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AtendimentoServiceImpl implements AtendimentoService {

    private final SolicitacaoRepository solicitacaoRepository;
    private final ClinicaRepository clinicaRepository;
    private final PetReadOnlyRepository petRepository;
    private final PlanoVeterinarioRepository planoVeterinarioRepository;

    @Autowired
    public AtendimentoServiceImpl(SolicitacaoRepository solicitacaoRepository, ClinicaRepository clinicaRepository,
                                  PetReadOnlyRepository petRepository, PlanoVeterinarioRepository planoRepository) {
        this.solicitacaoRepository = solicitacaoRepository;
        this.clinicaRepository = clinicaRepository;
        this.petRepository = petRepository;
        this.planoVeterinarioRepository = planoRepository;
    }

    // TODO ocultar exames cobertos pelo plano
    @Override
    public Solicitacao solicitarExame(SolicitacaoDTO solicitacaoDTO) {

        // verifica se a clínica existe, antes de criar uma nova
        Clinica clinica = clinicaRepository.clinicaExiste(solicitacaoDTO.clinica().getCnpj())
                .orElseGet(()-> clinicaRepository.criarClinica(solicitacaoDTO.clinica()));

        // verifica se a pet existe, senão repassa o enviado para criação
        Pet pet = petRepository.petExiste(solicitacaoDTO.pet().getNome(), solicitacaoDTO.pet().getCpfResponsavel())
                .orElse(solicitacaoDTO.pet());

        // Verifica se o plano existe. Caso não, lança exceção
        PlanoVeterinario plano = planoVeterinarioRepository.planoExiste(solicitacaoDTO.cnpjPlano())
                .orElseThrow(()-> new ExcecaoDeSistema(HttpStatus.BAD_REQUEST,"Plano não cadastrado no sistema"));

        // Verifica se o exame é atendido pelo plano. Caso não, lança exceção
        TipoExame tipoExame = plano.getExamesCobertos().stream()
                .filter(exame -> exame.getNome().equalsIgnoreCase(solicitacaoDTO.tipoExame()))
                .findAny()
                .orElseThrow(() -> new ExcecaoDeSistema(HttpStatus.BAD_REQUEST,"Exame não atendido pelo plano selecionado"));


        Solicitacao solicitacao = new Solicitacao(clinica, pet, tipoExame, plano);

        try {
            Solicitacao criada = solicitacaoRepository.cria(solicitacao);
            // TODO mockar serviço de notificação de nova solicitação aos consultores
            return criada;
        } catch (DataIntegrityViolationException e) {
            throw new ExcecaoDeSistema(HttpStatus.BAD_REQUEST,"Solicitação para este exame já realizada para o pet.");
        }
    }

    @Override
    public Solicitacao consultarSolicitacao(Integer solicitacaoId) {
        return solicitacaoRepository.consultaPorId(solicitacaoId)
            .orElseThrow(() -> new ExcecaoDeSistema(
                HttpStatus.NOT_FOUND, "A solicitacao requisitada não foi encontrada."));
    }

    @Override
    public List<Solicitacao> consultarSolicitacoes(String cnpjClinica) {
        return solicitacaoRepository.consultaPorClinica(cnpjClinica);
    }

    @Override
    public Exame aceitarOferta(OfertaAtendimento ofertaAtendimento) {
        return null;
    }

}
