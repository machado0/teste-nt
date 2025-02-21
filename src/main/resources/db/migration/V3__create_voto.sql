CREATE TABLE voto (
    id BIGSERIAL PRIMARY KEY,
    associado_id BIGINT NOT NULL,
    pauta_id BIGINT NOT NULL,
    voto BOOLEAN NOT NULL,
    FOREIGN KEY (associado_id) REFERENCES associado(id),
    FOREIGN KEY (pauta_id) REFERENCES pauta(id)
);
