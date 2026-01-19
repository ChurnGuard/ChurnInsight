package com.team42.churninsight.customer.api;

import com.team42.churninsight.customer.dto.CriticalCustomerResponse;
import com.team42.churninsight.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;




@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/critical")
    public List<CriticalCustomerResponse> getCriticalCustomers() {
        return customerService.getCriticalCustomers();
    }
}
