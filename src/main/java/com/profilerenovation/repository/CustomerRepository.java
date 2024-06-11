package com.profilerenovation.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.profilerenovation.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Query("SELECT c FROM Customer c LEFT JOIN c.budgets b GROUP BY c.id ORDER BY COUNT(b) DESC")
    List<Customer> findAllOrderByBudgetsDesc();

    @Query("SELECT c FROM Customer c LEFT JOIN c.budgets b GROUP BY c.id ORDER BY COUNT(b) ASC")
    List<Customer> findAllOrderByBudgetsAsc();

    List<Customer> findAll(Sort sort);
}
