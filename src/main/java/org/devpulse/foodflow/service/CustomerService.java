package org.devpulse.foodflow.service;

import org.devpulse.foodflow.exceptions.InvalidCustomerException;
import org.devpulse.foodflow.model.dtos.CustomerRegisterDto;
import org.devpulse.foodflow.repository.CustomerRepository;


public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public void registerCustomer(CustomerRegisterDto newCustomer) throws InvalidCustomerException {
        if (newCustomer == null ||
                newCustomer.getName() == null ||
                newCustomer.getLastname() == null) {
            throw new InvalidCustomerException("El nombre y apellido son obligatorios");
        }
        try {
            customerRepository.saveCustomer(newCustomer);
        } catch (Exception e) {
            throw new RuntimeException("Ocurrió un error inesperado al registrar el cliente", e);
        }
    }

}
