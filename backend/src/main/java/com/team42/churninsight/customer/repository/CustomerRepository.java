package com.team42.churninsight.customer.repository;

import com.team42.churninsight.customer.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByCustomerId(String customerId);

    List<Customer> findAllByOrderByPriorityScoreDesc();
}
