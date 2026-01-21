package com.team42.churninsight.prediction.repository;

import com.team42.churninsight.customer.entity.Customer;
import com.team42.churninsight.prediction.Prediction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PredictionRepository extends JpaRepository<Prediction, Long> {

    @Query(value = "SELECT p.recommended_action FROM predictions p WHERE p.customer_id = :customerId ORDER BY p.prediction_date DESC LIMIT 1", nativeQuery = true)
    Optional<String> findLastRecommendedActionByCustomer(@Param("customerId") Long customerId);
}
