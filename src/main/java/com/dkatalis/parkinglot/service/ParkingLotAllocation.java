package com.dkatalis.parkinglot.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.dkatalis.parkinglot.model.ParkingSlot;

@Service
public class ParkingLotAllocation {

	private List<ParkingSlot> allocatedSlots;
	
	public void initParking(int parkingCapacity) {
		allocatedSlots = new ArrayList<>(parkingCapacity);
	}

	public List<ParkingSlot> getAllocatedSlots() {
		return allocatedSlots;
	}

	public void setAllocatedSlots(List<ParkingSlot> allocatedSlots) {
		this.allocatedSlots = allocatedSlots;
	}

	@Nullable
	public void park(ParkingSlot slot, int position) {
		allocatedSlots.add(position, slot);
	}

	@NotNull
	public List<ParkingSlot> status() {
		return allocatedSlots.stream().filter(x -> null != x).sorted(Comparator.comparingInt(ParkingSlot::getPosition))
				.collect(Collectors.toList());
	}
	
	
}
