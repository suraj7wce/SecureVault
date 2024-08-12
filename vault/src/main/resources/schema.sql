CREATE TABLE IF NOT EXISTS passwords (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    website_or_app_name VARCHAR(255) NOT NULL,
    encrypted_password TEXT NOT NULL
);
