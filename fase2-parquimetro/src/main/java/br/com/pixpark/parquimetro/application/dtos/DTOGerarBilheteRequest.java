package br.com.pixpark.parquimetro.application.dtos;

import jakarta.validation.constraints.NotBlank;

import java.time.Duration;

public record DTOGerarBilheteRequest(
        @NotBlank String placa,
        @NotBlank Duration tempo
) {}
