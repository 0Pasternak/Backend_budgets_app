package com.profilerenovation.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.profilerenovation.entity.BudgetTableLine;
import com.profilerenovation.enums.LineType;

@Service
public interface IBudgetTableLineService {

	BudgetTableLine createLine(BudgetTableLine tableLine);

	List<BudgetTableLine> findByBudgetId(Long budgetId);

	BudgetTableLine updateLine(Long lineId, BudgetTableLine lineDetails);

	BudgetTableLine updateLineType(Long lineId, LineType newType);

	void deleteLine(Long lineId);

}
