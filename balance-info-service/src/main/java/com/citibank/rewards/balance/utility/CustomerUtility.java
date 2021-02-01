package com.citibank.rewards.balance.utility;

import com.citibank.rewards.balance.model.CustomerType;

public class CustomerUtility {

	public static CustomerType getCustomerLevel(String availablePoint) {
		CustomerType cType = new CustomerType();
		final int availablePts = Integer.parseInt(availablePoint);

		if (availablePts >= 100000) {
			cType.setCustomerType("VVIC");
		} else if (availablePts < 100000 && availablePts >= 50000) {
			cType.setCustomerType("VIC");
		} else if (availablePts < 50000 && availablePts >= 10000) {
			cType.setCustomerType("IC");
		} else
			cType.setCustomerType("NC");

		return cType;

	}
}
