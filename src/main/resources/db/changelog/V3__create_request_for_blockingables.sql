CREATE TABLE request_for_blocking (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    card_id BIGINT NOT NULL,
    reason VARCHAR(100) NOT NULL,
    expiry_date DATE NOT NULL,
    CONSTRAINT fk_request_user FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT fk_request_card FOREIGN KEY (card_id) REFERENCES cards(id)
);