package com.profilerenovation.entity;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.profilerenovation.enums.LineType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "t_budget_table_lines")
public class BudgetTableLine {

	// Propieties:

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "BudgetTableLineId")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BudgetID", nullable = false)
	@JsonBackReference
	private Budget budget;

	@Column(name = "LineName", nullable = true) // Permitir null
	private String lineName;

	@Column(name = "UnitPrice", nullable = true) // Permitir null
	private BigDecimal unitPrice;

	@Column(name = "RquiredUnits", nullable = true) // Permitir null
	private Integer requiredUnits;

	@Column(name = "TotalPrice", nullable = true) // Permitir null
	private BigDecimal totalPrice;

	@Column(name = "TotalHours", nullable = true) // Permitir null
	private Integer totalHours; // Cambiado a Integer para permitir null

	@Column(name = "HourPrice", nullable = true) // Permitir null
	private BigDecimal hourPrice;

	@OneToMany(mappedBy = "budgetTableLine", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
	private List<Material> materials;

	@Enumerated(EnumType.STRING)
	@Column(name = "LineType", nullable = true) // El campo puede ser nulo
	private LineType lineType;

	// Constructor:

	public BudgetTableLine() {
		super();
	}

	public Long getBudgetTableLineId() {
		return id;
	}

	public void setBudgetTableLineId(Long id) {
		this.id = id;
	}

	public Budget getBudget() {
		return budget;
	}

	public void setBudget(Budget budget) {
		this.budget = budget;
	}

	public String getLineName() {
		return lineName;
	}

	public void setLineName(String lineName) {
		this.lineName = lineName;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Integer getRequiredUnits() {
		return requiredUnits;
	}

	public void setRequiredUnits(Integer requiredUnits) {
		this.requiredUnits = requiredUnits;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	public List<Material> getMaterials() {
		return materials;
	}

	public void setMaterials(List<Material> materials) {
		this.materials = materials;
	}

	public LineType getLineType() {
		return lineType;
	}

	public void setLineType(LineType lineType) {
		this.lineType = lineType;
	}

	public Integer getTotalHours() {
		return totalHours;
	}

	public void setTotalHours(Integer totalHours) {
		this.totalHours = totalHours;
	}

	public BigDecimal getHourPrice() {
		return hourPrice;
	}

	public void setHourPrice(BigDecimal hourPrice) {
		this.hourPrice = hourPrice;
	}

}
