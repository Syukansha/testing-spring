package com.testing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@SpringBootApplication
@RestController
@RequestMapping("api/v1/customers")
public class Main {

    private final CustomerRepository customerRepository;

    public Main(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }


    public static void main(String[] args){
        SpringApplication.run(Main.class, args);
    }

    @GetMapping
    public List<Customer> getCustomers(){
//        return List.of();
        return customerRepository.findAll();
    }

    @GetMapping("search/{customerId}")
    public Optional<Customer> selectCustomerById(@PathVariable("customerId") Integer id){
//        return List.of();
        return customerRepository.findById(id);
    }



    record NewCustomerRequest(
            String name,
            String email,
            Integer age
    ){
    }

    @PostMapping("add-customer")
    public void addCustomer(@RequestBody NewCustomerRequest request){
        Customer customer = new Customer();
        customer.setName(request.name());
        customer.setEmail(request.email());
        customer.setAge(request.age());
        customerRepository.save(customer);
    }

    @DeleteMapping("{customerId}")
    public void deleteCustomer(@PathVariable("customerId") Integer id){
        customerRepository.deleteById(id);
    }



    @PutMapping("{customerId}")
    public Customer updateCustomer(@PathVariable("customerId") Integer id, @RequestBody NewCustomerRequest request){
        Customer customer = customerRepository.findById(id).get();

        customer.setName(request.name());
        customer.setEmail(request.email());
        customer.setAge(request.age());
        return customerRepository.save(customer);

    }
}
