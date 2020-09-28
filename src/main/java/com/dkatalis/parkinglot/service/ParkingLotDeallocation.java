package com.dkatalis.parkinglot.service;

import java.math.BigDecimal;
import java.util.List;

import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.dkatalis.parkinglot.model.ParkingSlot;
import com.dkatalis.parkinglot.model.Vehicle;
import com.dkatalis.parkinglot.paymentrule.FirstTwoHourPayment;

@Service
public class ParkingLotDeallocation {

	
	private ParkingRateCalculator parkingRateCalculator;
	
	public void initUnParking() {
		parkingRateCalculator = new ParkingRateCalculator(new BigDecimal(10));
		parkingRateCalculator.addFareRule(new FirstTwoHourPayment());
	}

	@Nullable
	public ParkingSlot unpark(Vehicle vehicle, int durationHour, List<ParkingSlot> allocatedSlots) {
		int parkSlotIndex = allocatedSlots.indexOf(new ParkingSlot(vehicle));
		if (-1 == parkSlotIndex) {
			return null;
		}
		ParkingSlot slot = allocatedSlots.get(parkSlotIndex);
		slot.setDurationHour(durationHour);
		slot.setPrice(this.parkingRateCalculator.calculatePrice(durationHour));
		allocatedSlots.set(parkSlotIndex, null);

		return slot;
	}

}
