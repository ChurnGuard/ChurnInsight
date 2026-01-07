package com.team42.churninsight.prediction.repository;

import com.team42.churninsight.prediction.Prediction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PredictionRepository extends JpaRepository<Prediction, Long> {
}
