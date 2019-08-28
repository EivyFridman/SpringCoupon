package com.johnbryce.springCoupon.beans;

import java.sql.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
	
	@Basic(optional = false)
	@Column(nullable = false)
	private CouponType category;

	@Basic(optional = false)
	@Column(nullable = false)
	private String message;

	@Basic(optional = false)
	@Column(nullable = false)
	private double price;

	@Basic(optional = false)
	@Column(nullable = false)
	private String image;
	
	//@ManyToOne
	private Company company;

}


