package com.team42.churninsight.prediction.api.controller;


import com.team42.churninsight.prediction.api.dto.PredictionRequest;
import com.team42.churninsight.prediction.api.dto.PredictionResponse;
import com.team42.churninsight.prediction.service.PredictionServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/predictions")
public class PredictionController {

    private final PredictionServiceImpl predictionService;
    public PredictionController (PredictionServiceImpl predServ){this.predictionService = predServ;}


    @PostMapping
    public ResponseEntity<PredictionResponse> predictionChurn (@RequestBody @Valid PredictionRequest request){
        var response = predictionService.predict(request);
        return ResponseEntity.ok(response);
    }

}
