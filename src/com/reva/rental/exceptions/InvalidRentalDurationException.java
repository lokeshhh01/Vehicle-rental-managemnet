package com.reva.rental.exceptions;

/**
 * Custom Exception: InvalidRentalDurationException
 * 
 * Demonstrates Exception Handling:
 * Thrown when an invalid number of rental days (such as <= 0 or exceeding maximum limit) is specified.
 * 
 * Student: AVULU LOKESH (SRN: R24SA007)
 * REVA University - B.Sc. (BSTCs) Semester V
 */
public class InvalidRentalDurationException extends Exception {
    
    public InvalidRentalDurationException(String message) {
        super(message);
    }

    public InvalidRentalDurationException(int days) {
        super("Invalid rental duration: " + days + " days. Duration must be between 1 and 365 days.");
    }
}
