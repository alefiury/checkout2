CREATE DATABASE "banco_precos2";

CREATE TABLE produtos(
    id_produto SERIAL PRIMARY KEY,
    nome_item TEXT,
    preco NUMERIC
);

CREATE TABLE promocoes(
    id_promocao NUMERIC PRIMARY KEY
);

CREATE TABLE produto_promocao(
    id SERIAL PRIMARY KEY,
    id_produto NUMERIC,
    id_promocao NUMERIC
);

SELECT * FROM produtos;
SELECT * FROM promocoes;
SELECT * FROM produto_promocao;

DELETE FROM produtos;
DELETE FROM promocoes;
DELETE FROM produto_promocao;

SELECT * FROM produtos WHERE nome_item='A';


INSERT INTO promocoes(id_promocao) VALUES (3) ON CONFLICT DO NOTHING;
