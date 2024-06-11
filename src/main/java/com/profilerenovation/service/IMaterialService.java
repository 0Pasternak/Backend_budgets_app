package com.profilerenovation.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.profilerenovation.entity.Material;

@Service
public interface IMaterialService {

	Material addMaterial(Material material);

	List<Material> findByBudgetTableLineId(Long tableLineId);

	Material updateMaterial(Long materialId, Material materialDetails);

	void deleteMaterial(Long materialId);

}
