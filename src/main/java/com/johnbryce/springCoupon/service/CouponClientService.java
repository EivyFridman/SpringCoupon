package com.johnbryce.springCoupon.service;

public interface CouponClientService {

	/**
	 * in this class we have method that check by name & password and we use it
	 *  at classes to enable login by clientType.
	 * @param name
	 * @param password
	 * @return
	 * @throws Exception 
	 */
	public CouponClientService login(String name, String password) throws Exception;

}
