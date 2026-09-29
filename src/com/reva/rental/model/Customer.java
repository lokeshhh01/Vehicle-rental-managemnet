package com.reva.rental.model;

/**
 * Class: Customer
 * 
 * Demonstrates Core OOP Concepts (Unit I & II):
 * 1. Encapsulation: Private member variables with public getters and setters.
 * 2. Constructors: Constructor overloading (default, parameterized) and constructor chaining using 'this(...)'.
 * 3. 'this' Keyword: Used to reference instance variables and invoke overloaded constructors.
 * 4. Static Members: 'customerCounter' tracks the number of registered customers.
 * 
 * Student: AVULU LOKESH (SRN: R24SA007)
 * REVA University - B.Sc. (BSTCs) Semester V
 */
public class Customer {

    // Static counter to generate unique sequential Customer IDs (Static Member)
    private static int customerCounter = 1000;

    // Encapsulated instance fields (private access specifier)
    private String customerId;
    private String name;
    private String phoneNumber;
    private String licenseNumber;
    private String membershipType; // "REGULAR", "VIP", "CORPORATE"
    private int totalRentalsCompleted;

    /**
     * Default Constructor:
     * Chains to the 4-argument constructor using 'this(...)'.
     */
    public Customer() {
        this("Guest Customer", "0000000000", "UNKNOWN", "REGULAR");
    }

    /**
     * Overloaded Constructor (Convenience: defaults membership to REGULAR):
     * Demonstrates Constructor Overloading and Constructor Chaining with 'this(...)'.
     */
    public Customer(String name, String phoneNumber, String licenseNumber) {
        this(name, phoneNumber, licenseNumber, "REGULAR");
    }

    /**
     * Full Parameterized Constructor:
     * Uses 'this' keyword to resolve shadowing between parameter names and instance variables.
     */
    public Customer(String name, String phoneNumber, String licenseNumber, String membershipType) {
        this.customerId = "CUST-" + (++customerCounter);
        this.name = (name != null && !name.trim().isEmpty()) ? name.trim() : "Unnamed Customer";
        this.phoneNumber = (phoneNumber != null && !phoneNumber.trim().isEmpty()) ? phoneNumber.trim() : "N/A";
        this.licenseNumber = (licenseNumber != null && !licenseNumber.trim().isEmpty()) ? licenseNumber.trim().toUpperCase() : "N/A";
        this.membershipType = (membershipType != null && !membershipType.trim().isEmpty()) ? membershipType.trim().toUpperCase() : "REGULAR";
        this.totalRentalsCompleted = 0;
    }

    /**
     * Overloaded Constructor that accepts an existing ID (for pre-loading sample records):
     */
    public Customer(String customId, String name, String phoneNumber, String licenseNumber, String membershipType) {
        this.customerId = customId;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.licenseNumber = licenseNumber;
        this.membershipType = membershipType;
        this.totalRentalsCompleted = 0;

        // Keep customerCounter aligned with custom IDs
        try {
            if (customId != null && customId.startsWith("CUST-")) {
                int idNum = Integer.parseInt(customId.substring(5));
                if (idNum > customerCounter) {
                    customerCounter = idNum;
                }
            }
        } catch (NumberFormatException ignored) {}
    }

    // ==========================================
    // GETTERS AND SETTERS (Encapsulation)
    // ==========================================

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name != null && !name.trim().isEmpty()) {
            this.name = name.trim();
        }
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        if (phoneNumber != null && phoneNumber.trim().length() >= 10) {
            this.phoneNumber = phoneNumber.trim();
        }
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        if (licenseNumber != null && !licenseNumber.trim().isEmpty()) {
            this.licenseNumber = licenseNumber.trim().toUpperCase();
        }
    }

    public String getMembershipType() {
        return membershipType;
    }

    public void setMembershipType(String membershipType) {
        if (membershipType != null) {
            this.membershipType = membershipType.trim().toUpperCase();
        }
    }

    public int getTotalRentalsCompleted() {
        return totalRentalsCompleted;
    }

    public void incrementRentalCount() {
        this.totalRentalsCompleted++;
        // Auto-upgrade membership if frequent renter
        if (this.totalRentalsCompleted >= 3 && !"CORPORATE".equals(this.membershipType)) {
            this.membershipType = "VIP";
        }
    }

    public static int getCustomerCounter() {
        return customerCounter;
    }

    /**
     * Checks if customer qualifies for special loyalty discounts.
     */
    public boolean isLoyaltyMember() {
        return "VIP".equalsIgnoreCase(membershipType) || "CORPORATE".equalsIgnoreCase(membershipType);
    }

    /**
     * Displays clean customer summary.
     */
    public void displayCustomerInfo() {
        System.out.printf("| %-10s | %-20s | %-13s | %-14s | %-10s | %-7d |%n",
                customerId, name, phoneNumber, licenseNumber, membershipType, totalRentalsCompleted);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Customer)) return false;
        Customer customer = (Customer) obj; // Explicit reference downcasting (Requirement 3)
        return customerId != null && customerId.equalsIgnoreCase(customer.customerId);
    }

    @Override
    public int hashCode() {
        return (customerId != null) ? customerId.toUpperCase().hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "ID='" + customerId + '\'' +
                ", Name='" + name + '\'' +
                ", Phone='" + phoneNumber + '\'' +
                ", License='" + licenseNumber + '\'' +
                ", Type='" + membershipType + '\'' +
                ", Rentals=" + totalRentalsCompleted +
                '}';
    }
}
