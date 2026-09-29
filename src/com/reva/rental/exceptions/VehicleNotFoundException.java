package com.reva.rental.exceptions;

/**
 * Custom Exception: VehicleNotFoundException
 * 
 * Demonstrates Exception Handling (Unit I & II):
 * Thrown when an operation is requested on a vehicle ID that does not exist in the inventory.
 * 
 * Student: AVULU LOKESH (SRN: R24SA007)
 * REVA University - B.Sc. (BSTCs) Semester V
 */
public class VehicleNotFoundException extends Exception {
    
    public VehicleNotFoundException(String message) {
        super(message);
    }

    public VehicleNotFoundException(String vehicleId, String message) {
        super("Vehicle ID [" + vehicleId + "]: " + message);
    }
}
