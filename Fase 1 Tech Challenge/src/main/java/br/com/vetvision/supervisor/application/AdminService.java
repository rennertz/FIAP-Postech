package br.com.vetvision.supervisor.application;

import br.com.vetvision.supervisor.domain.model.plano.PlanoVeterinario;
import br.com.vetvision.supervisor.domain.model.plano.TipoExame;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CNPJ;

import java.util.List;
import java.util.Set;

public interface AdminService {
    PlanoVeterinario adicionaPlano(@Valid PlanoVeterinarioDTO planoVeterinarioDTO);

    List<PlanoVeterinarioExamesSimplesDTO> listaPlanos();

    Set<TipoExame> listaTipoExames();

    record PlanoVeterinarioDTO(
            @NotBlank @CNPJ String cnpj,
            @NotBlank String nome,
            @NotNull @Size(min = 1) List<TipoExame> examesCobertos) {}
    record PlanoVeterinarioExamesSimplesDTO(String cnpj, String nome, List<String> nomeExamesCobertos) {}

}
