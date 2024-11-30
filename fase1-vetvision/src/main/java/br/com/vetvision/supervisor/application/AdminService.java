package br.com.vetvision.supervisor.application;

import br.com.vetvision.supervisor.domain.model.plano.PlanoVeterinario;
import br.com.vetvision.supervisor.domain.model.plano.TipoExame;

import java.util.List;
import java.util.Set;

public interface AdminService {
    PlanoVeterinario adicionaPlano(PlanoVeterinarioDTO planoVeterinarioDTO);

    List<PlanoVeterinarioExamesSimplesDTO> listaPlanos();

    Set<TipoExame> listaTipoExames();

    record PlanoVeterinarioDTO(String cnpj, String nome, List<TipoExame> examesCobertos) {}
    record PlanoVeterinarioExamesSimplesDTO(String cnpj, String nome, List<String> nomeExamesCobertos) {}

}
