package com.johnbryce.springCoupon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.johnbryce.springCoupon.beans.Customer;



@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

	boolean findByNameIsNull(String custName);
}
