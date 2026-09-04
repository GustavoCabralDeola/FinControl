CREATE EXTENSION IF NOT EXISTS "pgcrypto";


CREATE TABLE customer(
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    name VARCHAR(40) NOT NULL,
    age INT NOT NULL

);

