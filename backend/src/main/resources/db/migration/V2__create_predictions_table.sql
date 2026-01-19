CREATE TABLE `predictions`(
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `customer_id` BIGINT UNSIGNED NOT NULL,
    `transaction_id` VARCHAR(255) NOT NULL,
    -- DECIMAL(5, 4) -> DOUBLE
    `churn_probability` DOUBLE NOT NULL,
    `churn_status` ENUM(
        'CHURN',
        'NO_CHURN'
    ),
    `recommended_action` TEXT NOT NULL,
    `prediction_date` TIMESTAMP NOT NULL,

-- Índice para buscar última predicción de un cliente
    INDEX idx_customer_date (customer_id, prediction_date DESC)
);

ALTER TABLE `predictions`
    ADD CONSTRAINT `predictions_customer_id_foreign`
        FOREIGN KEY(`customer_id`)
            REFERENCES `customers`(`id`);