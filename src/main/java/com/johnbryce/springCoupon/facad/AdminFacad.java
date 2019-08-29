package com.johnbryce.springCoupon.facad;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Optional;

import javax.transaction.Transactional;

import org.aspectj.internal.lang.annotation.ajcDeclareAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.johnbryce.springCoupon.beans.Company;
import com.johnbryce.springCoupon.beans.Customer;
import com.johnbryce.springCoupon.exception.CouponException;
import com.johnbryce.springCoupon.exception.CreateException;
import com.johnbryce.springCoupon.exception.RemoveException;
import com.johnbryce.springCoupon.repository.CompanyRepository;
import com.johnbryce.springCoupon.repository.CustomerRepository;
import com.johnbryce.springCoupon.utils.DataBase;

@Service
@Transactional
public class AdminFacad implements CouponClientFacad{

	
	private static final String ADMIN_USER_NAME = "admin";
	private static final String ADMIN_PASSWORD = "1234";
	@Autowired
	private CompanyRepository companyRepository;
	@Autowired
	private CustomerRepository customerRepository;
	
	
	/**
	 * this method check password of admin, if true return admin.
	 * 
	 * @param name  String
	 * @param password  String
	 *
	 */
	public CouponClientFacad login(String name, String password) {
		if (name.equals(AdminFacad.ADMIN_USER_NAME) && password.equals(AdminFacad.ADMIN_PASSWORD)) {
			return this;
		}
		return null;
	}
	

	/**
	 * this method create a company, at first need to check if company exist.
	 * 
	 * @param company
	 * @throws CouponException
	 */
	public Company createCompany(Company company) throws CouponException { 
		if (company != null) {
			String compName = company.getName();
			if (compName != null) {
				if (company.getPassword() != null) {
						if (companyRepository.findByNameIsNull(compName)) {
							Company newCompany = companyRepository.save(company);
							if(newCompany != null) {
								return newCompany;
							}else {
								throw new CouponException("erro accured while saving,company was not saved");
							}
						} else {
							throw new CouponException("create company failed by already existing similar company name");
						}
				}else {
					throw new CouponException("create company failed by empty password");
				}
			}else {
				throw new CouponException("create company failed by empty company name");
			}
		}
		return null;
	}

	/**
	 * This method get Company as object and take all its coupons and delete all the
	 * coupons from Company_Coupon table & Customer_Coupon table & Coupon table. in
	 * addition, we delete the company in the Company table.
	 * 
	 * @param company
	 */
	public void removeCompany(Company company) {
		companyRepository.delete(company);
	}

	/**
	 * This method get Company as object and send it to method that update line in
	 * the Company table. The method don't update the name.
	 * 
	 * @param company
	 * @param newpassword
	 * @param newEmail
	 * @throws CouponException 
	 */
	public Company updateCompany(Company company, String newpassword, String newEmail) throws CouponException{
		company.setPassword(newpassword);
		company.setEmail(newEmail);
		Company newCompany = companyRepository.save(company);
		if(newCompany != null) {
			return newCompany;
		}else {
			throw new CouponException("erro accured while saving,company was not saved");
		}
	}

	/**
	 * This method get company id and return the line from Company table as Company
	 * object.
	 * 
	 * @param id
	 * @return
	 */
	public Company getCompany(long id) throws CouponException {
			Optional<Company> company = companyRepository.findById(id);
			return company.isPresent()? company.get() : null;
	}

	/**
	 * This method return all the Companies as a list.
	 * 
	 * @return
	 */
	public Collection<Company> getAllCompanies() throws CouponException {
		return companyRepository.findAll();
	}

	/**
	 * This method get Customer as object and send it to create line in Customer
	 * table. first we check that the name not exist.
	 * 
	 * @param customer
	 * @throws CouponException
	 */
	public Customer createCustomer(Customer customer) throws CouponException {
		if (customer != null) {
			String custName = customer.getName();
			if (custName != null) {
				if (customer.getPassword() != null) {
						if (customerRepository.findByNameIsNull(custName)) {
							Customer newCustomer = customerRepository.save(customer);
							if (newCustomer != null) {
								return newCustomer;
							} else {
								throw new CouponException("error accured while saving,customer was not saved");
							}

						}else {
							throw new CouponException("create customer failed by already existing similar customer name");
						}
				}else {
					throw new CouponException("create customer failed by empty password");
				}
			}else {
				throw new CouponException("create customer failed by empty customer name");
			}
		}
		return customer;
	}


	/**
	 * This method get Customer as object and take all its coupons and delete all
	 * the lines with those coupons from Customer_Coupon table and the line from
	 * Customer table
	 * 
	 * @param customer
	 */
	public void removeCustomer(Customer customer) throws CouponException {
			customerRepository.delete(customer);	
	}

	/**
	 * This method get Customer,and new password as object and send it to method
	 * that update line in the Customer table. The method don't update the name
	 * 
	 * @param customer
	 * @param newpassword
	 * @throws CouponException
	 */
	public Customer updateCustomer(Customer customer, String newpassword) throws CouponException {
		customer.setPassword(newpassword);
		Customer newCustomer = customerRepository.save(customer);
		if(newCustomer != null) {
			return newCustomer;
		}else {
			throw new CouponException("erro accured while saving,customer was not updated");
		}
	}

	/**
	 * This method return all the Customers as a list.
	 * 
	 * @return
	 */
	public Collection<Customer> getAllCustomers() throws CouponException {
			return customerRepository.findAll();
	}

	/**
	 * This method get Customer id and return the line from Customer table as
	 * Customer object.
	 * 
	 * @param id
	 * @return
	 */
	public Optional<Customer> getCustomer(long id){
		return customerRepository.findById(id);
	}

	public void rebuildDb() throws CouponException, RemoveException, SQLException, CreateException {
		DataBase.dropTableifNeeded();
		DataBase.createTables();
	}

}
