package com.dkatalis.parkinglot.model;


import lombok.*;

@Builder
@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Vehicle {

	@EqualsAndHashCode.Include
    private String registrationNumber;
	
	
}
