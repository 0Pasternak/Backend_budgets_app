package com.profilerenovation.Impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.profilerenovation.dto.BudgetDTO;
import com.profilerenovation.entity.Budget;
import com.profilerenovation.entity.Customer;
import com.profilerenovation.enums.BudgetStatus;
import com.profilerenovation.repository.BudgetRepository;
import com.profilerenovation.repository.CustomerRepository;
import com.profilerenovation.service.IBudgetService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class BudgetImpl implements IBudgetService {

	@Autowired
	private BudgetRepository budgetRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@Override
	public Budget createBudget(Budget budget) {
		if (budget.getCustomer() != null && budget.getCustomer().getId() != null) {
			Customer persistentCustomer = customerRepository.findById(budget.getCustomer().getId())
					.orElseThrow(() -> new EntityNotFoundException("Customer not found"));
			budget.setCustomer(persistentCustomer);
		}
		return budgetRepository.save(budget);
	}

	@Override
	public List<Budget> getAllBudgets() {
		return budgetRepository.findAll();
	}

	@Override
	public List<BudgetDTO> getAllBudgetsWithCustomerName() {
		return budgetRepository.findAll().stream().map(budget -> {
			BudgetDTO dto = new BudgetDTO();
			dto.setId(budget.getId());
			dto.setBudgetName(budget.getBudgetName());
			dto.setCreationDate(budget.getCreationDate());
			dto.setEndDate(budget.getEndDate());
			dto.setCustomerName(budget.getCustomer().getFirstName());
			dto.setCustomerLastName(budget.getCustomer().getLastName());
			dto.setStatus(budget.getStatus().toString());
			dto.setAnotaciones(budget.getAnotaciones());
			return dto;
		}).collect(Collectors.toList());
	}

	@Override
	public List<BudgetDTO> filterBudgets(BudgetStatus status, String sortOrder) {
		List<Budget> budgets;
		if (status != null) {
			budgets = budgetRepository.findByStatus(status);
		} else {
			if ("asc".equalsIgnoreCase(sortOrder)) {
				budgets = budgetRepository.findAllByOrderByIdAsc();
			} else {
				budgets = budgetRepository.findAllByOrderByIdDesc();
			}
		}

		return budgets.stream().map(budget -> {
			BudgetDTO dto = new BudgetDTO();
			dto.setId(budget.getId());
			dto.setBudgetName(budget.getBudgetName());
			dto.setCreationDate(budget.getCreationDate());
			dto.setEndDate(budget.getEndDate());
			dto.setCustomerName(budget.getCustomer().getFirstName());
			dto.setCustomerLastName(budget.getCustomer().getLastName());
			dto.setStatus(budget.getStatus().toString());
			dto.setAnotaciones(budget.getAnotaciones());
			return dto;
		}).collect(Collectors.toList());
	}

	@Override
	public Budget updateBudget(Long id, Budget budget) {
		Budget existingBudget = budgetRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Budget not found"));
		existingBudget.setBudgetName(budget.getBudgetName());
		existingBudget.setCreationDate(budget.getCreationDate());
		existingBudget.setEndDate(budget.getEndDate());
		existingBudget.setStatus(budget.getStatus());
		existingBudget.setAnotaciones(budget.getAnotaciones());

		if (budget.getCustomer() != null && budget.getCustomer().getId() != null) {
			Customer persistentCustomer = customerRepository.findById(budget.getCustomer().getId())
					.orElseThrow(() -> new EntityNotFoundException("Customer not found"));
			existingBudget.setCustomer(persistentCustomer);
		}

		return budgetRepository.save(existingBudget);
	}
}
