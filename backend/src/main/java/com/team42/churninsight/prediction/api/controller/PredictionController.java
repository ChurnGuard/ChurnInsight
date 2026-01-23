package com.team42.churninsight.prediction.api.controller;


import com.team42.churninsight.prediction.api.dto.DetailsPredictionResponse;
import com.team42.churninsight.prediction.api.dto.PredictionRequest;
import com.team42.churninsight.prediction.api.dto.PredictionResponse;
import com.team42.churninsight.prediction.service.PredictionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/predictions")
@RequiredArgsConstructor
public class PredictionController {

    private final PredictionService predictionService;

    @PostMapping
    public ResponseEntity<PredictionResponse> predictionChurn (@RequestBody @Valid PredictionRequest request){
        var response = predictionService.predict(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetailsPredictionResponse> getPredictionById (@PathVariable Long id){
        var response = predictionService.getPrediction(id);
        return ResponseEntity.ok(response);
    }
}
