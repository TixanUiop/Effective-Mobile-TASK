INSERT INTO users (username, password, role) VALUES
    ('admin', '$2a$10$hashedpassword', 'ADMIN'),
    ('user1', '$2a$10$hashedpassword', 'USER');

INSERT INTO cards (number, owner, expiry_date, status, balance, user_id) VALUES
    ('1111222233334444', 'User One', '2026-12-31', 'ACTIVE', 1000.00, 2),
    ('5555666677778888', 'User One', '2025-06-30', 'ACTIVE', 500.00, 2);