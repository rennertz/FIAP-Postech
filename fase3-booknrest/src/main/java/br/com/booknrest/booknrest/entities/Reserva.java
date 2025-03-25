package br.com.booknrest.booknrest.entities;

import br.com.booknrest.booknrest.exceptions.ErroDeValidacao;

import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Objects;

public class Reserva {
    private final Long id;
    private final Restaurante restaurante;
    private final Cliente cliente;
    private final LocalDateTime dataHora;
    private final int quantidadePessoas;
    private StatusReserva status;

    public Reserva(Long id, Restaurante restaurante, Cliente cliente, LocalDateTime dataHora, int quantidadePessoas, StatusReserva status) {
        if (Objects.isNull(restaurante) || Objects.isNull(cliente) || Objects.isNull(dataHora) || quantidadePessoas == 0  || Objects.isNull(status)) {
            throw new ErroDeValidacao("Campos restaurante, cliente, dataHora, quantidadePessoas e status devem ser informados");
        }

        if (quantidadePessoas < 0 || quantidadePessoas > 100) {
            throw new ErroDeValidacao("capacidade deve ser maior que zero e menor do que 100");
        }

        if (!restaurante.estaraAbertoNoHorario(dataHora)) {
            throw new ErroDeValidacao("O restaurante não está aberto neste horário, " + dataHora.toLocalTime() +" de "+ dataHora.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.of("pt","BR")));
        }

        this.id = id;
        this.restaurante = restaurante;
        this.cliente = cliente;
        this.dataHora = dataHora;
        this.quantidadePessoas = quantidadePessoas;
        this.status = status;
    }

    public Reserva(Restaurante restaurante, Cliente cliente, LocalDateTime dataHora, int quantidadePessoas) {
        this(null, restaurante, cliente, dataHora, quantidadePessoas, StatusReserva.PENDENTE);
    }

    public void confirmar() {
        switch (this.status) {
            case PENDENTE -> this.status = StatusReserva.CONFIRMADA;
            case CONFIRMADA -> throw new ErroDeValidacao("Reserva já foi confirmada.");
            case null, default -> throw new ErroDeValidacao("Reserva não pode ser confirmada.");
        }
    }

    public void cancelar() {
        if (this.status == StatusReserva.CANCELADA) {
            throw new ErroDeValidacao("Reserva já foi cancelada.");
        }
        this.status = StatusReserva.CANCELADA;
    }

    public Long getId() {
        return id;
    }

    public Restaurante getRestaurante() {
        return restaurante;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public int getQuantidadePessoas() {
        return quantidadePessoas;
    }

    public StatusReserva getStatus() {
        return status;
    }

    public enum StatusReserva {
        PENDENTE,
        CONFIRMADA,
        CANCELADA
    }

    @Override
    public String toString() {
        return "Reserva{" +
                "id=" + id +
                ", restaurante=" + restaurante +
                ", cliente=" + cliente +
                ", dataHora=" + dataHora +
                ", quantidadePessoas=" + quantidadePessoas +
                ", status=" + status +
                '}';
    }
}