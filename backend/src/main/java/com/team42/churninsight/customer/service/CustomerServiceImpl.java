package com.team42.churninsight.customer.service;

import com.team42.churninsight.customer.dto.CriticalCustomerResponse;
import com.team42.churninsight.customer.entity.Customer;
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

    private CriticalCustomerResponse toResponse(Customer cc) {
        return new CriticalCustomerResponse(
                cc.getExternalId(),
                cc.getEconomicValue(),
                cc.getPriorityScore()
        );
    }
}
