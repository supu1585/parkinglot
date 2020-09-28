package com.dkatalis.parkinglot.paymentrule;

import java.math.BigDecimal;

public interface IPaymentRule {

    boolean test(int durationHour);

    BigDecimal applyRule(int durationHour, BigDecimal pricePerHour);
}
