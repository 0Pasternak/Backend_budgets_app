package com.profilerenovation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.profilerenovation.entity.Budget;
import com.profilerenovation.enums.BudgetStatus;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, Long> {
    List<Budget> findByStatus(BudgetStatus status);

    List<Budget> findAllByOrderByIdAsc();

    List<Budget> findAllByOrderByIdDesc();

}
