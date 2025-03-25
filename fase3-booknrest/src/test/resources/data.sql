INSERT INTO restaurante
    (capacidade, localizacao, nome, tipo_cozinha)
VALUES
    (50, 'Rua A Melhor, 50', 'O Melhor', 'The best');

INSERT INTO horario_de_funcionamento
    (dia_da_semana, hora_abertura, hora_fechamento, restaurante_id)
VALUES
    ('SATURDAY', '18:00:00', '23:00:00',(SELECT id FROM restaurante WHERE nome='O Melhor'));

INSERT INTO cliente
    (nome, telefone)
VALUES
    ('Fulano', '20 99999-9999');

INSERT INTO reserva
    (cliente_id, data_hora, quantidade_pessoas, restaurante_id, status)
VALUES
    ((SELECT id FROM cliente WHERE nome='Fulano'), '2027-03-13 20:00:00', 10, 1, 'CONFIRMADA');