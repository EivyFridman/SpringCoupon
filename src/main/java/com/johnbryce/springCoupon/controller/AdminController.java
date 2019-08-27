package com.johnbryce.springCoupon.controller;

import java.awt.PageAttributes.MediaType;
import java.util.HashSet;
import java.util.Set;

import javax.websocket.server.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.johnbryce.facad.AdminFacad;
import com.johnbryce.springCoupon.beans.Company;
import com.johnbryce.springCoupon.beans.Customer;
import com.johnbryce.springCoupon.exception.CouponException;
import com.johnbryce.springCoupon.service.AdminService;
import com.johnbryce.springCoupon.utils.ClientType;
import com.johnbryce.springCoupon.utils.CouponSystem;

@RestController
@RequestMapping("/admin/")
public class AdminController {
	
	@Autowired
	AdminService adminService;
	
	@PostMapping("/createCompany")
	public ResponseEntity<?> createCompany(@RequestBody Company company)  {
		return new ResponseEntity<Company> (adminService.createCompany(company),HttpStatus.OK);
	}


	@DELETE
	@Path("removeCompany/{companyId}")
	@Produces(MediaType.TEXT_PLAIN)
	public String removeCompany(@PathParam("companyId") long id) throws Exception {
		AdminFacad admin = (AdminFacad)CouponSystem.login("admin", "1234", ClientType.ADMIN);
		//AdminFacad admin = getFacade();
		try {
			Company company = admin.getCompany(id);
			if (company != null) {
				System.out.println("delete company id");
				admin.removeCompany(company);
				System.out.println(company);
				return "Succeded to remove a company: name = " + company.getCompName() + ", id = " + id;
			} else {
				return "Failed to remove a company: the provided company id is invalid";
			}
		} catch (CouponException e) {
			return "Failed to remove a company: " + e.getMessage();
		}

	}

	@POST
	@Path("updateCompany")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String updateCompany(Company newCompany) throws Exception {
		AdminFacad admin = (AdminFacad)CouponSystem.login("admin", "1234", ClientType.ADMIN);
		//AdminFacad admin = getFacade();
		try {
			Company oldCompany = admin.getCompany(newCompany.getCompanyId());
			System.out.println("before update componay "+ oldCompany);
			if (oldCompany != null) {
				newCompany = admin.updateCompany(oldCompany, newCompany.getPassword(), newCompany.getEmail());
				System.out.println("after update componay "+ newCompany);
				return new Gson().toJson(newCompany);
			} else {
				return "Failed to update a company: the provided company id is invalid";
			}
		} catch (CouponException e) {
			return "Failed to update a company: " + e.getMessage();
		}

	}

	@GET
	@Path("getAllCompanies")
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllCompanies() throws Exception {
		//AdminFacad admin = getFacade();
		AdminFacad admin = (AdminFacad)CouponSystem.login("admin", "1234", ClientType.ADMIN);
		Set<Company> companies;
		try {
			companies = admin.getAllCompanies();
		} catch (CouponException e) {
			System.err.println("Get all Companies failed: " + e.getMessage());
			companies = new HashSet<Company>();
		}
		return new Gson().toJson(companies);
	}

	@GET
	@Path("getCompany/{companyId}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getCompany(@PathParam("companyId") long id) throws Exception {
		System.out.println("the company is");
		AdminFacad admin = (AdminFacad)CouponSystem.login("admin", "1234", ClientType.ADMIN);
		//AdminFacad admin = getFacade();
		try {
			System.out.println("the company is");
			Company company = admin.getCompany(id);
			System.out.println(company);
			if (company != null) {
				return new Gson().toJson(company);
			} else {
				return null;
			}
		} catch (CouponException e) {
			System.err.println("get company by id failed " + e.getMessage());
			return null;
		}
	}

	@POST
	@Path("createCustomer")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String Customer(Customer customer) throws Exception {
		AdminFacad admin = (AdminFacad)CouponSystem.login("admin", "1234", ClientType.ADMIN);
		//AdminFacad admin = getFacade();
		try {
			customer = admin.createCustomer(customer);
			return new Gson().toJson(customer);
		} catch (CouponException e) {
			return "Failed to Add a new customer:" + e.getMessage();
		}
	}

	@DELETE
	@Path("removeCustomer/{customerId}")
	@Produces(MediaType.TEXT_PLAIN)
	public String removeCustomer(@PathParam("customerId") long id) throws Exception {
		AdminFacad admin = (AdminFacad)CouponSystem.login("admin", "1234", ClientType.ADMIN);
	//	AdminFacad admin = getFacade();

		try {
			Customer customer = admin.getCustomer(id);
			if (customer != null) {
				admin.removeCustomer(customer);
				return "Succeded to remove a customer: name = " + customer.getCustomerName();
			} else {
				return "Failed to remove a customer: the provided customer id is invalid";
			}
		} catch (CouponException e) {
			return "Failed to remove a customer: " + e.getMessage();
		}

	}

	@POST
	@Path("updateCustomer")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String updateCustomer(Customer newCostomer) throws Exception {
		AdminFacad admin = (AdminFacad)CouponSystem.login("admin", "1234", ClientType.ADMIN);
		//AdminFacad admin = getFacade();
		try {
			Customer oldCustomer = admin.getCustomer(newCostomer.getCustomerId());
			
			if (oldCustomer != null) {
				newCostomer = admin.updateCustomer(oldCustomer, newCostomer.getPassword());
				return new Gson().toJson(newCostomer);
			} else {
				return "Failed to update a customer: the provided customer id is invalid";
			}
		} catch (CouponException e) {
			return "Failed to update a customer: " + e.getMessage();
		}
	}

	@GET
	@Path("getAllCustomers")
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllCustomers() throws Exception {
		AdminFacad admin = (AdminFacad)CouponSystem.login("admin", "1234", ClientType.ADMIN);
		//AdminFacad admin = getFacade();
		Set<Customer> customers;
		try {
			customers = admin.getAllCustomers();
		} catch (CouponException e) {
			System.err.println("Get all customers failed: " + e.getMessage());
			customers = new HashSet<Customer>();
		}
		return new Gson().toJson(customers);
	}

	@GET
	@Path("getCustomer/{customerId}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getCustomer(@PathParam("customerId") long id) throws Exception {
		AdminFacad admin = (AdminFacad)CouponSystem.login("admin", "1234", ClientType.ADMIN);
		//AdminFacad admin = getFacade();
		System.out.println(id);
		try {
			Customer customer = admin.getCustomer(id);
			System.out.println(customer);
			if (customer != null) {
				return new Gson().toJson(customer);
			} else {
				return null;
			}
		} catch (CouponException e) {
			System.err.println("get customer by id failed " + e.getMessage());
			return null;
		}
	}

}
