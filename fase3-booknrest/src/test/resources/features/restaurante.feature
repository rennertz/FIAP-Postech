# language: pt

Funcionalidade: Cadastro de restaurante

  Cenario: Novo restaurante pode ser cadastrado
    Quando cadastrar novo restaurante
    Entao deve responder que foi criado corretamente

  Cenario: Os restaurantes podem ser listados
    Dado que existe ao menos um restaurante cadastrado
    Quando o usuario listar os restaurantes
    Entao deve retornar a lista com os restaurantes existentes
