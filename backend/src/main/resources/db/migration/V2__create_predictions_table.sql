CREATE TABLE `predictions`(
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `customer_id` BIGINT UNSIGNED NOT NULL,
    `transaction_id` VARCHAR(255) NOT NULL,
    -- churn_probability -> probability_churn
    -- DECIMAL(5, 4) -> DOUBLE
    `probability_churn` DOUBLE NOT NULL,
    -- churn_status -> churn
    `churn` ENUM(
        'CHURN',
        'NO_CHURN'
    ),
    `recommended_action` TEXT NOT NULL,
    -- prediction_date -> created_at
    `created_at` TIMESTAMP NOT NULL,

-- Índice para buscar última predicción de un cliente
    INDEX idx_customer_date (customer_id, created_at DESC)
);

ALTER TABLE `predictions`
    ADD CONSTRAINT `predictions_customer_id_foreign`
        FOREIGN KEY(`customer_id`)
            -- customes -> customer_critical
            REFERENCES `customer_critical`(`id`);