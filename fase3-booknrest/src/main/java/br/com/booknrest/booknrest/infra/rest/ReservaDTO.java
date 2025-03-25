package br.com.booknrest.booknrest.infra.rest;

import br.com.booknrest.booknrest.entities.Cliente;
import br.com.booknrest.booknrest.entities.Reserva;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

public record ReservaDTO(
        Long id,
        @Positive
        long restauranteId,
        @NotNull @Valid
        ClienteDto cliente,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
        LocalDateTime dataHora,
        @Positive
        int quantidadePessoas,
        Reserva.StatusReserva status
) {

    public record ClienteDto(
            Long id,

            @NotEmpty
            String nome,

            @NotNull
            String telefone
    ) {}


    public static Cliente toModel(ClienteDto req) {
        return new Cliente(req.id(), req.nome(), req.telefone());
    }

    static ReservaDTO toDTO(Reserva req) {
        return new ReservaDTO(req.getId(), req.getRestaurante().getId(), toDTO(req.getCliente()), req.getDataHora(), req.getQuantidadePessoas(), req.getStatus());
    }

    public static ClienteDto toDTO(Cliente req) {
        return new ClienteDto(req.getId(), req.getNome(), req.getTelefone());
    }
}
