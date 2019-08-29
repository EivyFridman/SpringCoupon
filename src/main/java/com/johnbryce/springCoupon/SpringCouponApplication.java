package com.johnbryce.springCoupon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages="com.johnbryce.springCoupon")
public class SpringCouponApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCouponApplication.class, args);
	}

}
