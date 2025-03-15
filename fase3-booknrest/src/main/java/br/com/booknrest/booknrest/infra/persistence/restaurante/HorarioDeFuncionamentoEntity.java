package br.com.booknrest.booknrest.infra.persistence.restaurante;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Objects;

@Entity
@Table(name = "horario_de_funcionamento")
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
    @JoinColumn(name = "restaurante_id", nullable = false)
    private RestauranteEntity restaurante;

    public HorarioDeFuncionamentoEntity() {
    }

    public HorarioDeFuncionamentoEntity(Long id, DayOfWeek diaDaSemana, LocalTime horaAbertura, LocalTime horaFechamento) {
        this.id = id;
        this.diaDaSemana = diaDaSemana;
        this.horaAbertura = horaAbertura;
        this.horaFechamento = horaFechamento;
    }

    public Long getId() {
        return this.id;
    }

    public DayOfWeek getDiaDaSemana() {
        return this.diaDaSemana;
    }

    public LocalTime getHoraAbertura() {
        return this.horaAbertura;
    }

    public LocalTime getHoraFechamento() {
        return this.horaFechamento;
    }

    public void setRestaurante(RestauranteEntity restaurante) {
        this.restaurante = restaurante;
    }

    public RestauranteEntity getRestaurante() {
        return this.restaurante;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        HorarioDeFuncionamentoEntity that = (HorarioDeFuncionamentoEntity) o;
        return Objects.equals(id, that.id) &&
                diaDaSemana == that.diaDaSemana &&
                Objects.equals(horaAbertura, that.horaAbertura) &&
                Objects.equals(horaFechamento, that.horaFechamento) &&
                Objects.equals(restaurante, that.restaurante);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, diaDaSemana, horaAbertura, horaFechamento, restaurante);
    }

    @Override
    public String toString() {
        return "HorarioDeFuncionamentoEntity(id=" + this.getId() + ", diaDaSemana=" + this.getDiaDaSemana() + ", horaAbertura=" + this.getHoraAbertura() + ", horaFechamento=" + this.getHoraFechamento() + ", restaurante=" + this.getRestaurante() + ")";
    }
}