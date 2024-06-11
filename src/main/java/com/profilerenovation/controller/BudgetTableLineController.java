package com.profilerenovation.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.profilerenovation.entity.BudgetTableLine;
import com.profilerenovation.enums.LineType;
import com.profilerenovation.service.IBudgetTableLineService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/budget")
public class BudgetTableLineController {

	@Autowired
	private IBudgetTableLineService tableLineService;

	@PostMapping("/new-line")
	@PreAuthorize("hasRole('ROLE_ADMIN_PADRE')")
	public ResponseEntity<BudgetTableLine> createTableLine(@RequestBody BudgetTableLine tableLine) {
		BudgetTableLine createLine = tableLineService.createLine(tableLine);
		return new ResponseEntity<>(createLine, HttpStatus.CREATED);
	}

	@GetMapping("/lines/{budgetId}")
	@PreAuthorize("hasRole('ROLE_ADMIN_PADRE')")
	public ResponseEntity<List<BudgetTableLine>> findLinesByBudgetId(@PathVariable Long budgetId) {
		List<BudgetTableLine> lines = tableLineService.findByBudgetId(budgetId);
		return new ResponseEntity<>(lines, HttpStatus.OK);
	}

	@PutMapping("/line/{lineId}")
	@PreAuthorize("hasRole('ROLE_ADMIN_PADRE')")
	public ResponseEntity<BudgetTableLine> updateLine(@PathVariable Long lineId,
			@RequestBody BudgetTableLine lineDetails) {
		System.out.println("informacion recibida para actulizar la linea: " + lineDetails);
		BudgetTableLine updateLine = tableLineService.updateLine(lineId, lineDetails);
		return new ResponseEntity<>(updateLine, HttpStatus.OK);
	}

	@PutMapping("/line/{lineId}/update-type")
	@PreAuthorize("hasRole('ROLE_ADMIN_PADRE')")
	public ResponseEntity<BudgetTableLine> updateLineType(@PathVariable Long lineId,
			@RequestBody Map<String, String> payload) {
		String lineType = payload.get("lineType");
		LineType newType;
		try {
			newType = LineType.valueOf(lineType.toUpperCase());
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		BudgetTableLine updatedLine = tableLineService.updateLineType(lineId, newType);
		return new ResponseEntity<>(updatedLine, HttpStatus.OK);
	}

	@DeleteMapping("/line/{lineId}")
	@PreAuthorize("hasRole('ROLE_ADMIN_PADRE')")
	public ResponseEntity<Void> deleteLine(@PathVariable Long lineId) {
		tableLineService.deleteLine(lineId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
