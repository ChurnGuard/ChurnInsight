/*
Aqui definimos la interfaz para el servicio de predicción
* */

package com.team42.churninsight.prediction.service;

import com.team42.churninsight.prediction.api.dto.DetailsPredictionResponse;
import com.team42.churninsight.prediction.api.dto.PredictionRequest;
import com.team42.churninsight.prediction.api.dto.PredictionResponse;

public interface PredictionService {
    PredictionResponse predict(PredictionRequest request);

    DetailsPredictionResponse getPrediction(Long id);
}
