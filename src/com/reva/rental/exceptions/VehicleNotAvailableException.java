package com.reva.rental.exceptions;

/**
 * Custom Exception: VehicleNotAvailableException
 * 
 * Demonstrates Exception Handling:
 * Thrown when attempting to rent a vehicle that is currently already rented out or undergoing maintenance.
 * 
 * Student: AVULU LOKESH (SRN: R24SA007)
 * REVA University - B.Sc. (BSTCs) Semester V
 */
public class VehicleNotAvailableException extends Exception {
    
    public VehicleNotAvailableException(String message) {
        super(message);
    }

    public VehicleNotAvailableException(String vehicleId, String message) {
        super("Vehicle ID [" + vehicleId + "] is unavailable: " + message);
    }
}
