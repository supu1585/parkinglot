package com.dkatalis.parkinglot.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.dkatalis.parkinglot.model.ParkingSlot;
import com.dkatalis.parkinglot.model.Vehicle;

@Service
public class CarParkingApp {

	final static Logger logger = Logger.getLogger(CarParkingApp.class.getName());

	ParkingRateCalculator calculator;

	@Autowired
	ParkingLotAllocation parkinglotAllocation;

	@Autowired
	ParkingLotDeallocation parkingLotDeallocation;

	Deque<Integer> deque;
	
	/**
	 * This is main method for carparkingApp.
	 * 
	 * @param arg
	 */
	public void carParking(String[] arg) {
		if (arg.length != 1) {
			logger.log(Level.INFO, "Please supply the full input file path");
			System.exit(0);
		}
		String filePath = arg[0];
		try (BufferedReader bf = new BufferedReader(new FileReader(new File(filePath)));) {
			String line = null;
			boolean initSlotOnce = true;
			while (StringUtils.isNotBlank((line = bf.readLine()))) {

				if (initSlotOnce && checkPattern(line, "create_parking_lot")) {
					initSlotOnce = initializeCarParking(line);
				} else if (null != parkinglotAllocation && checkPattern(line, "park")) {
					parkVehicle(line);
				} else if (parkingLotDeallocation != null && checkPattern(line, "leave")) {
					unParkVehicle(line);
				} else if (null != parkinglotAllocation && checkPattern(line, "status")) {
					println("Slot No. \t Registration No.");
					List<ParkingSlot> slots = parkinglotAllocation.status();
					slots.stream().forEach(x -> println(x.toString()));
				}
			}

		} catch (IOException e) {
			logger.log(Level.SEVERE, String.format("file processing error :: %s", filePath), e);
		}
	}

	/**
	 * This method initialises data for carparking.
	 * 
	 * @param line
	 * @return
	 */
	private boolean initializeCarParking(String line) {
		boolean initSlotOnce;
		String slotLength = parseForInput(line, "create_parking_lot").get(0);
		if (NumberUtils.isDigits(slotLength)) {
			deque = new ArrayDeque<>(Integer.parseInt(slotLength));
			for (int i = 0; i < Integer.parseInt(slotLength); i++) {
				deque.addLast(Integer.valueOf(i));
			}
			parkinglotAllocation.initParking(Integer.parseInt(slotLength));
			parkingLotDeallocation.initUnParking();
			println(String.format("Created parking lot with %s slots", slotLength));
		}
		initSlotOnce = false;
		return initSlotOnce;
	}

	/**
	 * This method unparks the car.
	 * 
	 * @param line
	 */
	private void unParkVehicle(String line) {

		List<String> registrationNumberAndHour = parseForInput(line, "leave");
		String registrationNumber = registrationNumberAndHour.get(0);
		int durationHour = NumberUtils.isDigits(registrationNumberAndHour.get(1))
				? Integer.parseInt(registrationNumberAndHour.get(1))
				: 0;
		Vehicle vehicle = new Vehicle(registrationNumber);
		int parkSlotIndex = parkinglotAllocation.getAllocatedSlots().indexOf(new ParkingSlot(vehicle));
		if(-1 == parkSlotIndex){
	         return;
	     }
		ParkingSlot slot = parkingLotDeallocation.unpark(vehicle, durationHour,
				parkinglotAllocation.getAllocatedSlots());

		deque.addLast(parkSlotIndex);

		if (slot == null) {
			println(String.format("Registration number %s not found", registrationNumber));
		} else {
			println(String.format("Registration number %s " + "with Slot Number %s is free with Charge %s",
					registrationNumber, slot.getPosition() + 1, slot.getPrice()));
		}
	}

	/**
	 * This method parks the car.
	 * 
	 * @param line
	 */
	private void parkVehicle(String line) {
		if (!this.deque.isEmpty()) {
			int position = this.deque.pop();
			String registrationNumber = parseForInput(line, "park").get(0);
			ParkingSlot parkingSlot = new ParkingSlot(new Vehicle(registrationNumber), position);
			parkinglotAllocation.park(parkingSlot, position);
			println(String.format("Allocated slot number: %s", parkingSlot.getPosition() + 1));
		} else {
			println(String.format("Sorry, parking lot is full"));
		}
	}

	/**
	 * @param line
	 * @param pattern
	 * @return
	 */
	private static List<String> parseForInput(String line, String pattern) {
		String regex = pattern + "|\\s+";

		return Arrays.stream(line.split(regex)).filter(StringUtils::isNotBlank).map(x -> x.trim())
				.collect(Collectors.toList());
	}

	/**
	 * @param line
	 * @param pattern
	 * @return
	 */
	private static boolean checkPattern(String line, String pattern) {
		Pattern p = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
		return p.matcher(line).find();
	}

	/**
	 * @param string
	 */
	private static void println(String string) {
		System.out.println(string);
	}

}
