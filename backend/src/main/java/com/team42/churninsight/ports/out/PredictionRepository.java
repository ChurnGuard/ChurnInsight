package com.team42.churninsight.ports.out;

import com.team42.churninsight.domain.Prediction;

import java.util.List;
import java.util.Optional;

public interface PredictionRepository {
    Prediction save(Prediction prediction);
    Optional<Prediction> findById(Long id);
    List<Prediction> findByCustomerId(String customerId);
}