# language: pt

Funcionalidade: Criacao de reserva

  Cenario: Nova reserva pode ser criada
    Dado que existe ao menos um restaurante cadastrado
    Quando criar nova reserva
    Entao deve responder que a reserva foi criada corretamente

  Cenario: As reservas podem ser listados
    Dado que existe ao menos uma reserva cadastrada
    Quando o usuario listar as reservas
    Entao deve retornar a lista com as reservas existentes
