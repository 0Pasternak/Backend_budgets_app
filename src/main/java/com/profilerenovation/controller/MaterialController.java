package com.profilerenovation.controller;

import java.util.List;

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

import com.profilerenovation.entity.Material;
import com.profilerenovation.service.IMaterialService;

@CrossOrigin(origins = { "http://localhost:3000", "https://tpreformas.es" })
@RestController
@RequestMapping("/api/budget")
public class MaterialController {

	@Autowired
	private IMaterialService materialService;

	@PostMapping("/add-material")
	@PreAuthorize("hasRole('ROLE_ADMIN_PADRE')")
	public ResponseEntity<Material> addMaterial(@RequestBody Material material) {
		Material addMaterial = materialService.addMaterial(material);
		return new ResponseEntity<>(addMaterial, HttpStatus.CREATED);
	}

	@GetMapping("/material/{lineId}")
	@PreAuthorize("hasRole('ROLE_ADMIN_PADRE')")
	public ResponseEntity<List<Material>> findMaterialsByTableLineId(@PathVariable("lineId") Long tableLineId) {
		List<Material> materials = materialService.findByBudgetTableLineId(tableLineId);
		return new ResponseEntity<>(materials, HttpStatus.OK);
	}

	@PutMapping("/material/{materialId}")
	@PreAuthorize("hasRole('ROLE_ADMIN_PADRE')")
	public ResponseEntity<Material> updateMaterial(@PathVariable Long materialId,
			@RequestBody Material materialDetails) {
		Material updatedMaterial = materialService.updateMaterial(materialId, materialDetails);
		return new ResponseEntity<>(updatedMaterial, HttpStatus.OK);
	}

	@DeleteMapping("/material/delete-material/{materialId}")
	@PreAuthorize("hasRole('ROLE_ADMIN_PADRE')")
	public ResponseEntity<Void> deleteMaterial(@PathVariable Long materialId) {
		materialService.deleteMaterial(materialId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
