package br.com.vetvision.supervisor.infrastructure.persistence;

import br.com.vetvision.supervisor.domain.model.solicitacao.Pet;
import br.com.vetvision.supervisor.domain.model.solicitacao.PetReadOnlyRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PetReadOnlyRepositoryJPA extends JpaRepository<Pet, Long>, PetReadOnlyRepository {

    @Override
    default Optional<Pet> petExiste(String nome, String cpfResponsavel) {
        return findByNomeAndCpfResponsavel(nome, cpfResponsavel)
                .stream().findAny();
    }

    List<Pet> findByNomeAndCpfResponsavel(String nome, String cpfResponsavel);
}
