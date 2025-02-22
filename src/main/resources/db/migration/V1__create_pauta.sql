CREATE TABLE pauta (
    id BIGSERIAL PRIMARY KEY,
    titulo VARCHAR(255) UNIQUE NOT NULL,
    descricao VARCHAR(255) NULL,
    tempo_encerramento TIMESTAMP NULL
);
