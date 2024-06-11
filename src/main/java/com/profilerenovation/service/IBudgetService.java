package com.profilerenovation.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.profilerenovation.dto.BudgetDTO;
import com.profilerenovation.entity.Budget;
import com.profilerenovation.enums.BudgetStatus;

@Service
public interface IBudgetService {

	Budget createBudget(Budget budget);

	List<Budget> getAllBudgets();

	List<BudgetDTO> getAllBudgetsWithCustomerName();

	List<BudgetDTO> filterBudgets(BudgetStatus status, String sortOrder);

	Budget updateBudget(Long id, Budget budget);

}
