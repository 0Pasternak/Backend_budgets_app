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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.profilerenovation.dto.CustomerDTO;
import com.profilerenovation.entity.Customer;
import com.profilerenovation.service.ICustomerService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/customer")
public class CustomerController {

	@Autowired
	private ICustomerService customerService;

	@PostMapping("/create-customer")
	@PreAuthorize("hasRole('ROLE_ADMIN_PADRE')")
	public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
		Customer savedCustomer = customerService.createCustomer(customer);
		return new ResponseEntity<>(savedCustomer, HttpStatus.CREATED);
	}

	@PutMapping("/update/{customerId}")
	@PreAuthorize("hasRole('ROLE_ADMIN_PADRE')")
	public ResponseEntity<Customer> updateCustomer(@PathVariable Long customerId,
			@RequestBody Customer customerDetails) {
		Customer updatedCustomer = customerService.updateCustomer(customerId, customerDetails);
		return ResponseEntity.ok(updatedCustomer);
	}

	@DeleteMapping("/delete-customer/{customerId}")
	@PreAuthorize("hasRole('ROLE_ADMIN_PADRE')")
	public ResponseEntity<Void> deleteCustomer(@PathVariable("customerId") Long customerId) {
		customerService.deleteCustomer(customerId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/all-customers-dto")
	@PreAuthorize("hasRole('ROLE_ADMIN_PADRE')")
	public ResponseEntity<List<CustomerDTO>> getAllUsers() {
		List<CustomerDTO> customers = customerService.getAllCustomersDTO();
		return ResponseEntity.ok(customers);
	}

	// * Este get all devuelve todos los datos customer -> budget -> budgetLine ->
	// * Material;
	@GetMapping("/all-customers")
	@PreAuthorize("hasRole('ROLE_ADMIN_PADRE')")
	public ResponseEntity<List<Customer>> getAllCustomers() {
		List<Customer> customers = customerService.getAllCustomers();
		return new ResponseEntity<>(customers, HttpStatus.OK);
	}

	@GetMapping("/all-customers-sorted")
	@PreAuthorize("hasRole('ROLE_ADMIN_PADRE')")
	public ResponseEntity<List<CustomerDTO>> getAllCustomersSorted(
			@RequestParam String sortBy,
			@RequestParam String order) {
		List<CustomerDTO> customers = customerService.getAllCustomersSorted(sortBy, order);
		return ResponseEntity.ok(customers);
	}

}
