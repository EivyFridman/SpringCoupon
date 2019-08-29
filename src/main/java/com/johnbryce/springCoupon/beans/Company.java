package com.johnbryce.springCoupon.beans;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Data
@AllArgsConstructor
public class Company {
	@Id
	@GeneratedValue
	private long id;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private String password;
	
	@Column(nullable = false)
	private String email;
	
	
    @OneToMany(cascade=CascadeType.PERSIST)
	private List<Coupon> coupons;
    
    public Company(String name,String password,String email) {
    	setName(name);
    	setPassword(password);
    	setEmail(email);
    }
}
