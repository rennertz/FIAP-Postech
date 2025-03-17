INSERT INTO restaurante
    (capacidade, localizacao, nome, tipo_cozinha)
VALUES
    (50, 'Rua A Melhor, 50', 'O Melhor', 'The best');

INSERT INTO horario_de_funcionamento
    (dia_da_semana, hora_abertura, hora_fechamento, restaurante_id)
VALUES
    ('MONDAY', '18:00:00', '23:00:00',(SELECT id FROM restaurante WHERE nome='O Melhor'));
