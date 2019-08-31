package com.johnbryce.springCoupon.controller;

import java.sql.Date;
import java.util.Collection;

import javax.annotation.PostConstruct;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.johnbryce.springCoupon.beans.Company;
import com.johnbryce.springCoupon.beans.Coupon;
import com.johnbryce.springCoupon.beans.CouponType;
import com.johnbryce.springCoupon.facade.CompanyFacade;
import com.johnbryce.springCoupon.utils.ClientType;
import com.johnbryce.springCoupon.utils.CouponSystem;

@RestController
@RequestMapping("/company")
public class CompanyController {
	
	@PostConstruct
	public void Init() {	
		try {
			CompanyFacade companyFacade = (CompanyFacade) CouponSystem.login("company", "company", ClientType.COMPANY);
			companyFacade.createCoupon(new Coupon("i1",new Date(2019,9,15), new Date(2019,10,15), 3, CouponType.FOOD, "m1",10,""));
			companyFacade.createCoupon(new Coupon("i2",new Date(2019,9,15), new Date(2019,9,25), 4, CouponType.FOOD, "m2",20,""));
			companyFacade.createCoupon(new Coupon("i3",new Date(2019,9,15), new Date(2019,9,15), 5, CouponType.FOOD, "m3",30,""));
		} catch (Exception e) {
			System.out.println("Failed to init company ...");
		}
	}

	@PostMapping("/createCoupon")
	public ResponseEntity<?> createCoupon(Coupon coupon) {
		// CompanyFacade companyFacade = getFacade();
		try {
			CompanyFacade companyFacade = (CompanyFacade) CouponSystem.login("company", "company", ClientType.COMPANY);
			return new ResponseEntity<Coupon>(companyFacade.createCoupon(coupon), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("Failed to create a couponS: " + e.getMessage(),HttpStatus.PRECONDITION_REQUIRED);
		}

	}

	@DeleteMapping("removeCoupon/{companyId}")
	public ResponseEntity<String> removeCompany(@PathVariable long id) {
		try {
			CompanyFacade companyFacade = (CompanyFacade) CouponSystem.login("company", "company", ClientType.COMPANY);
			Coupon coupon = companyFacade.getCoupon(id);
			if (coupon != null) {
				companyFacade.removeCouponID(id);
				return new ResponseEntity<String>(
						"Succeded to remove a coupon: name = " + coupon.getTitle() + ", id = " + id, HttpStatus.OK);
			} else {
				return new ResponseEntity<String>("Failed to remove a coupon: the provided coupon id is invalid",HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<String>("Failed to remove a coupon: " + e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// TODO check what are the fields that should be updated (only)
	@PutMapping("/updateCoupon")
	public ResponseEntity<?> updateCoupon(Coupon coupon) {
		try {
			CompanyFacade companyFacade = (CompanyFacade) CouponSystem.login("company", "company", ClientType.COMPANY);
			if (coupon != null) {
				Coupon oldCoupon = companyFacade.getCoupon(coupon.getId());
				oldCoupon.setTitle(coupon.getTitle());
				oldCoupon.setStartDate(coupon.getStartDate());
				oldCoupon.setEndDate(coupon.getEndDate());
				oldCoupon.setAmount(coupon.getAmount());
				oldCoupon.setCategory(coupon.getCategory());
				oldCoupon.setMessage(coupon.getMessage());
				oldCoupon.setPrice(coupon.getPrice());
				oldCoupon.setImage(coupon.getImage());
				coupon = companyFacade.updateCoupon(oldCoupon);
				return new ResponseEntity<Coupon>(coupon, HttpStatus.OK);
			} else {
				return new ResponseEntity<String>("Failed to Update a coupon: the provided coupon does not exist in the system",HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<String>("Failed to update a coupon: " + e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/getCompany")
	public ResponseEntity<?> getCompany() {
		try {
			CompanyFacade companyFacade = (CompanyFacade) CouponSystem.login("company", "company", ClientType.COMPANY);
			return new ResponseEntity<Company>(companyFacade.getCompany(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("Get Company failed: " + e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/getCoupon/{couponId}")
	public ResponseEntity<?> getCoupon(@PathVariable long id){
		//CompanyFacade companyFacade = getFacade();
		try {
			CompanyFacade companyFacade = (CompanyFacade) CouponSystem.login("company", "company", ClientType.COMPANY);
			Coupon coupon = companyFacade.getCoupon(id);
			if (coupon != null) {
				return new ResponseEntity<Coupon> (coupon,HttpStatus.OK);
				} else {
					return new ResponseEntity<String> ("Failed get the coupon datails : the provided coupon id is invalid",HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<String> ("Failed to get the coupon: " + e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/getCoupons")
	public ResponseEntity<?> getCoupons(){
		// CompanyFacade companyFacade = getFacade();
		try {
			CompanyFacade companyFacade = (CompanyFacade) CouponSystem.login("company", "company", ClientType.COMPANY);
			return new ResponseEntity<Collection<Coupon>> (companyFacade.getCoupons(),HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String> ("Failed to get all coupons: " + e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/getAllCouponsByType/{type}")
	public ResponseEntity<?> getAllCouponsByType(@PathVariable CouponType type) {
		// CompanyFacade companyFacade = getFacade();
		try {
			CompanyFacade companyFacade = (CompanyFacade) CouponSystem.login("company", "company", ClientType.COMPANY);
			return new ResponseEntity<Collection<Coupon>> (companyFacade.getAllCouponsByType(type),HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String> ("Failed to get all coupons of type"+type+" : " + e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/getCouponsByMaxCouponPrice/{price}")
	public ResponseEntity<?> getCouponsByMaxCouponPrice(@PathVariable double price)  {
		
		//CompanyFacade companyFacade = getFacade();
		try {
			CompanyFacade companyFacade = (CompanyFacade) CouponSystem.login("company", "company", ClientType.COMPANY);
			return new ResponseEntity<Collection<Coupon>> (companyFacade.getCouponsByMaxCouponPrice(price),HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String> ("Failed to get all coupons of max price "+price+" : " + e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/getCouponsByMaxCouponDate/{date}")
	public ResponseEntity<?> getCouponsByMaxCouponDate(@PathVariable Date date){
		
		//CompanyFacade companyFacade = getFacade();
		try {
			CompanyFacade companyFacade = (CompanyFacade) CouponSystem.login("company", "company", ClientType.COMPANY);
			return new ResponseEntity<Collection<Coupon>> (companyFacade.getCouponsByMaxCouponDate(date),HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String> ("Failed to get all coupons of max sate "+date+" : " + e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}