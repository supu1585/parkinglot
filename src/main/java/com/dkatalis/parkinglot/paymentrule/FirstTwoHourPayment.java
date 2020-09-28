package com.dkatalis.parkinglot.paymentrule;

import java.math.BigDecimal;

public class FirstTwoHourPayment implements IPaymentRule {

	@Override
	public boolean test(int duration) {
		return duration >= 2;
	}

	@Override
	public BigDecimal applyRule(int duration, BigDecimal ratePerHour) {
		if (test(duration))
			return ratePerHour;

		return BigDecimal.ZERO;
	}
}
