package com.reva.rental.interfaces;

/**
 * Interface: Billable
 * 
 * Demonstrates Interface implementation for invoicing.
 * 
 * Student: AVULU LOKESH (SRN: R24SA007)
 * REVA University - B.Sc. (BSTCs) Semester V
 */
public interface Billable {
    
    /**
     * Prints a formatted invoice or billing statement.
     */
    void printInvoice();

    /**
     * Retrieves the net payable amount after taxes and discounts.
     * 
     * @return Net amount in INR (₹)
     */
    double getNetPayableAmount();
}
