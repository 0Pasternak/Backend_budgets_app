package com.profilerenovation.entity;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.profilerenovation.enums.BudgetStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "t_budgets")
public class Budget {

	// Properties

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "BudgetId")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "CustomerId", nullable = false)
	@JsonBackReference
	private Customer customer;

	@Column(name = "BudgetName", nullable = false, length = 255)
	private String budgetName;

	@Column(name = "CreationDate", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date creationDate;

	@Column(name = "EndDate")
	@Temporal(TemporalType.DATE)
	private Date endDate;

	@OneToMany(mappedBy = "budget", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
	private List<BudgetTableLine> budgetTableLine;

	@Enumerated(EnumType.STRING)
	@Column(name = "Status", nullable = false)
	private BudgetStatus status = BudgetStatus.PENDIENTE;

	@Column(name = "Anotaciones", columnDefinition = "TEXT")
	private String anotaciones;

	// Constructors

	public Budget() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getBudgetName() {
		return budgetName;
	}

	public void setBudgetName(String budgetName) {
		this.budgetName = budgetName;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public List<BudgetTableLine> getBudgetTableLine() {
		return budgetTableLine;
	}

	public void setBudgetTableLine(List<BudgetTableLine> budgetTableLine) {
		this.budgetTableLine = budgetTableLine;
	}

	public BudgetStatus getStatus() {
		return status;
	}

	public void setStatus(BudgetStatus status) {
		this.status = status;
	}

	public String getAnotaciones() {
		return anotaciones;
	}

	public void setAnotaciones(String anotaciones) {
		this.anotaciones = anotaciones;
	}

	@Override
	public String toString() {
		String budgetTableLineString = budgetTableLine == null ? "null"
				: budgetTableLine.stream()
						.map(Object::toString)
						.collect(Collectors.joining(", "));

		return "Budget{" +
				"id=" + id +
				", customer=" + (customer != null ? customer.toString() : "null") +
				", budgetName='" + budgetName + '\'' +
				", creationDate=" + creationDate +
				", endDate=" + endDate +
				", status=" + status +
				", anotaciones='" + anotaciones + '\'' +
				", budgetTableLine=[" + budgetTableLineString + "]" +
				'}';
	}
}
