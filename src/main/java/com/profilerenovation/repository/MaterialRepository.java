package com.profilerenovation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.profilerenovation.entity.Material;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Long> {

	List<Material> findByBudgetTableLineId(Long tableLineId);

}
