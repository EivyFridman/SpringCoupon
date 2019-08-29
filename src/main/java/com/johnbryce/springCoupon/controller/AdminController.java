package com.johnbryce.springCoupon.controller;

import java.util.Collection;

import javax.annotation.PostConstruct;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.johnbryce.springCoupon.beans.Company;
import com.johnbryce.springCoupon.exception.CouponException;
import com.johnbryce.springCoupon.facad.AdminFacad;
import com.johnbryce.springCoupon.utils.ClientType;
import com.johnbryce.springCoupon.utils.CouponSystem;

@RestController
@RequestMapping("/admin/")
public class AdminController {
	
	@PostConstruct
	public void Init() {
		
		try {
			AdminFacad admin = (AdminFacad)CouponSystem.login("admin", "1234", ClientType.ADMIN);
			admin.createCompany(new Company("comapny","company","Company@com.com"));
			admin.createCompany(new Company("coca-cola","1234","main@cocacola.com"));
			admin.createCompany(new Company("pepsi","5678","costomers@pepsi.com"));
		} catch (Exception e) {
			System.out.println("Failed to init ...");
		}
		
	}
	
	@PostMapping("/createCompany")
	public ResponseEntity<?> createCompany(Company company)  {
		try {
			AdminFacad admin = (AdminFacad)CouponSystem.login("admin", "1234", ClientType.ADMIN);
			return new ResponseEntity<Company> (admin.createCompany(company),HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String> (e.getMessage(),HttpStatus.PRECONDITION_REQUIRED); 
		}
	}


	@DeleteMapping("/removeCompany/{companyId}")
	public ResponseEntity<String> removeCompany(@PathVariable long id) throws Exception {
		AdminFacad admin = (AdminFacad)CouponSystem.login("admin", "1234", ClientType.ADMIN);
		//AdminFacad admin = getFacade();
		try {
			Company company = admin.getCompany(id);
			if (company != null) {
				admin.removeCompany(company);
				return new ResponseEntity<String> ("Succeded to remove a company: name = " + company.getName() + ", id = " + id,HttpStatus.OK);
			} else {
				return new ResponseEntity<String> ("Failed to remove a company: the provided company id is invalid",HttpStatus.BAD_REQUEST);
			}
		} catch (CouponException e) {
			return new ResponseEntity<String> ("Failed to remove a company: " + e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@PostMapping("/updateCompany")
	public ResponseEntity<?> updateCompany(Company newCompany) {
		
		//AdminFacad admin = getFacade();
		try {
			AdminFacad admin = (AdminFacad)CouponSystem.login("admin", "1234", ClientType.ADMIN);
			Company oldCompany = admin.getCompany(newCompany.getId());
			if (oldCompany != null) {
				newCompany = admin.updateCompany(oldCompany, newCompany.getPassword(), newCompany.getEmail());
				return new ResponseEntity<Company> (admin.createCompany(newCompany),HttpStatus.OK);
				} else {
					return new ResponseEntity<String> ("Failed to Update a company: the provided company id is invalid",HttpStatus.BAD_REQUEST);
			}
		} catch ( Exception e) {
			return new ResponseEntity<String> ("Failed to update a company: " + e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/getAllCompanies")
	public Collection<Company> getAllCompanies() {
		try {
			AdminFacad admin = (AdminFacad)CouponSystem.login("admin", "1234", ClientType.ADMIN);
			return admin.getAllCompanies();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

//	@GET
//	@Path("getCompany/{companyId}")
//	@Produces(MediaType.APPLICATION_JSON)
//	public String getCompany(@PathParam("companyId") long id) throws Exception {
//		System.out.println("the company is");
//		AdminFacad admin = (AdminFacad)CouponSystem.login("admin", "1234", ClientType.ADMIN);
//		//AdminFacad admin = getFacade();
//		try {
//			System.out.println("the company is");
//			Company company = admin.getCompany(id);
//			System.out.println(company);
//			if (company != null) {
//				return new Gson().toJson(company);
//			} else {
//				return null;
//			}
//		} catch (CouponException e) {
//			System.err.println("get company by id failed " + e.getMessage());
//			return null;
//		}
//	}
//
//	@POST
//	@Path("createCustomer")
//	@Consumes(MediaType.APPLICATION_JSON)
//	@Produces(MediaType.APPLICATION_JSON)
//	public String Customer(Customer customer) throws Exception {
//		AdminFacad admin = (AdminFacad)CouponSystem.login("admin", "1234", ClientType.ADMIN);
//		//AdminFacad admin = getFacade();
//		try {
//			customer = admin.createCustomer(customer);
//			return new Gson().toJson(customer);
//		} catch (CouponException e) {
//			return "Failed to Add a new customer:" + e.getMessage();
//		}
//	}
//
//	@DELETE
//	@Path("removeCustomer/{customerId}")
//	@Produces(MediaType.TEXT_PLAIN)
//	public String removeCustomer(@PathParam("customerId") long id) throws Exception {
//		AdminFacad admin = (AdminFacad)CouponSystem.login("admin", "1234", ClientType.ADMIN);
//	//	AdminFacad admin = getFacade();
//
//		try {
//			Customer customer = admin.getCustomer(id);
//			if (customer != null) {
//				admin.removeCustomer(customer);
//				return "Succeded to remove a customer: name = " + customer.getCustomerName();
//			} else {
//				return "Failed to remove a customer: the provided customer id is invalid";
//			}
//		} catch (CouponException e) {
//			return "Failed to remove a customer: " + e.getMessage();
//		}
//
//	}
//
//	@POST
//	@Path("updateCustomer")
//	@Consumes(MediaType.APPLICATION_JSON)
//	@Produces(MediaType.APPLICATION_JSON)
//	public String updateCustomer(Customer newCostomer) throws Exception {
//		AdminFacad admin = (AdminFacad)CouponSystem.login("admin", "1234", ClientType.ADMIN);
//		//AdminFacad admin = getFacade();
//		try {
//			Customer oldCustomer = admin.getCustomer(newCostomer.getCustomerId());
//			
//			if (oldCustomer != null) {
//				newCostomer = admin.updateCustomer(oldCustomer, newCostomer.getPassword());
//				return new Gson().toJson(newCostomer);
//			} else {
//				return "Failed to update a customer: the provided customer id is invalid";
//			}
//		} catch (CouponException e) {
//			return "Failed to update a customer: " + e.getMessage();
//		}
//	}
//
//	@GET
//	@Path("getAllCustomers")
//	@Produces(MediaType.APPLICATION_JSON)
//	public String getAllCustomers() throws Exception {
//		AdminFacad admin = (AdminFacad)CouponSystem.login("admin", "1234", ClientType.ADMIN);
//		//AdminFacad admin = getFacade();
//		Set<Customer> customers;
//		try {
//			customers = admin.getAllCustomers();
//		} catch (CouponException e) {
//			System.err.println("Get all customers failed: " + e.getMessage());
//			customers = new HashSet<Customer>();
//		}
//		return new Gson().toJson(customers);
//	}
//
//	@GET
//	@Path("getCustomer/{customerId}")
//	@Produces(MediaType.APPLICATION_JSON)
//	public String getCustomer(@PathParam("customerId") long id) throws Exception {
//		AdminFacad admin = (AdminFacad)CouponSystem.login("admin", "1234", ClientType.ADMIN);
//		//AdminFacad admin = getFacade();
//		System.out.println(id);
//		try {
//			Customer customer = admin.getCustomer(id);
//			System.out.println(customer);
//			if (customer != null) {
//				return new Gson().toJson(customer);
//			} else {
//				return null;
//			}
//		} catch (CouponException e) {
//			System.err.println("get customer by id failed " + e.getMessage());
//			return null;
//		}
//	}

}
