CREATE TABLE pessoa (
    codigo BIGINT(20) PRIMARY KEY  AUTO_INCREMENT,
    nome VARCHAR(50) NOT NULL,
    ativo BOOLEAN NOT NULL,
    logradouro VARCHAR (60),
    numero VARCHAR (40),
    complemento VARCHAR(60),
    bairro VARCHAR (60),
    cep VARCHAR (25),
    cidade VARCHAR (60),
    estado VARCHAR (40)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO pessoa (nome, ativo, logradouro, numero, complemento, bairro, cep, cidade, estado)
values ('José', true, 'Rua Carlos Matos', '2', 's/a', 'Castelo de Amorin', '43584-123', 'Brasilia', 'Distrito Federal');
INSERT INTO pessoa (nome, ativo, logradouro, numero, complemento, bairro, cep, cidade, estado)
values ('Maria', true, 'Rua Carlos Matos', '2', 's/a', 'Castelo de Amorin', '43584-123', 'Brasilia', 'Distrito Federal');
INSERT INTO pessoa (nome, ativo, logradouro, numero, complemento, bairro, cep, cidade, estado)
values ('Carlos', true, 'Rua Carlos Matos', '2', 's/a', 'Castelo de Amorin', '43584-123', 'Brasilia', 'Distrito Federal');
INSERT INTO pessoa (nome, ativo, logradouro, numero, complemento, bairro, cep, cidade, estado)
values ('Ana', true, 'Rua Carlos Matos', '2', 's/a', 'Castelo de Amorin', '43584-123', 'Brasilia', 'Distrito Federal');

