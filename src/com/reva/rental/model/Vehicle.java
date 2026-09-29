package com.reva.rental.model;

import com.reva.rental.interfaces.Rentable;

/**
 * Abstract Class: Vehicle
 * 
 * Demonstrates Core OOP Concepts (Unit I & II):
 * 1. Abstraction: Cannot be instantiated directly; provides template for specific vehicle types.
 * 2. Interface Implementation: Implements 'Rentable' interface contract.
 * 3. Encapsulation: Protected/private state variables with validated accessor and mutator methods.
 * 4. Polymorphism: Declares the abstract method 'calculateRentalCost(int days)' which every subclass
 *    overrides with specialized pricing logic.
 * 5. Static Members: Tracks global fleet count and currently rented vehicles.
 * 6. 'this' Keyword: Used for constructor chaining and distinguishing instance fields.
 * 
 * Student: AVULU LOKESH (SRN: R24SA007)
 * REVA University - B.Sc. (BSTCs) Semester V
 */
public abstract class Vehicle implements Rentable {

    // Static Fleet Counters (Static Members)
    private static int totalVehiclesInFleet = 0;
    private static int currentlyRentedCount = 0;

    // Encapsulated state fields (protected allows access to subclasses: Car, Motorcycle, Truck)
    protected String vehicleId;
    protected String brand;
    protected String model;
    protected int manufacturingYear;
    protected double baseDailyRate; // In Indian Rupees (₹)
    protected boolean isAvailable;
    protected Customer currentRenter;
    protected VehicleType vehicleType; // Enum integration (Requirement 1)

    /**
     * Overloaded Constructor (Convenience: defaults year to 2023):
     * Demonstrates Constructor Overloading and 'this(...)' Constructor Chaining.
     */
    public Vehicle(String vehicleId, String brand, String model, double baseDailyRate, VehicleType vehicleType) {
        this(vehicleId, brand, model, 2023, baseDailyRate, vehicleType);
    }

    /**
     * Full Parameterized Superclass Constructor:
     * Subclasses invoke this using 'super(...)'.
     */
    public Vehicle(String vehicleId, String brand, String model, int manufacturingYear, double baseDailyRate, VehicleType vehicleType) {
        this.vehicleId = vehicleId;
        this.brand = brand;
        this.model = model;
        this.manufacturingYear = manufacturingYear;
        this.baseDailyRate = (baseDailyRate > 0) ? baseDailyRate : 500.0;
        this.vehicleType = (vehicleType != null) ? vehicleType : VehicleType.CAR;
        this.isAvailable = true;
        this.currentRenter = null;

        // Increment static fleet counter
        totalVehiclesInFleet++;
    }

    /**
     * Overloaded Constructor accepting String for backwards compatibility:
     */
    public Vehicle(String vehicleId, String brand, String model, int manufacturingYear, double baseDailyRate, String typeStr) {
        this(vehicleId, brand, model, manufacturingYear, baseDailyRate, parseVehicleType(typeStr));
    }

    /**
     * Helper to safely map String to VehicleType enum.
     */
    public static VehicleType parseVehicleType(String typeStr) {
        if (typeStr == null) return VehicleType.CAR;
        for (VehicleType vt : VehicleType.values()) {
            if (vt.name().equalsIgnoreCase(typeStr.trim()) || vt.getDisplayName().equalsIgnoreCase(typeStr.trim())) {
                return vt;
            }
        }
        return VehicleType.CAR;
    }

    // ==========================================
    // ABSTRACT METHOD (Polymorphism / Abstraction)
    // ==========================================
    
    /**
     * Abstract Method: Must be implemented by all derived classes (Car, Motorcycle, Truck).
     * Demonstrates Dynamic Method Dispatch (Runtime Polymorphism).
     *
     * @param rentalDays Total days of rental
     * @return Gross rental charges in INR
     */
    public abstract double calculateRentalCost(int rentalDays);

    // ==========================================
    // RENTABLE INTERFACE METHODS IMPLEMENTATION
    // ==========================================

    @Override
    public boolean rentVehicle(Customer customer, int days) {
        if (!this.isAvailable) {
            return false;
        }
        this.isAvailable = false;
        this.currentRenter = customer;
        currentlyRentedCount++;
        return true;
    }

    @Override
    public void returnVehicle() {
        if (!this.isAvailable) {
            this.isAvailable = true;
            this.currentRenter = null;
            if (currentlyRentedCount > 0) {
                currentlyRentedCount--;
            }
        }
    }

    @Override
    public boolean isAvailableForRent() {
        return this.isAvailable;
    }

    // ==========================================
    // CONCRETE METHODS & DISPLAY
    // ==========================================

    /**
     * Base implementation of details printing.
     * Subclasses override and call 'super.displayDetails()' to add specific attributes.
     */
    public void displayDetails() {
        System.out.println("  Vehicle ID   : " + this.vehicleId);
        System.out.println("  Type         : " + this.vehicleType);
        System.out.println("  Make & Model : " + this.brand + " " + this.model + " (" + this.manufacturingYear + ")");
        System.out.printf("  Base Rate    : Rs. %.2f / day%n", this.baseDailyRate);
        System.out.println("  Status       : " + (this.isAvailable ? "AVAILABLE" : "RENTED OUT (to " + (currentRenter != null ? currentRenter.getName() : "Customer") + ")"));
    }

    /**
     * Formatted single-row summary for tabular CLI viewing.
     */
    public void displayTableRow() {
        String statusStr = isAvailable ? "Available" : "Rented";
        System.out.printf("| %-9s | %-11s | %-12s | %-14s | %-4d | Rs. %-7.2f | %-11s |%n",
                vehicleId, vehicleType, brand, model, manufacturingYear, baseDailyRate, statusStr);
    }

    // ==========================================
    // GETTERS AND SETTERS (Encapsulation)
    // ==========================================

    /**
     * Final Method: Prevents subclasses from altering the unique vehicle identity logic.
     * Demonstrates the 'final' method keyword (Requirement 6).
     */
    public final String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getManufacturingYear() {
        return manufacturingYear;
    }

    public void setManufacturingYear(int manufacturingYear) {
        if (manufacturingYear >= 1990 && manufacturingYear <= 2026) {
            this.manufacturingYear = manufacturingYear;
        }
    }

    public double getBaseDailyRate() {
        return baseDailyRate;
    }

    public void setBaseDailyRate(double baseDailyRate) {
        if (baseDailyRate > 0) {
            this.baseDailyRate = baseDailyRate;
        }
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public Customer getCurrentRenter() {
        return currentRenter;
    }

    public void setCurrentRenter(Customer currentRenter) {
        this.currentRenter = currentRenter;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public static int getTotalVehiclesInFleet() {
        return totalVehiclesInFleet;
    }

    public static int getCurrentlyRentedCount() {
        return currentlyRentedCount;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Vehicle)) return false;
        Vehicle other = (Vehicle) obj; // Explicit reference downcasting (Requirement 3)
        return vehicleId != null && vehicleId.equalsIgnoreCase(other.vehicleId);
    }

    @Override
    public int hashCode() {
        return (vehicleId != null) ? vehicleId.toUpperCase().hashCode() : 0;
    }

    @Override
    public String toString() {
        return "[" + vehicleType + "] " + vehicleId + " - " + brand + " " + model + " (" + manufacturingYear + ")";
    }
}
