package com.team42.churninsight.customer.service;

import com.team42.churninsight.customer.dto.CriticalCustomerResponse;
import com.team42.churninsight.customer.entity.CustomerCritical;
import com.team42.churninsight.customer.repository.CustomerCriticalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerCriticalRepository repository;

    @Override
    public List<CriticalCustomerResponse> getCriticalCustomers() {
        return repository.findAllByOrderByPriorityScoreDesc()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private CriticalCustomerResponse toResponse(CustomerCritical cc) {
        return new CriticalCustomerResponse(
                cc.getCustomerId(),
                cc.getChurnProbability(),
                cc.getEconomicValue(),
                cc.getPriorityScore(),
                cc.getRecommendedAction()
        );
    }
}
