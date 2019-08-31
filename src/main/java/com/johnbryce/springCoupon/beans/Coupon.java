package com.johnbryce.springCoupon.beans;

import java.sql.Date;
import java.time.OffsetDateTime;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Coupon {
	

	@Id
	@GeneratedValue
	private int id;
	
	@Column(nullable = false)
	private String title;
	
	@Column(nullable = false)
	private Date startDate;
	
	@Column(nullable = false)
	private Date endDate;

	@Column(nullable = false)
	private int amount;
	
	@Column(nullable = false)
	private CouponType category;

	@Column(nullable = false)
	private String message;

	@Column(nullable = false)
	private double price;

	@Column
	private String image;
	
	//@ManyToOne
	private Company company;
	
	public Coupon(String title, Date startDate, Date endDate, int amount, CouponType type, String message,double price,String Image) {
		setTitle(title);
		setStartDate(startDate);
		setEndDate(endDate);
		setAmount(amount);
		setCategory(type);
		setMessage(message);
		setPrice(price);
		setImage(Image);
	}

}


