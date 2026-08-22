package service;

import java.util.List;

import org.springframework.stereotype.Service;

import model.Customer;
import repository.CustomerRepository;

@Service
public class CustomerService {

    private final CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    // CREATE
    public Customer createCustomer(Customer customer) {
        return repository.save(customer);
    }

    // GET ALL
    public List<Customer> getAllCustomers() {
        return repository.findAll();
    }

    // GET BY ID
    public Customer getCustomerById(Long id) {
        return repository.findById(id).orElse(null);
    }

    // UPDATE
    public Customer updateCustomer(Long id, Customer customer) {

        Customer existingCustomer = repository.findById(id).orElse(null);

        if (existingCustomer == null) {
            return null;
        }

        existingCustomer.setName(customer.getName());
        existingCustomer.setEmail(customer.getEmail());
        existingCustomer.setPhone(customer.getPhone());

        return repository.save(existingCustomer);
    }

    // DELETE
    public void deleteCustomer(Long id) {
        repository.deleteById(id);
    }
}