package br.com.booknrest.booknrest.infra.persistence.reserva;

import br.com.booknrest.booknrest.entities.Reserva;
import br.com.booknrest.booknrest.infra.persistence.restaurante.RestauranteEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "reserva")
public class ReservaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "restaurante_id", nullable = false)
    private RestauranteEntity restaurante;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private ClienteEntity cliente;

    private LocalDateTime dataHora;
    private int quantidadePessoas;

    @Enumerated(EnumType.STRING)
    private Reserva.StatusReserva status;

    public ReservaEntity(Long id, RestauranteEntity restaurante, ClienteEntity cliente, LocalDateTime dataHora, int quantidadePessoas, Reserva.StatusReserva status) {
        this.id = id;
        this.restaurante = restaurante;
        this.cliente = cliente;
        this.dataHora = dataHora;
        this.quantidadePessoas = quantidadePessoas;
        this.status = status;
    }

    public ReservaEntity() {
    }

    public Long getId() {
        return id;
    }

    public RestauranteEntity getRestaurante() {
        return restaurante;
    }

    public ClienteEntity getCliente() {
        return cliente;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public int getQuantidadePessoas() {
        return quantidadePessoas;
    }

    public Reserva.StatusReserva getStatus() {
        return status;
    }
}
