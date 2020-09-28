package com.dkatalis.parkinglot.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.dkatalis.parkinglot.paymentrule.IPaymentRule;


public class ParkingRateCalculator {

	private BigDecimal pricePerHour;

	private List<IPaymentRule> paymentRules;
	
	public ParkingRateCalculator(BigDecimal pricePerHour) {
		this.pricePerHour = pricePerHour;
		paymentRules = new ArrayList<>();
	}

	public void addFareRule(IPaymentRule rule) {
		this.paymentRules.add(rule);
	}

	public BigDecimal calculatePrice(int duration) {
		BigDecimal totalAmount = BigDecimal.valueOf(duration).multiply(pricePerHour);
		for (IPaymentRule rules : paymentRules) {
			totalAmount = totalAmount.subtract(rules.applyRule(duration, pricePerHour));
		}
		return totalAmount;
	}
}
