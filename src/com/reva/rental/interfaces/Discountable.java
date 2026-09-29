package com.reva.rental.interfaces;

import com.reva.rental.model.Customer;

/**
 * Interface: Discountable
 * 
 * Demonstrates Abstraction & Interface Design:
 * Provides standard methods for calculating discounts based on duration and customer loyalty.
 * 
 * Student: AVULU LOKESH (SRN: R24SA007)
 * REVA University - B.Sc. (BSTCs) Semester V
 */
public interface Discountable {
    
    /**
     * Calculates the discount amount based on base cost and duration.
     * 
     * @param grossAmount The gross rental cost before discounts
     * @param rentalDays Total duration in days
     * @param customer Customer profile with loyalty status
     * @return Discount value to subtract from total
     */
    double calculateDiscount(double grossAmount, int rentalDays, Customer customer);
}
