package com.team42.churninsight.customer.service;

import com.team42.churninsight.customer.dto.CriticalCustomerResponse;
import com.team42.churninsight.prediction.api.dto.DetailsPredictionResponse;

import java.util.List;

public interface CustomerService {
    List<CriticalCustomerResponse> getCriticalCustomers();

    List<DetailsPredictionResponse> getPredictionsByCustomerId(String customerId);
}
