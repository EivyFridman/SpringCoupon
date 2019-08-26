package com.johnbryce.springCoupon.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.johnbryce.springCoupon.beans.Coupon;

public interface CouponRepository extends JpaRepository<Coupon, Long>{
	
	List<Coupon> findByTitle(String title);
	Optional<Coupon> findById(long id);

}
