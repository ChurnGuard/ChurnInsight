CREATE TABLE `risk_flags`(
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `prediction_id` BIGINT UNSIGNED NOT NULL,
    `flag_type` ENUM(
        'INACTIVITY_RISK',
        'FINANCIAL_RISK',
        'PROMO_ABUSE'
    ) NOT NULL,
    `detected_at` TIMESTAMP NOT NULL,

    CONSTRAINT fk_prediction FOREIGN KEY(prediction_id) REFERENCES predictions(id)
);
