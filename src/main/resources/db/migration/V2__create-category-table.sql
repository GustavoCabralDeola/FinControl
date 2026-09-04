CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE category(
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    description VARCHAR(100) NOT NULL,
    purpose VARCHAR(20) NOT NULL
);