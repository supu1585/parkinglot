package com.dkatalis.parkinglot.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.dkatalis.parkinglot.service.CarParkingApp;

@SpringBootApplication
@ComponentScan("com.dkatalis.parkinglot")
public class ParkingLotApplication implements CommandLineRunner{
	
	@Autowired
	CarParkingApp carParkingApp;
	
	public static void main(String[] args) {
		SpringApplication.run(ParkingLotApplication.class, args);
	}
	 @Override
	    public void run(String... args) {
	        carParkingApp.carParking(args);
	    }
}
