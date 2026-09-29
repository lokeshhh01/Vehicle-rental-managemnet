package com.reva.rental.interfaces;

import com.reva.rental.model.Customer;

/**
 * Interface: Rentable
 * 
 * Demonstrates Abstraction & Interface Concept (Unit II):
 * Defines the contract that any rentable entity (e.g., Vehicle) must adhere to.
 * All methods in an interface are implicitly public and abstract.
 * 
 * Student: AVULU LOKESH (SRN: R24SA007)
 * REVA University - B.Sc. (BSTCs) Semester V
 */
public interface Rentable {
    
    /**
     * Attempts to rent the vehicle to a specific customer for a given number of days.
     * 
     * @param customer The customer renting the vehicle
     * @param days Number of days for the rental
     * @return true if rental was successful, false otherwise
     */
    boolean rentVehicle(Customer customer, int days);

    /**
     * Marks the vehicle as returned and restores its availability.
     */
    void returnVehicle();

    /**
     * Checks if the vehicle is currently available for rent.
     * 
     * @return true if available, false if rented or in maintenance
     */
    boolean isAvailableForRent();
}
