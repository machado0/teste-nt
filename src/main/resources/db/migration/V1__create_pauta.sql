CREATE TABLE pauta (
    id BIGSERIAL PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    descricao VARCHAR(255) NOT NULL,
    tempo_encerramento TIMESTAMP NOT NULL
);
