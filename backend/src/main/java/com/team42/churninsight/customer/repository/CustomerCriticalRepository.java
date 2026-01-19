package com.team42.churninsight.customer.repository;

import com.team42.churninsight.customer.entity.CustomerCritical;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerCriticalRepository extends JpaRepository<CustomerCritical, Long> {

    Optional<CustomerCritical> findByCustomerId(String customerId);

    List<CustomerCritical> findAllByOrderByPriorityScoreDesc();
}
