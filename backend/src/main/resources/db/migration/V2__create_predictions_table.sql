CREATE TABLE `predictions`(
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `customer_id` BIGINT UNSIGNED NOT NULL,
    -- DECIMAL(5, 4) -> DOUBLE
    `churn_probability` DECIMAL(5,2) NOT NULL,
    `churn_status` ENUM(
        'CHURN',
        'NO_CHURN'
    ) NOT NULL,
    `recommended_action` TEXT NOT NULL,
    `prediction_date` TIMESTAMP NOT NULL,


    CONSTRAINT fk_customer FOREIGN KEY(customer_id) REFERENCES customers(id),

-- Índice para buscar última predicción de un cliente
    INDEX idx_customer_date (customer_id, prediction_date DESC)
);