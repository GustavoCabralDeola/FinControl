CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE transactions(
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    description VARCHAR(100) NOT NULL,
    transactionType VARCHAR(20) NOT NULL,

    category_id UUID NOT NULL,
    customer_id UUID NOT NULL,

    CONSTRAINT fk_transaction_category
        FOREIGN KEY (category_id)
            REFERENCES category(id),

    CONSTRAINT fk_transaction_customer
        FOREIGN KEY (customer_id)
            REFERENCES customer(id)
);