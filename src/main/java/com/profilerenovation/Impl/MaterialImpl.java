package com.profilerenovation.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.profilerenovation.entity.BudgetTableLine;
import com.profilerenovation.entity.Material;
import com.profilerenovation.repository.BudgetTableLineRepository;
import com.profilerenovation.repository.MaterialRepository;
import com.profilerenovation.service.IMaterialService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class MaterialImpl implements IMaterialService {

	@Autowired
	private MaterialRepository materialRepository;

	@Autowired
	private BudgetTableLineRepository lineRepository;

	@Override
	public Material addMaterial(Material material) {
		if (material.getBudgetTableLine() != null && material.getBudgetTableLine().getBudgetTableLineId() != null) {
			BudgetTableLine persistentLine = lineRepository
					.findById(material.getBudgetTableLine().getBudgetTableLineId())
					.orElseThrow(() -> new EntityNotFoundException(
							"Budget not found with id: " + material.getBudgetTableLine().getBudgetTableLineId()));
			material.setBudgetTableLine(persistentLine); // Vincular el material a la línea de presupuesto encontrada
		} else {
			throw new IllegalArgumentException("BudgetTableLine object with ID is required");
		}

		return materialRepository.save(material);
	}

	@Override
	public List<Material> findByBudgetTableLineId(Long tableLineId) {
		return materialRepository.findByBudgetTableLineId(tableLineId);
	}
	
	@Override
    public Material updateMaterial(Long materialId, Material materialDetails) {
        Material material = materialRepository.findById(materialId)
            .orElseThrow(() -> new EntityNotFoundException("Material not found with id: " + materialId));

        material.setMaterialName(materialDetails.getMaterialName());
        material.setUnitPrice(materialDetails.getUnitPrice());
        material.setSizeUnitMaterial(materialDetails.getSizeUnitMaterial());
        material.setQuantity(materialDetails.getQuantity());
        material.setAreaToCover(materialDetails.getAreaToCover());
        material.setTotalPrice(materialDetails.getTotalPrice());
        return materialRepository.save(material);
    }

	@Override
	public void deleteMaterial(Long materialId) {
	    materialRepository.deleteById(materialId);
	}

}
