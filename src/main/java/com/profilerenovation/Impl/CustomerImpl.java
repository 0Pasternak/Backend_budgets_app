package com.profilerenovation.Impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.profilerenovation.dto.CustomerDTO;
import com.profilerenovation.entity.Customer;
import com.profilerenovation.exceptions.ResourceNotFoundException;
import com.profilerenovation.repository.CustomerRepository;
import com.profilerenovation.service.ICustomerService;

@Service
public class CustomerImpl implements ICustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	public Customer createCustomer(Customer customer) {
		return customerRepository.save(customer);
	}

	public List<Customer> getAllCustomers() {
		return customerRepository.findAll();
	}

	@Override
	public void deleteCustomer(Long customerId) {
		Customer customer = customerRepository.findById(customerId)
				.orElseThrow(() -> new ResourceNotFoundException("Customer not found with id " + customerId));
		customerRepository.delete(customer);
	}

	@Override
	public Customer updateCustomer(Long customerId, Customer customerDetails) {
		return customerRepository.findById(customerId).map(customer -> {
			customer.setFirstName(customerDetails.getFirstName());
			customer.setLastName(customerDetails.getLastName());
			customer.setPhone(customerDetails.getPhone());
			customer.setPhone2(customerDetails.getPhone2());
			customer.setEmail(customerDetails.getEmail());
			customer.setAddress(customerDetails.getAddress());
			return customerRepository.save(customer);
		}).orElseThrow(() -> new ResourceNotFoundException("Customer not found with id " + customerId));
	}

	@Override
	public List<CustomerDTO> getAllCustomersDTO() {
		return customerRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
	}

	private CustomerDTO convertToDTO(Customer customer) {
		CustomerDTO dto = new CustomerDTO();
		dto.setId(customer.getId());
		dto.setFirstName(customer.getFirstName());
		dto.setLastName(customer.getLastName());
		dto.setPhone(customer.getPhone());
		dto.setPhone2(customer.getPhone2());
		dto.setEmail(customer.getEmail());
		dto.setAddress(customer.getAddress());
		return dto;
	}

	@Override
	public List<CustomerDTO> getAllCustomersSorted(String sortBy, String order) {
		List<Customer> customers;
		if ("budgets".equalsIgnoreCase(sortBy)) {
			if ("asc".equalsIgnoreCase(order)) {
				customers = customerRepository.findAllOrderByBudgetsAsc();
			} else {
				customers = customerRepository.findAllOrderByBudgetsDesc();
			}
		} else {
			Sort sort = order.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
					: Sort.by(sortBy).descending();
			customers = customerRepository.findAll(sort);
		}
		return customers.stream().map(this::convertToDTO).collect(Collectors.toList());
	}
}
