package Service;

import model.Customer;

import java.util.*;

public class CustomerService {
    //creating static reference
    private static CustomerService service;

    private final Map<String, Customer> cust;

    private CustomerService(){
        this.cust=new HashMap<>();
    }

    public static CustomerService getInstance(){
        if (service==null){
     service=new CustomerService();
        }
        return service;
    }

    public void addCustomer(String email, String firstName, String lastName){
       if(cust.containsKey(email)){
           throw new IllegalArgumentException(" Error:- your email is already registered!");
       }
       Customer newCustomer=new Customer(firstName, lastName, email);
       cust.put(email, newCustomer);
    }

    public Customer getCustomer(String customerEmail){
        return cust.get(customerEmail);
    }

    public Collection<Customer> getAllCustomers(){
        return cust.values();
    }

}
