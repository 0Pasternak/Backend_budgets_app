package com.profilerenovation.entity;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "t_materials")
public class Material {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaterialID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "BudgetTableLineId", nullable = false)
    @JsonBackReference
    private BudgetTableLine budgetTableLine;

    @Column(name = "MaterialName", length = 100, nullable = true)
    private String materialName;

    @Column(name = "UnitPrice", precision = 10, scale = 2, nullable = true)
    private BigDecimal unitPrice;
    
    @Column(name = "SizeUnitMaterial", precision = 10, scale = 2, nullable = true)
    private BigDecimal sizeUnitMaterial;

    @Column(name = "Quantity")
    private Integer quantity;
    
    @Column(name = "TotalPrice", nullable = true)
    private BigDecimal totalPrice;

    @Column(name = "AreaToCover", precision = 10, scale = 2, nullable = true)
    private BigDecimal areaToCover;

	public Material() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getMaterialId() {
		return id;
	}

	public void setMaterialId(Long materialId) {
		this.id = materialId;
	}

	public BudgetTableLine getBudgetTableLine() {
		return budgetTableLine;
	}

	public void setBudgetTableLine(BudgetTableLine budgetTableLine) {
		this.budgetTableLine = budgetTableLine;
	}

	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	public BigDecimal getSizeUnitMaterial() {
		return sizeUnitMaterial;
	}

	public void setSizeUnitMaterial(BigDecimal sizeUnitMaterial) {
		this.sizeUnitMaterial = sizeUnitMaterial;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getAreaToCover() {
		return areaToCover;
	}

	public void setAreaToCover(BigDecimal areaToCover) {
		this.areaToCover = areaToCover;
	}
	

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	@Override
	public String toString() {
		return "Material [materialId=" + id + ", budgetTableLine=" + budgetTableLine + ", materialName="
				+ materialName + ", unitPrice=" + unitPrice + ", sizeUnitMaterial=" + sizeUnitMaterial + ", quantity="
				+ quantity + ", totalPrice=" + totalPrice + ", areaToCover=" + areaToCover + "]";
	}
	
	
    
    

    

}
