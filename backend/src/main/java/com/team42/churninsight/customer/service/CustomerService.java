package com.team42.churninsight.customer.service;

import com.team42.churninsight.customer.dto.CriticalCustomerResponse;

import java.util.List;

public interface CustomerService {
    List<CriticalCustomerResponse> getCriticalCustomers();
}
