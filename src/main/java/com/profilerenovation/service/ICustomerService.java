package com.profilerenovation.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.profilerenovation.dto.CustomerDTO;
import com.profilerenovation.entity.Customer;

@Service
public interface ICustomerService {

	Customer createCustomer(Customer customer);

	List<Customer> getAllCustomers();

	void deleteCustomer(Long customerId);

	Customer updateCustomer(Long customerId, Customer customerDetails);

	List<CustomerDTO> getAllCustomersDTO();

	List<CustomerDTO> getAllCustomersSorted(String sortBy, String order);

}
