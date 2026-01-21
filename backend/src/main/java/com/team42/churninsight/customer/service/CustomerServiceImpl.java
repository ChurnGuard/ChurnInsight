package com.team42.churninsight.customer.service;

import com.team42.churninsight.customer.dto.CriticalCustomerResponse;
import com.team42.churninsight.customer.entity.Customer;
import com.team42.churninsight.customer.repository.CustomerRepository;
import com.team42.churninsight.prediction.repository.PredictionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository repository;
    private final PredictionRepository predictionRepository;

    @Override
    public List<CriticalCustomerResponse> getCriticalCustomers() {
        return repository.findTop5ByOrderByPriorityScoreDesc()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private CriticalCustomerResponse toResponse(Customer cc) {
        String recommendedAction = predictionRepository.findLastRecommendedActionByCustomer(cc.getId()).orElse(null);
        return new CriticalCustomerResponse(
                cc.getExternalId(),
                cc.getEconomicValue(),
                cc.getPriorityScore(),
                recommendedAction
        );
    }
}
