package com.johnbryce.springCoupon.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.johnbryce.springCoupon.beans.Company;
import com.johnbryce.springCoupon.beans.Coupon;
import com.johnbryce.springCoupon.facade.CompanyFacade;
import com.johnbryce.springCoupon.utils.ClientType;
import com.johnbryce.springCoupon.utils.CouponSystem;

@RestController
@RequestMapping("/company")
public class CompanyController {

	@PostMapping("/createCoupon")
	public ResponseEntity<?> createCoupon(Coupon coupon) {
		// CompanyFacade companyFacade = getFacade();
		try {
			CompanyFacade companyFacade = (CompanyFacade) CouponSystem.login("company", "company", ClientType.COMPANY);
			return new ResponseEntity<Coupon>(companyFacade.createCoupon(coupon), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("Failed to create a couponS: " + e.getMessage(),
					HttpStatus.PRECONDITION_REQUIRED);
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
				return new ResponseEntity<String>("Failed to remove a coupon: the provided coupon id is invalid",
						HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<String>("Failed to remove a coupon: " + e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// TODO chekc what are the fileds that should be updated (only)
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
				return new ResponseEntity<String>(
						"Failed to Update a coupon: the provided coupon does not exist in the system",
						HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<String>("Failed to update a coupon: " + e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

//	@GET
//	@Path("getCompany")
//	@Produces(MediaType.APPLICATION_JSON)
//	public String getCompany() {
//		CompanyFacade companyFacade = getFacade();
//		Company company;
//		try {
//			company = companyFacade.getCompany();
//		} catch (Exception e) {
//			System.err.println("Get Company failed: " + e.getMessage());
//			company = new Company();
//		}
//
//		return new Gson().toJson(company);
//	}
//
//	@GET
//	@Path("getCoupon/{couponId}")
//	@Produces(MediaType.APPLICATION_JSON)
//	public String getCoupon(@PathParam("couponId") long id) throws Exception {
//		CompanyFacade companyFacade = (CompanyFacade) CouponSystem.login("company", "company", ClientType.COMPANY);
//		//CompanyFacade companyFacade = getFacade();
//		try {
//			Coupon coupon = companyFacade.getCoupon(id);
//			if (coupon != null) {
//				return new Gson().toJson(coupon);
//			} else {
//				return null;
//			}
//		} catch (Exception e) {
//			System.err.println("get coupon by id failed " + e.getMessage());
//			return null;
//		}
//	}
//
//	@GET
//	@Path("getCoupons")
//	@Produces(MediaType.APPLICATION_JSON)
//	public String getCoupons() throws Exception {
//		CompanyFacade companyFacade = (CompanyFacade) CouponSystem.login("company", "company", ClientType.COMPANY);
//		// CompanyFacade companyFacade = getFacade();
//		Set<Coupon> coupons;
//
//		try {
//			coupons = company.getCoupons();
//			System.out.println(coupons);
//		} catch (Exception e) {
//			System.err.println("Get Coupons failed: " + e.getMessage());
//			coupons = new HashSet<Coupon>();
//		}
//		return new Gson().toJson(coupons);
//	}
//
//	@GET
//	@Produces(MediaType.APPLICATION_JSON)
//	@Path("/getAllCouponsByType/{type}")
//	public String getAllCouponsByType(@PathParam("type") CouponType type) throws Exception {
//		CompanyFacade companyFacade = (CompanyFacade) CouponSystem.login("company", "company", ClientType.COMPANY);
//		// CompanyFacade companyFacade = getFacade();
//		Set<Coupon> allCouponsByType = new HashSet<>();
//		try {
//			allCouponsByType = company.getAllCouponsByType(type);
//		} catch (Exception e) {
//			System.err.println("Get Coupons by type failed: " + e.getMessage());
//			allCouponsByType = new HashSet<Coupon>();
//		}
//		return new Gson().toJson(allCouponsByType);
//	}
//
//	@GET
//	@Produces(MediaType.APPLICATION_JSON)
//	@Path("/getCouponsByMaxCouponPrice/{price}")
//	public String getCouponsByMaxCouponPrice(@PathParam("price") double price) throws Exception {
//		CompanyFacade companyFacade = (CompanyFacade) CouponSystem.login("company", "company", ClientType.COMPANY);
//		//CompanyFacade companyFacade = getFacade();
//		Set<Coupon> allCouponsByType = new HashSet<>();
//		try {
//			allCouponsByType = companyFacade.getCouponsByMaxCouponPrice(price);
//		} catch (Exception e) {
//			System.err.println("Get Coupons by max price failed: " + e.getMessage());
//			allCouponsByType = new HashSet<Coupon>();
//		}
//		return new Gson().toJson(allCouponsByType);
//	}
//
//	@GET
//	@Consumes(MediaType.APPLICATION_JSON)
//	@Path("/getCouponsByMaxCouponDate/{date}")
//	public String getCouponsByMaxCouponDate(@PathParam("date") Date date) throws Exception {
//		CompanyFacade companyFacade = (CompanyFacade) CouponSystem.login("company", "company", ClientType.COMPANY);
//		//CompanyFacade companyFacade = getFacade();
//		Set<Coupon> allCouponsByType = new HashSet<>();
//		try {
//			allCouponsByType = companyFacade.getCouponsByMaxCouponDate(date);
//		} catch (Exception e) {
//			System.err.println("Get Coupons by max dare failed: " + e.getMessage());
//			allCouponsByType = new HashSet<Coupon>();
//		}
//		return new Gson().toJson(allCouponsByType);
//	}

}