package com.johnbryce.springCoupon.service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.johnbryce.springCoupon.beans.Company;
import com.johnbryce.springCoupon.beans.Coupon;
import com.johnbryce.springCoupon.beans.CouponType;
import com.johnbryce.springCoupon.exception.CouponException;
import com.johnbryce.springCoupon.repository.CompanyRepository;
import com.johnbryce.springCoupon.repository.CouponRepository;
import com.johnbryce.springCoupon.utils.Utile;

@Service
public class CompanyService implements CouponClientService {
	@Autowired
	private CouponRepository couponRepository;
	@Autowired
	private CompanyRepository companyRepository;
	
	private Company company;
	Logger logger = LoggerFactory.getLogger(CompanyService.class);

	@Override
	public CouponClientService login(String name, String password) throws Exception {
		company = companyRepository.findByNameAndPassword(name, password);
		if (company != null) {
			return this;
		} else {
			return null;
		}
	}

	@Transactional
	public Coupon createCoupon(Coupon coupon) throws CouponException {
		if (coupon != null) {
			logger.debug("Create coupon" + coupon.toString());
			String CoupTitle = coupon.getTitle();
			if (CoupTitle != null) {
				Date startDate = coupon.getStartDate();
				Date endDate = coupon.getEndDate();
				if (startDate.getTime() <= endDate.getTime()) {
					if (startDate.getTime() >= Utile.getCurrentDate().getTime()) {
						if (couponRepository.findByTitle(CoupTitle).isEmpty()/* == null */) {
							coupon = couponRepository.save(coupon);
							return coupon;
						} else {
							throw new CouponException("Coupon Title is Already Exists! Create New Coupon is Canceled!");
						}
					} else {
						throw new CouponException("invalid coupon start time is in the past");
					}
				} else {
					throw new CouponException("invalid coupon start time in after the end time");
				}
			} else {
				throw new CouponException("invalid coupon has no title");
			}
		} else {
			throw new CouponException("invalid null coupon");
		}
	}

	@Transactional
	public void removeCouponID(long coupId) throws Exception {
		Optional<Coupon> findById = couponRepository.findById(coupId);
		if (coupId > 0 || findById.isPresent()) {
			if (company.getCoupons().contains(findById.get())) {
				couponRepository.delete(coupId);
			} else {
				throw new CouponException("cannot delete coupon  that doesn't belog to the company");
			}
		} else {
			throw new CouponException("invalid coupon id");
		}
	}

	@Transactional
	public Coupon updateCoupon(Coupon coupon) throws Exception {
		if (coupon != null) {
			long couponId = coupon.getId();
			Optional<Coupon> findById = couponRepository.findById(couponId);
			if (findById.isPresent() && company.getCoupons().contains(findById.get())) {
				Double CoupPrice = coupon.getPrice();
				if (CoupPrice > 0) {
					Date startDate = coupon.getStartDate();
					Date endDate = coupon.getEndDate();
					if (startDate.getTime() <= endDate.getTime()) {
						couponRepository.delete(coupon);
						return couponRepository.save(coupon);
					} else {
						throw new CouponException("Update Coupon as the end date is before the start date");
					}

				} else {
					throw new CouponException(" Update Coupon failed due to invalid price");
				}

			} else {
				throw new CouponException("cannot update coupon as there is no coupon with that id in the coupmany");
			}
		} else {
			throw new CouponException("invalid null coupon");
		}
	}

	public Company getCompany() {
		return company;
	}

	public Coupon getCoupon(long coupId) throws Exception {
		Optional<Coupon> findById = couponRepository.findById(coupId);
		if (findById.isPresent() && company.getCoupons().contains(findById.get())) {
			return findById.get();
		} else {
			throw new CouponException("is no coupon with that id in the coupmany");
		}

	}

	public List<Coupon> getCoupons() {
		return company.getCoupons();
	}

	public Set<Coupon> getAllCouponsByType(CouponType coupType) {
		return null;
	}

}