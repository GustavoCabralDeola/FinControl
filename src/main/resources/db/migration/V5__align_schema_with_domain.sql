ALTER TABLE customer
    ALTER COLUMN name TYPE VARCHAR(200);

ALTER TABLE category
    ALTER COLUMN description TYPE VARCHAR(400);

ALTER TABLE transactions
    ALTER COLUMN description TYPE VARCHAR(400),
    ALTER COLUMN amount TYPE NUMERIC(19, 2) USING amount::NUMERIC(19, 2);

ALTER TABLE transactions
    RENAME COLUMN transactiontype TO transaction_type;

ALTER TABLE transactions
    DROP CONSTRAINT fk_transaction_customer;

ALTER TABLE transactions
    ADD CONSTRAINT fk_transaction_customer
        FOREIGN KEY (customer_id)
        REFERENCES customer(id)
        ON DELETE CASCADE;
