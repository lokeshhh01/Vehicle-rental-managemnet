package com.reva.rental.model;

import com.reva.rental.interfaces.Billable;
import com.reva.rental.interfaces.Rentable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Class: RentalRecord
 * 
 * Demonstrates Core OOP Concepts (Unit I & II):
 * 1. Interface Implementation: Implements 'Billable' to generate professional customer invoices.
 * 2. Composition (Has-A Relationship): Contains references to 'Vehicle' and 'Customer'.
 * 3. Encapsulation: Protects monetary and status transactions with private variables.
 * 4. Static Members: 'rentalCounter' generates sequential invoice IDs.
 * 5. Formatting & Calculations: Computes gross charges, discounts, GST tax, and late return penalties.
 * 
 * Student: AVULU LOKESH (SRN: R24SA007)
 * REVA University - B.Sc. (BSTCs) Semester V
 */
public class RentalRecord implements Billable {

    // Static counter for rental agreements (Static Member)
    private static int rentalCounter = 2000;

    // Domain-level final constant for GST taxation rate (Requirement 7)
    public static final double GST_PERCENTAGE = 5.0;

    // Association / Composition fields
    private String rentalId;
    private Vehicle vehicle;
    private Customer customer;
    
    // Rental timeline & terms
    private int plannedDays;
    private int actualDaysUsed;
    private String rentalDate;
    private String expectedReturnDate;
    private String actualReturnDate;
    private boolean isReturned;

    // Financial breakdown fields
    private double grossRentalAmount;
    private double discountPercentage;
    private double discountAmount;
    private double gstPercentage; // Goods & Services Tax percentage
    private double gstAmount;
    private double securityDeposit;
    private double netPayableAmount;

    // Return & Late fees
    private int lateDays;
    private double lateFeePerDay;
    private double totalLateFee;
    private double finalSettledAmount;

    /**
     * Parameterized Constructor:
     * Generates a new rental agreement and calculates initial bill.
     */
    public RentalRecord(Vehicle vehicle, Customer customer, int plannedDays, double discountPercentage) {
        this.rentalId = "RENT-" + (++rentalCounter);
        this.vehicle = vehicle;
        this.customer = customer;
        this.plannedDays = plannedDays;
        this.actualDaysUsed = plannedDays;
        this.discountPercentage = discountPercentage;
        this.gstPercentage = GST_PERCENTAGE; // Uses domain constant (Requirement 7)
        this.isReturned = false;
        this.lateDays = 0;
        this.totalLateFee = 0.0;

        // Date handling
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
        this.rentalDate = today.format(formatter);
        this.expectedReturnDate = today.plusDays(plannedDays).format(formatter);
        this.actualReturnDate = "Pending";

        // Security deposit based on vehicle category with reference downcasting (Requirement 3)
        if (vehicle instanceof Car) {
            Car car = (Car) vehicle; // Explicit reference downcasting
            this.securityDeposit = (car.getSeatingCapacity() > 5) ? 2500.0 : 2000.0;
            this.lateFeePerDay = 500.0;
        } else if (vehicle instanceof Motorcycle) {
            Motorcycle bike = (Motorcycle) vehicle; // Explicit reference downcasting
            this.securityDeposit = (bike.getEngineCapacityCC() > 350) ? 1500.0 : 1000.0;
            this.lateFeePerDay = 250.0;
        } else if (vehicle instanceof Truck) {
            Truck truck = (Truck) vehicle; // Explicit reference downcasting
            this.securityDeposit = (truck.getCargoCapacityTons() > 3.0) ? 4000.0 : 3500.0;
            this.lateFeePerDay = 800.0;
        } else {
            this.securityDeposit = 2000.0;
            this.lateFeePerDay = 500.0;
        }

        // Calculate billing immediately
        recalculateBill();
    }

    /**
     * Calculates gross cost (polymorphic call vehicle.calculateRentalCost),
     * discounts, GST, and net payable.
     */
    public void recalculateBill() {
        // Polymorphic method call - dynamically dispatches to Car, Motorcycle, or Truck!
        this.grossRentalAmount = vehicle.calculateRentalCost(this.plannedDays);

        // Apply discount percentage
        this.discountAmount = (this.grossRentalAmount * (this.discountPercentage / 100.0));
        double amountAfterDiscount = this.grossRentalAmount - this.discountAmount;

        // Apply GST using the domain constant
        this.gstAmount = (amountAfterDiscount * (GST_PERCENTAGE / 100.0));

        // Net rental charges payable
        this.netPayableAmount = amountAfterDiscount + this.gstAmount;
        this.finalSettledAmount = this.netPayableAmount;
    }

    /**
     * Finalizes the return of the rented vehicle.
     * Computes late fees if vehicle was kept longer than agreed duration.
     */
    public void markAsReturned(int daysActual) {
        this.isReturned = true;
        this.actualDaysUsed = daysActual;
        this.actualReturnDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MMM-yyyy"));

        if (daysActual > plannedDays) {
            this.lateDays = daysActual - plannedDays;
            this.totalLateFee = this.lateDays * this.lateFeePerDay;
        } else {
            this.lateDays = 0;
            this.totalLateFee = 0.0;
        }

        this.finalSettledAmount = this.netPayableAmount + this.totalLateFee;
        
        // Restore vehicle state using interface reference polymorphism (Requirement 4)
        Rentable rentableVehicle = vehicle;
        rentableVehicle.returnVehicle();
        customer.incrementRentalCount();
    }

    // ==========================================
    // BILLABLE INTERFACE IMPLEMENTATION
    // ==========================================

    @Override
    public double getNetPayableAmount() {
        return this.finalSettledAmount;
    }

    /**
     * Prints an itemized, elegant ASCII rental bill.
     */
    @Override
    public void printInvoice() {
        System.out.println("\n=======================================================================");
        System.out.println("               CITYDRIVE VEHICLE RENTALS & LOGISTICS                  ");
        System.out.println("               REVA University Java Programming Mini-Project           ");
        System.out.println("-----------------------------------------------------------------------");
        System.out.printf("  Invoice Number  : %-20s  Date Issued : %s%n", rentalId, rentalDate);
        System.out.printf("  Rental Status   : %-20s  Return Date : %s%n", 
                (isReturned ? "COMPLETED / RETURNED" : "ACTIVE / ON RENT"), 
                (isReturned ? actualReturnDate : expectedReturnDate + " (Expected)"));
        System.out.println("-----------------------------------------------------------------------");
        System.out.println("  [CUSTOMER DETAILS]");
        System.out.printf("  Customer ID     : %-20s  Name        : %s%n", customer.getCustomerId(), customer.getName());
        System.out.printf("  Contact Phone   : %-20s  License No  : %s%n", customer.getPhoneNumber(), customer.getLicenseNumber());
        System.out.printf("  Membership      : %-20s  Rentals Done: %d%n", customer.getMembershipType(), customer.getTotalRentalsCompleted());
        System.out.println("-----------------------------------------------------------------------");
        System.out.println("  [VEHICLE DETAILS]");
        System.out.printf("  Vehicle ID      : %-20s  Category    : %s%n", vehicle.getVehicleId(), vehicle.getVehicleType());
        System.out.printf("  Model & Make    : %s %s (%d)%n", vehicle.getBrand(), vehicle.getModel(), vehicle.getManufacturingYear());
        System.out.printf("  Base Daily Rate : Rs. %.2f / day%n", vehicle.getBaseDailyRate());
        System.out.println("-----------------------------------------------------------------------");
        System.out.println("  [BILLING & PAYMENT BREAKDOWN]");
        System.out.printf("  Agreed Duration          : %d Days%n", plannedDays);
        System.out.printf("  Gross Rental Subtotal    : Rs. %10.2f%n", grossRentalAmount);
        System.out.printf("  Discount Applied (%4.1f%%) : -Rs. %9.2f%n", discountPercentage, discountAmount);
        System.out.printf("  GST Tax (CGST+SGST %3.1f%%): +Rs. %9.2f%n", gstPercentage, gstAmount);
        System.out.println("  .....................................................................");
        System.out.printf("  Net Rental Charges       : Rs. %10.2f%n", netPayableAmount);
        System.out.printf("  Refundable Security Dep. : Rs. %10.2f (Hold)%n", securityDeposit);

        if (isReturned && lateDays > 0) {
            System.out.println("  .....................................................................");
            System.out.printf("  Late Return Penalty (%d d): +Rs. %9.2f (Rs. %.2f/day)%n", lateDays, totalLateFee, lateFeePerDay);
            System.out.printf("  TOTAL SETTLEMENT AMOUNT  : Rs. %10.2f%n", finalSettledAmount);
        } else {
            System.out.printf("  TOTAL PAYABLE TODAY      : Rs. %10.2f%n", finalSettledAmount);
        }

        // Explicit narrowing primitive type casting: double -> int (Requirement 3)
        int roundedTotal = (int) finalSettledAmount;
        System.out.printf("  ROUNDED CASH TOTAL       : Rs. %,d%n", roundedTotal);
        System.out.println("=======================================================================");
        System.out.println("  Thank you for choosing CityDrive Rentals! Safe Journey & Drive Safe! ");
        System.out.println("  Developed by: AVULU LOKESH (SRN: R24SA007) - REVA University         ");
        System.out.println("=======================================================================\n");
    }

    /**
     * Formatted row for table listing.
     */
    public void displayTableRow() {
        String statusStr = isReturned ? "Returned" : "Active";
        System.out.printf("| %-10s | %-9s | %-12s | %-15s | %-4d | Rs. %-8.2f | %-8s |%n",
                rentalId, vehicle.getVehicleId(), customer.getCustomerId(),
                customer.getName(), plannedDays, finalSettledAmount, statusStr);
    }

    // ==========================================
    // GETTERS AND SETTERS
    // ==========================================

    public String getRentalId() {
        return rentalId;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public Customer getCustomer() {
        return customer;
    }

    public int getPlannedDays() {
        return plannedDays;
    }

    public int getActualDaysUsed() {
        return actualDaysUsed;
    }

    public boolean isReturned() {
        return isReturned;
    }

    public double getGrossRentalAmount() {
        return grossRentalAmount;
    }

    public double getDiscountPercentage() {
        return discountPercentage;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public double getNetPayableAmountField() {
        return netPayableAmount;
    }

    public double getSecurityDeposit() {
        return securityDeposit;
    }

    public int getLateDays() {
        return lateDays;
    }

    public double getTotalLateFee() {
        return totalLateFee;
    }

    public double getFinalSettledAmount() {
        return finalSettledAmount;
    }

    /**
     * Demonstrates explicit narrowing primitive type casting (Requirement 3).
     * Converts floating-point finalSettledAmount to whole integer value.
     */
    public int getRoundedSettledAmount() {
        int roundedTotal = (int) this.finalSettledAmount; // Explicit narrowing primitive cast: double -> int (Requirement 3)
        return roundedTotal;
    }

    public String getRentalDate() {
        return rentalDate;
    }

    public static int getRentalCounter() {
        return rentalCounter;
    }
}
