CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE usuarios (
      id UUID CONSTRAINT pk_usuario_id PRIMARY KEY DEFAULT uuid_generate_v4(),
      nome VARCHAR(50) NOT NULL,
      email VARCHAR(100) NOT NULL UNIQUE,
      senha VARCHAR(255) NOT NULL
);
