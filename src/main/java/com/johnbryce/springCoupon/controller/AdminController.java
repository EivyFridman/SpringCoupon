package com.johnbryce.springCoupon.controller;

import java.util.Collection;

import javax.annotation.PostConstruct;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.johnbryce.springCoupon.beans.Company;
import com.johnbryce.springCoupon.beans.Customer;
import com.johnbryce.springCoupon.facade.AdminFacade;
import com.johnbryce.springCoupon.utils.ClientType;
import com.johnbryce.springCoupon.utils.CouponSystem;

@RestController
@RequestMapping("/admin")
public class AdminController {
	
	@PostConstruct
	public void Init() {
		
		try {
			AdminFacade admin = (AdminFacade)CouponSystem.login("admin", "1234", ClientType.ADMIN);
			admin.createCompany(new Company("comapny","company","Company@com.com"));
			admin.createCompany(new Company("coca-cola","1234","main@cocacola.com"));
			admin.createCompany(new Company("pepsi","5678","costomers@pepsi.com"));
		} catch (Exception e) {
			System.out.println("Failed to init admin ...");
		}
		
	}
	
	@PostMapping("/createCompany")
	public ResponseEntity<?> createCompany(Company company)  {
		try {
			AdminFacade admin = (AdminFacade)CouponSystem.login("admin", "1234", ClientType.ADMIN);
			return new ResponseEntity<Company> (admin.createCompany(company),HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String> ("Failed to create a company account: "+e.getMessage(),HttpStatus.PRECONDITION_REQUIRED); 
		}
	}


	@DeleteMapping("/removeCompany/{companyId}")
	public ResponseEntity<String> removeCompany(@PathVariable long id){
		//AdminFacad admin = getFacade();
		try {
			AdminFacade admin = (AdminFacade)CouponSystem.login("admin", "1234", ClientType.ADMIN);
			Company company = admin.getCompany(id);
			if (company != null) {
				admin.removeCompany(company);
				return new ResponseEntity<String> ("Succeded to remove a company: name = " + company.getName() + ", id = " + id,HttpStatus.OK);
			} else {
				return new ResponseEntity<String> ("Failed to remove a company: the provided company id is invalid",HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<String> ("Failed to remove a company: " + e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@PostMapping("/updateCompany")
	public ResponseEntity<?> updateCompany(Company newCompany) {
		
		//AdminFacad admin = getFacade();
		try {
			AdminFacade admin = (AdminFacade)CouponSystem.login("admin", "1234", ClientType.ADMIN);
			Company oldCompany = admin.getCompany(newCompany.getId());
			if (oldCompany != null) {
				newCompany = admin.updateCompany(oldCompany, newCompany.getPassword(), newCompany.getEmail());
				return new ResponseEntity<Company> (newCompany,HttpStatus.OK);
				} else {
					return new ResponseEntity<String> ("Failed to Update a company: the provided company id is invalid",HttpStatus.BAD_REQUEST);
			}
		} catch ( Exception e) {
			return new ResponseEntity<String> ("Failed to update a company: " + e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/getAllCompanies")
	public ResponseEntity<?> getAllCompanies() {
		try {
			AdminFacade admin = (AdminFacade)CouponSystem.login("admin", "1234", ClientType.ADMIN);
			return new ResponseEntity<Collection<Company>> (admin.getAllCompanies(),HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String> ("Failed to get all companies: " + e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		

	}

	@GetMapping("/getCompany/{companyId}")
	public ResponseEntity<?> getCompany(@PathVariable long id) {
		//AdminFacad admin = getFacade();
		try {
			AdminFacade admin = (AdminFacade)CouponSystem.login("admin", "1234", ClientType.ADMIN);
			Company company = admin.getCompany(id);
			if (company != null) {
				return new ResponseEntity<Company> (company,HttpStatus.OK);
				} else {
					return new ResponseEntity<String> ("Failed get the company datails : the provided company id is invalid",HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<String> ("Failed to get the company: " + e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/createCustomer")
	public ResponseEntity<?> Customer(Customer customer) {
		try {
			AdminFacade admin = (AdminFacade)CouponSystem.login("admin", "1234", ClientType.ADMIN);
			return new ResponseEntity<Customer> (admin.createCustomer(customer),HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String> ("Failed to create a customer account: "+e.getMessage(),HttpStatus.PRECONDITION_REQUIRED); 
		}
	}
	
	@DeleteMapping("/removeCustomer/{customerId}")
	public ResponseEntity<String> removeCustomer(@PathVariable long id){
		
		//AdminFacad admin = getFacade();
		try {
			AdminFacade admin = (AdminFacade)CouponSystem.login("admin", "1234", ClientType.ADMIN);
			Customer customer = admin.getCustomer(id);
			if (customer != null) {
				admin.removeCustomer(customer);
				return new ResponseEntity<String> ("Succeded to remove the customer: name = " + customer.getName() + ", id = " + id,HttpStatus.OK);
			} else {
				return new ResponseEntity<String> ("Failed toremove the customer: the provided customer id is invalid",HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<String> ("Failed to remove the customer: " + e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@PostMapping("/updateCustomer")
	public ResponseEntity<?> updateCustomer(Customer newCustomer){
		//AdminFacad admin = getFacade();
		try {
			AdminFacade admin = (AdminFacade)CouponSystem.login("admin", "1234", ClientType.ADMIN);
			Customer oldCustomer = admin.getCustomer(newCustomer.getId());		
			if (oldCustomer != null) {
				newCustomer = admin.updateCustomer(oldCustomer, newCustomer.getPassword());
				return new ResponseEntity<Customer> (newCustomer,HttpStatus.OK);
			} else {
				return new ResponseEntity<String> ("Failed to Update a customer: the provided customer id is invalid",HttpStatus.BAD_REQUEST);
		}
	} catch ( Exception e) {
		return new ResponseEntity<String> ("Failed to update a customer: " + e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
	}
	}
	
	@GetMapping("/getAllCustomers")
	public ResponseEntity<?> getAllCustomers() {
		
		//AdminFacad admin = getFacade();
		try {
			AdminFacade admin = (AdminFacade)CouponSystem.login("admin", "1234", ClientType.ADMIN);
			return new ResponseEntity<Collection<Customer>> (admin.getAllCustomers(),HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<String> ("Failed to get all customers: " + e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
	@GetMapping("/getCustomer/{customerId}")
	public ResponseEntity<?> getCustomer(@PathVariable long id){
		//AdminFacad admin = getFacade();
		try {
			AdminFacade admin = (AdminFacade)CouponSystem.login("admin", "1234", ClientType.ADMIN);
			Customer customer = admin.getCustomer(id);
			if (customer != null) {
				return new ResponseEntity<Customer> (customer,HttpStatus.OK);
			} else {
				return new ResponseEntity<String> ("Failed get the customer datails : the provided customer id is invalid",HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<String> ("Failed to get the customer: " + e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
