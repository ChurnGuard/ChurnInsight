package com.team42.churninsight.customer.service;

import com.team42.churninsight.common.exception.NotFoundException;
import com.team42.churninsight.customer.dto.CriticalCustomerResponse;
import com.team42.churninsight.customer.entity.Customer;
import com.team42.churninsight.customer.repository.CustomerRepository;
import com.team42.churninsight.prediction.Prediction;
import com.team42.churninsight.prediction.api.dto.DetailsPredictionResponse;
import com.team42.churninsight.prediction.repository.PredictionRepository;
import com.team42.churninsight.prediction.service.PredictionServiceImpl;
import com.team42.churninsight.risk.entity.RiskFlag;
import com.team42.churninsight.risk.enums.FlagType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository repository;
    private final PredictionRepository predictionRepository;

    @Override
    public List<CriticalCustomerResponse> getCriticalCustomers() {
        return repository.findTop6ByOrderByPriorityScoreDesc()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public List<DetailsPredictionResponse> getPredictionsByCustomerId(String customerId) {
        Optional<Customer> customerOptional = repository.findByExternalId(customerId);
        if (customerOptional.isEmpty()){
            throw new NotFoundException("Customer not found with id: " + customerId);
        }
        Customer customer = customerOptional.get();
        return predictionRepository.findAllByCustomerOrderByCreatedAtDesc(customer).stream()
                .map(prediction -> {
                    Set<FlagType> flags = prediction.getRiskFlags()
                            .stream()
                            .map(RiskFlag::getFlagType)
                            .collect(Collectors.toSet());
                    return new DetailsPredictionResponse(
                            prediction.getCustomer().getExternalId(),
                            BigDecimal.valueOf(prediction.getProbabilityChurn()),
                            prediction.getChurn(),
                            flags,
                            prediction.getRecommendedAction(),
                            prediction.getCreatedAt());
                }).toList();
    }

    private CriticalCustomerResponse toResponse(Customer cc) {
        String recommendedAction = predictionRepository.findLastRecommendedActionByCustomer(cc.getId()).orElse(null);
        Double probabilityChurn = predictionRepository.findLastProbabilityChurnByCustomer(cc.getId()).orElse(null);
        return new CriticalCustomerResponse(
                cc.getExternalId(),
                probabilityChurn,
                cc.getEconomicValue(),
                cc.getPriorityScore(),
                recommendedAction
        );
    }
}
