package com.dkatalis.parkinglot.model;


import java.math.BigDecimal;

import lombok.*;

@Builder
@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
//@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ParkingSlot {

    @EqualsAndHashCode.Include
    private Vehicle vehicle;

    @Builder.Default private int position = -1;

    @Builder.Default private int durationHour = 0;

    @Builder.Default private BigDecimal price = new BigDecimal("0");

    public ParkingSlot(Vehicle v, int position){
        this.vehicle =v;
        this.position = position;
    }

    public ParkingSlot(Vehicle v){
        this.vehicle =v;
    }

    @Override
    public String toString(){
        return String.format(" %s \t %s",  position + 1, vehicle.getRegistrationNumber());
    }

}
