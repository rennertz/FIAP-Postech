package br.com.vetvision.supervisor.domain.model.solicitacao;

import java.util.Optional;

public interface PetReadOnlyRepository {
    Optional<Pet> petExiste(String nome, String cpfResponsavel);
}
