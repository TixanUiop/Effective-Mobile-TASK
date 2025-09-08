-- create users table
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    role VARCHAR(20) NOT NULL
);

-- create cards table
CREATE TABLE cards (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    number VARCHAR(19) NOT NULL,
    owner VARCHAR(100) NOT NULL,
    expiry_date DATE NOT NULL,
    status VARCHAR(20) NOT NULL,
    balance DECIMAL(12,2) NOT NULL,
    user_id BIGINT NOT NULL,
    CONSTRAINT fk_cards_user FOREIGN KEY (user_id) REFERENCES users(id)
);

-- create transactions table
CREATE TABLE transactions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    from_card_id BIGINT NOT NULL,
    to_card_id BIGINT NOT NULL,
    amount DECIMAL(12,2) NOT NULL,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_transactions_from FOREIGN KEY (from_card_id) REFERENCES cards(id),
    CONSTRAINT fk_transactions_to FOREIGN KEY (to_card_id) REFERENCES cards(id)
);