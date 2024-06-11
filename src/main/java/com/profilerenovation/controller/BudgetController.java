package com.profilerenovation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.profilerenovation.dto.BudgetDTO;
import com.profilerenovation.entity.Budget;
import com.profilerenovation.enums.BudgetStatus;
import com.profilerenovation.service.IBudgetService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/budget")
public class BudgetController {

	@Autowired
	private IBudgetService budgetService;

	@PostMapping("/create-budget")
	@PreAuthorize("hasRole('ROLE_ADMIN_PADRE')")
	public ResponseEntity<Budget> createBudget(@RequestBody Budget budget) {
		Budget savedBudget = budgetService.createBudget(budget);
		return new ResponseEntity<>(savedBudget, HttpStatus.CREATED);
	}

	@GetMapping("/all-budgets")
	@PreAuthorize("hasRole('ROLE_ADMIN_PADRE')")
	public ResponseEntity<List<Budget>> getAllCustomers() {
		List<Budget> budgets = budgetService.getAllBudgets();
		return new ResponseEntity<>(budgets, HttpStatus.OK);
	}

	@GetMapping("/all-budgets-detailed")
	@PreAuthorize("hasRole('ROLE_ADMIN_PADRE')")
	public ResponseEntity<List<BudgetDTO>> getAllBudgetsWithCustomerName() {
		List<BudgetDTO> budgetDetails = budgetService.getAllBudgetsWithCustomerName();
		return new ResponseEntity<>(budgetDetails, HttpStatus.OK);
	}

	@GetMapping("/filter-budgets")
	@PreAuthorize("hasRole('ROLE_ADMIN_PADRE')")
	public ResponseEntity<List<BudgetDTO>> filterBudgets(
			@RequestParam(required = false) BudgetStatus status,
			@RequestParam(required = false, defaultValue = "desc") String sortOrder) {
		List<BudgetDTO> filteredBudgets = budgetService.filterBudgets(status, sortOrder);
		return new ResponseEntity<>(filteredBudgets, HttpStatus.OK);
	}

	@PatchMapping("/update-budget/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN_PADRE')")
	public ResponseEntity<Budget> updateBudget(@PathVariable Long id, @RequestBody Budget budget) {
		Budget updatedBudget = budgetService.updateBudget(id, budget);
		return new ResponseEntity<>(updatedBudget, HttpStatus.OK);
	}
}
