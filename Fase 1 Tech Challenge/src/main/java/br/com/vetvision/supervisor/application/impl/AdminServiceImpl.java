package br.com.vetvision.supervisor.application.impl;

import br.com.vetvision.supervisor.application.AdminService;
import br.com.vetvision.supervisor.domain.model.plano.PlanoVeterinario;
import br.com.vetvision.supervisor.domain.model.plano.PlanoVeterinarioRepository;
import br.com.vetvision.supervisor.domain.model.plano.TipoExame;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Log
@Service
public class AdminServiceImpl implements AdminService {

    private final PlanoVeterinarioRepository repository;

    public AdminServiceImpl(PlanoVeterinarioRepository repository) {
        this.repository = repository;
    }

    @Override
    public PlanoVeterinario adicionaPlano(PlanoVeterinarioDTO planoDTO) {
        PlanoVeterinario plano = new PlanoVeterinario(planoDTO.cnpj(), planoDTO.nome());
        planoDTO.examesCobertos().forEach(plano::adicionaExameCoberto);

        return repository.criarPlano(plano);
    }

    @Override
    public List<PlanoVeterinarioExamesSimplesDTO> listaPlanos() {
        return repository.listaPlanos()
                .stream().map(AdminServiceImpl::toExamesSimples).toList();
    }

    private static PlanoVeterinarioExamesSimplesDTO toExamesSimples(PlanoVeterinario plano) {
        List<String> nomeExamesCobertos = plano.getExamesCobertos().stream()
                .map(TipoExame::getNome).toList();

        return new PlanoVeterinarioExamesSimplesDTO(plano.getCnpj(), plano.getNome(), nomeExamesCobertos);
    }

    @Override
    public Set<TipoExame> listaTipoExames() {
        return repository.listaTipoExames();
    }
}
