package com.profilerenovation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.profilerenovation.entity.BudgetTableLine;

@Repository
public interface BudgetTableLineRepository extends JpaRepository<BudgetTableLine, Long>{
	List<BudgetTableLine> findByBudgetId(Long budgetId);

}
