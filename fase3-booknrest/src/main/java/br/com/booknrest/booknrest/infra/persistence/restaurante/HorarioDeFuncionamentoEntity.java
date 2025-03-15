package br.com.booknrest.booknrest.infra.persistence.restaurante;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Data
@Entity
public class HorarioDeFuncionamentoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private DayOfWeek diaDaSemana;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime horaAbertura;
    private LocalTime horaFechamento;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "restaurante_id", nullable=false)
    private RestauranteEntity restaurante;

    public HorarioDeFuncionamentoEntity() {
    }

    public HorarioDeFuncionamentoEntity(Long id, DayOfWeek diaDaSemana, LocalTime horaAbertura, LocalTime horaFechamento) {
        this.id = id;
        this.diaDaSemana = diaDaSemana;
        this.horaAbertura = horaAbertura;
        this.horaFechamento = horaFechamento;
    }
}