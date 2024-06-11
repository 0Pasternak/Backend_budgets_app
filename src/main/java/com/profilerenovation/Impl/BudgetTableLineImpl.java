package com.profilerenovation.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.profilerenovation.entity.Budget;
import com.profilerenovation.entity.BudgetTableLine;
import com.profilerenovation.enums.LineType;
import com.profilerenovation.exceptions.ResourceNotFoundException;
import com.profilerenovation.repository.BudgetRepository;
import com.profilerenovation.repository.BudgetTableLineRepository;
import com.profilerenovation.repository.MaterialRepository;
import com.profilerenovation.service.IBudgetTableLineService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class BudgetTableLineImpl implements IBudgetTableLineService {

    @Autowired
    private BudgetRepository budgetRepository;

    @Autowired
    private BudgetTableLineRepository lineRepository;

    @Autowired
    private MaterialRepository materialRepository;

    @Override
    public BudgetTableLine createLine(BudgetTableLine tableLine) {
        if (tableLine.getBudget() != null && tableLine.getBudget().getId() != null) {
            Budget persistentBudget = budgetRepository.findById(tableLine.getBudget().getId())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Budget not found with id: " + tableLine.getBudget().getId()));
            tableLine.setBudget(persistentBudget);
        } else {
            throw new IllegalArgumentException("Budget object is required");
        }

        // Asumiendo que deseas que todos los campos aparte del Budget sean null, no
        // estableces ningún otro valor aquí
        return lineRepository.save(tableLine);
    }

    @Override
    public List<BudgetTableLine> findByBudgetId(Long budgetId) {
        return lineRepository.findByBudgetId(budgetId);
    }

    @Override
    public BudgetTableLine updateLine(Long lineId, BudgetTableLine lineDetails) {
        BudgetTableLine existingLine = lineRepository.findById(lineId)
                .orElseThrow(() -> new EntityNotFoundException("BudgetTableLine not found with id: " + lineId));

        // Actualiza solo los campos de total de horas y precio por hora
        existingLine.setHourPrice(lineDetails.getHourPrice());
        existingLine.setTotalHours(lineDetails.getTotalHours());

        // Guarda la línea actualizada en la base de datos
        return lineRepository.save(existingLine);
    }

    @Override
    public BudgetTableLine updateLineType(Long lineId, LineType newType) {
        BudgetTableLine line = lineRepository.findById(lineId)
                .orElseThrow(() -> new ResourceNotFoundException("Line not found with id " + lineId));
        line.setLineType(newType);
        return lineRepository.save(line);
    }

    @Override
    public void deleteLine(Long lineId) {
        BudgetTableLine line = lineRepository.findById(lineId)
                .orElseThrow(() -> new EntityNotFoundException("BudgetTableLine not found with id: " + lineId));

        // Eliminar todos los materiales vinculados
        materialRepository.deleteAll(line.getMaterials());

        // Eliminar la línea de presupuesto
        lineRepository.delete(line);
    }

}
