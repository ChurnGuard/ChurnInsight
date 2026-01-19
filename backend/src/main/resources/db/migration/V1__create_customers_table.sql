CREATE TABLE `customers`(
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `external_id` VARCHAR(255) NOT NULL,
    -- DECIMAL(5, 4) -> DOUBLE
    `priority_score` DECIMAL(5,2) NOT NULL,


    `economic_value` ENUM(
        'HIGH_VALUE_CUSTOMER',
        'MEDIUM_VALUE_CUSTOMER',
        'LOW_VALUE_CUSTOMER'
    ) NOT NULL,
    `economic_value_score` DECIMAL(5, 2) NOT NULL,

    `customer_profile` ENUM(
        'IN_STORE_DEAL_HUNTER',
        'HIGH_VALUE_DISCOUNT_ONLINE',
        'ESSENTIAL_MODERATE_BUYER'
    ) NOT NULL,
    `updated_at` TIMESTAMP NOT NULL,
    `created_at` TIMESTAMP NOT NULL,

-- Índice para buscar por mayor priority score
    INDEX idx_priority_score_desc (priority_score DESC),

-- Índice para buscar clientes por external_id
    UNIQUE INDEX idx_customer_id (external_id)

);