CREATE TABLE `customer_critical`(
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    -- external_id -> customer_id
    `customer_id` VARCHAR(255) NOT NULL,
    -- DECIMAL(5, 4) -> DOUBLE
    `priority_score` DOUBLE NOT NULL,
    -- Se maneja en predicitions pero está en JPA
    `churn_probability` DOUBLE NOT NULL,
    `economic_value` ENUM(
        'HIGH_VALUE_CUSTOMER',
        'MEDIUM_VALUE_CUSTOMER',
        'LOW_VALUE_CUSTOMER'
    ) NOT NULL,
    -- Aún no en JPA
    `economic_value_score` DECIMAL(5, 4) NOT NULL,
    -- Se maneja en predictions pero está en JPA
    `recommended_action` VARCHAR(255) NOT NULL,
    -- Aún no en JPA
    `customer_profile` ENUM(
        'IN_STORE_DEAL_HUNTER',
        'HIGH_VALUE_DISCOUNT_ONLINE',
        'ESSENTIAL_MODERATE_BUYER'
    ) NOT NULL,
    `updated_at` TIMESTAMP NOT NULL,
    -- Aún no en JPA
    `created_at` TIMESTAMP NOT NULL,

-- Índice para buscar por mayor priority score
    INDEX idx_priority_score_desc (priority_score DESC),

-- Índice para buscar clientes por external_id
    UNIQUE INDEX idx_customer_id (customer_id)

);