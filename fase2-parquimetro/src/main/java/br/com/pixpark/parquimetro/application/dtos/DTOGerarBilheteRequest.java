package br.com.pixpark.parquimetro.application.dtos;

import jakarta.validation.constraints.NotBlank;

public record DTOGerarBilheteRequest(
        @NotBlank String placa,
        @NotBlank int tempoEmHoras
) {}
