package com.team42.churninsight.api.controller;


import com.team42.churninsight.api.dto.PredictionRequest;
import com.team42.churninsight.api.dto.PredictionResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/predictions")
public class PredictionController {

    @PostMapping
    public ResponseEntity<PredictionResponse> predictionChurn (@RequestBody @Valid PredictionRequest request){
//        var response = new PredictionResponse();
//        return ResponseEntity.ok(response);
        return null;
    }

}
