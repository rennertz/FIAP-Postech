package br.com.booknrest.booknrest.entities;

import br.com.booknrest.booknrest.exceptions.ErroDeValidacao;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.Collections.unmodifiableList;

public class Restaurante {
    private final Long id;
    private final String nome;
    private final String localizacao;
    private final String tipoCozinha;

    private final List<HorarioDeFuncionamento> horariosDeFuncionamento = new ArrayList<>();

    private final int capacidade;

    public Restaurante(Long id, String nome, String localizacao, String tipoCozinha, int capacidade) {
        this.id = id;
        this.nome = nome;
        this.localizacao = localizacao;
        this.tipoCozinha = tipoCozinha;
        this.capacidade = capacidade;
    }

    public void adicionaHorarioDeFuncionamento(HorarioDeFuncionamento horario) {
        horariosDeFuncionamento.forEach(horarioExistente ->
                validaSeConflita(horario, horarioExistente));

        this.horariosDeFuncionamento.add(horario);
        validaApenasDoisHorariosPorDia();
    }

    private void validaApenasDoisHorariosPorDia() {
        Map<DayOfWeek, Long> contagemPorDia = horariosDeFuncionamento.stream().collect(Collectors.groupingBy(
                HorarioDeFuncionamento::getDiaDaSemana, Collectors.counting()
        ));

        contagemPorDia.entrySet().stream()
                .filter(horariosNoDia -> horariosNoDia.getValue() > 2)
                .findAny()
                .ifPresent(count -> {
                        throw new ErroDeValidacao("Não é permitido mais de 2 horarios por dia da semana");
                });
    }

    private void validaSeConflita(HorarioDeFuncionamento horario, HorarioDeFuncionamento horarioExistente) {
        if (estaSobrepondo(horario, horarioExistente)) {
            throw new ErroDeValidacao("Horários conflitantes: ");
        }
    }

    private boolean estaSobrepondo(HorarioDeFuncionamento horario, HorarioDeFuncionamento horarioExistente) {
        return !horario.getHoraFechamento().isBefore(horarioExistente.getHoraAbertura()) &&
                !horario.getHoraAbertura().isAfter(horarioExistente.getHoraFechamento());
    }

    public List<HorarioDeFuncionamento> getHorariosDeFuncionamento() {
        return unmodifiableList(this.horariosDeFuncionamento);
    }

    public Long getId() {
        return this.id;
    }

    public String getNome() {
        return this.nome;
    }

    public String getLocalizacao() {
        return this.localizacao;
    }

    public String getTipoCozinha() {
        return this.tipoCozinha;
    }

    public int getCapacidade() {
        return this.capacidade;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Restaurante that = (Restaurante) o;
        return capacidade == that.capacidade &&
                Objects.equals(id, that.id) &&
                Objects.equals(nome, that.nome) &&
                Objects.equals(localizacao, that.localizacao) &&
                Objects.equals(tipoCozinha, that.tipoCozinha) &&
                Objects.equals(horariosDeFuncionamento, that.horariosDeFuncionamento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, localizacao, tipoCozinha, horariosDeFuncionamento, capacidade);
    }

    @Override
    public String toString() {
        return "Restaurante(id=" + this.getId() + ", nome=" + this.getNome() + ", localizacao=" + this.getLocalizacao() + ", tipoCozinha=" + this.getTipoCozinha() + ", horariosDeFuncionamento=" + this.getHorariosDeFuncionamento() + ", capacidade=" + this.getCapacidade() + ")";
    }
}
