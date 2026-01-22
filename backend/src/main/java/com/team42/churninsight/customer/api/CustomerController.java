package com.team42.churninsight.customer.api;

import com.team42.churninsight.customer.dto.CriticalCustomerResponse;
import com.team42.churninsight.customer.service.CustomerService;
import com.team42.churninsight.prediction.api.dto.DetailsPredictionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;




@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/critical")
    public ResponseEntity<List<CriticalCustomerResponse>> getCriticalCustomers() {
        return ResponseEntity.ok(customerService.getCriticalCustomers());
    }

    @GetMapping("/{customerId}/predictions")
    public ResponseEntity<List<DetailsPredictionResponse>> getPredictionsByCustomerId(@PathVariable String customerId){
        return ResponseEntity.ok(customerService.getPredictionsByCustomerId(customerId));
    }
}
