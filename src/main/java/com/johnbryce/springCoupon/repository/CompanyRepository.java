package com.johnbryce.springCoupon.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.johnbryce.springCoupon.beans.Company;

public interface  CompanyRepository extends JpaRepository<Company, Long> {
	
	Company findByNameAndPassword(String name, String password);

	boolean findByNameIsNull(String compName);

}
