package br.com.booknrest.booknrest.gateway;

import br.com.booknrest.booknrest.entities.Cliente;
import br.com.booknrest.booknrest.infra.persistence.reserva.ClienteEntity;
import br.com.booknrest.booknrest.infra.persistence.reserva.ClienteRepositoryJPA;
import br.com.booknrest.booknrest.infra.persistence.reserva.ReservaEntityMapper;
import org.springframework.stereotype.Repository;

@Repository
public class ClienteGateway {

    private final ClienteRepositoryJPA repository;
    private final ReservaEntityMapper mapper;

    public ClienteGateway(ClienteRepositoryJPA repository, ReservaEntityMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public Cliente getOrSave(Cliente cliente) {
        ClienteEntity clienteEntity = getOrSave(mapper.toEntity(cliente));
        return mapper.toDomain(clienteEntity);
    }

    private ClienteEntity getOrSave(ClienteEntity entityToCheck) {
        return repository.getByNome(entityToCheck.getNome()).orElseGet(() ->
                repository.save(entityToCheck)
        );
    }
}
