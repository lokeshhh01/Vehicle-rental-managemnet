package com.reva.rental.service;

import com.reva.rental.exceptions.InvalidRentalDurationException;
import com.reva.rental.exceptions.VehicleNotAvailableException;
import com.reva.rental.exceptions.VehicleNotFoundException;
import com.reva.rental.interfaces.Discountable;
import com.reva.rental.interfaces.Rentable;
import com.reva.rental.model.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Class: RentalAgency
 * 
 * Demonstrates Core OOP Concepts (Unit I & II):
 * 1. Interface Implementation: Implements 'Discountable' to calculate dynamic discounts.
 * 2. Polymorphic Collections: Manages ArrayList<Vehicle> holding Car, Motorcycle, and Truck instances.
 * 3. Method Overloading (Compile-Time Polymorphism):
 *    - searchVehicles(String keyword)
 *    - searchVehicles(String type, double maxDailyRate)
 *    - searchVehicles(boolean availableOnly)
 *    - registerCustomer(...) overloaded versions
 * 4. Exception Handling: Methods throw and handle custom exceptions (VehicleNotFoundException,
 *    VehicleNotAvailableException, InvalidRentalDurationException).
 * 5. Business Logic & Aggregation: Central hub managing vehicles, customers, and rental transactions.
 * 
 * Student: AVULU LOKESH (SRN: R24SA007)
 * REVA University - B.Sc. (BSTCs) Semester V
 */
public class RentalAgency implements Discountable {

    private String agencyName;
    private String branchLocation;
    
    // Collections (Unit I & II)
    private List<Vehicle> inventory;
    private List<Customer> customers;
    private List<RentalRecord> rentalRecords;

    /**
     * Parameterized Constructor:
     * Initializes lists and seeds default test data.
     */
    public RentalAgency(String agencyName, String branchLocation) {
        this.agencyName = agencyName;
        this.branchLocation = branchLocation;
        this.inventory = new ArrayList<>();
        this.customers = new ArrayList<>();
        this.rentalRecords = new ArrayList<>();

        // Seed realistic initial inventory and customers for viva demo
        seedInitialData();
    }

    /**
     * Populates agency with realistic starter data for practical examination demonstration.
     */
    private void seedInitialData() {
        // Cars (ID, Brand, Model, Year, BaseRate, Seating, Fuel, AC)
        inventory.add(new Car("CAR-101", "Maruti", "Swift ZXi", 2023, 1400.0, 5, "Petrol", true));
        inventory.add(new Car("CAR-102", "Toyota", "Innova Crysta", 2024, 2800.0, 7, "Diesel", true));
        inventory.add(new Car("CAR-103", "Tata", "Nexon EV", 2024, 2200.0, 5, "Electric", true));
        inventory.add(new Car("CAR-104", "Hyundai", "Creta SX", 2023, 2000.0, 5, "Petrol", true));

        // Motorcycles (ID, Brand, Model, Year, BaseRate, CC, Category, IncludesGear)
        inventory.add(new Motorcycle("BIKE-201", "Royal Enfield", "Classic 350", 2023, 1100.0, 349, "Cruiser", true));
        inventory.add(new Motorcycle("BIKE-202", "Yamaha", "YZF R15 V4", 2024, 950.0, 155, "Sports", true));
        inventory.add(new Motorcycle("BIKE-203", "Honda", "Activa 6G", 2023, 500.0, 110, "Commuter", true));

        // Commercial Trucks (ID, Brand, Model, Year, BaseRate, Tons, Axles, HasPermit)
        inventory.add(new Truck("TRK-301", "Tata", "Ace Gold (Chota Hathi)", 2022, 1600.0, 1.5, 2, true));
        inventory.add(new Truck("TRK-302", "Mahindra", "Bolero Maxi Truck", 2023, 2100.0, 2.5, 2, true));
        inventory.add(new Truck("TRK-303", "BharatBenz", "1217R Medium Duty", 2021, 4500.0, 8.0, 3, true));

        // Initial Registered Customers
        Customer c1 = new Customer("CUST-1001", "Rahul Sharma", "9876543210", "KA03202100456", "VIP");
        Customer c2 = new Customer("CUST-1002", "Priya Nair", "9123456780", "KA04202200789", "REGULAR");
        Customer c3 = new Customer("CUST-1003", "Infosys Corp Logistics", "9845098450", "KA01201900112", "CORPORATE");
        customers.add(c1);
        customers.add(c2);
        customers.add(c3);

        // Pre-rent one vehicle (CAR-104 to Priya) to demonstrate active rental scenario during viva
        Vehicle v4 = inventory.get(3); // Creta
        v4.rentVehicle(c2, 3);
        RentalRecord existingRecord = new RentalRecord(v4, c2, 3, 5.0);
        rentalRecords.add(existingRecord);
    }

    // ==========================================
    // 1. VIEW ALL VEHICLES
    // ==========================================

    public void displayAllVehicles() {
        printVehicleTableHeader("ALL FLEET VEHICLES IN INVENTORY");
        if (inventory.isEmpty()) {
            System.out.println("| No vehicles currently registered in the inventory.                           |");
        } else {
            for (Vehicle v : inventory) {
                v.displayTableRow();
            }
        }
        printVehicleTableFooter();
    }

    // ==========================================
    // 2. VIEW AVAILABLE VEHICLES
    // ==========================================

    public void displayAvailableVehicles() {
        printVehicleTableHeader("CURRENTLY AVAILABLE VEHICLES (READY FOR RENT)");
        int count = 0;
        for (Vehicle v : inventory) {
            // Demonstrates genuine 'continue' statement (Requirement 2)
            if (!v.isAvailable()) {
                continue; // Skip rented vehicles and proceed to next iteration
            }
            v.displayTableRow();
            count++;
        }
        if (count == 0) {
            System.out.println("| All vehicles are currently rented out! Please check back later.              |");
        }
        printVehicleTableFooter();
        System.out.printf("  Total Available: %d / %d%n", count, inventory.size());
    }

    // ==========================================
    // 3. SEARCH VEHICLES (Method Overloading)
    // ==========================================

    /**
     * Overloaded Search 1: Search by text keyword (matches Vehicle ID, Brand, or Model).
     * Demonstrates Compile-Time Polymorphism.
     */
    public List<Vehicle> searchVehicles(String keyword) {
        List<Vehicle> matches = new ArrayList<>();
        if (keyword == null || keyword.trim().isEmpty()) {
            return matches;
        }
        String cleanKeyword = keyword.trim().toLowerCase();

        for (Vehicle v : inventory) {
            if (v.getVehicleId().toLowerCase().contains(cleanKeyword) ||
                v.getBrand().toLowerCase().contains(cleanKeyword) ||
                v.getModel().toLowerCase().contains(cleanKeyword)) {
                matches.add(v);
            }
        }
        return matches;
    }

    /**
     * Overloaded Search 2: Search by Vehicle Type ("Car", "Motorcycle", "Truck")
     * and Maximum Daily Budget Rate.
     * Demonstrates Compile-Time Polymorphism.
     */
    public List<Vehicle> searchVehicles(String type, double maxDailyRate) {
        List<Vehicle> matches = new ArrayList<>();
        for (Vehicle v : inventory) {
            boolean matchesType = (type == null || type.equalsIgnoreCase("ALL") ||
                    v.getVehicleType().name().equalsIgnoreCase(type) ||
                    v.getVehicleType().getDisplayName().equalsIgnoreCase(type));
            boolean matchesBudget = (v.getBaseDailyRate() <= maxDailyRate);

            if (matchesType && matchesBudget) {
                matches.add(v);
            }
        }
        return matches;
    }

    /**
     * Overloaded Search 3: Filter vehicles strictly by availability.
     * Demonstrates Compile-Time Polymorphism.
     */
    public List<Vehicle> searchVehicles(boolean availableOnly) {
        List<Vehicle> matches = new ArrayList<>();
        for (Vehicle v : inventory) {
            if (!availableOnly || v.isAvailable()) {
                matches.add(v);
            }
        }
        return matches;
    }

    // ==========================================
    // 4. CUSTOMER MANAGEMENT
    // ==========================================

    /**
     * Overloaded Customer Registration (Default REGULAR membership).
     */
    public Customer registerCustomer(String name, String phone, String license) {
        return registerCustomer(name, phone, license, "REGULAR");
    }

    /**
     * Overloaded Customer Registration with explicit membership type.
     */
    public Customer registerCustomer(String name, String phone, String license, String membershipType) {
        Customer customer = new Customer(name, phone, license, membershipType);
        customers.add(customer);
        return customer;
    }

    public Customer findCustomerById(String customerId) {
        if (customerId == null) return null;
        for (Customer c : customers) {
            if (c.getCustomerId().equalsIgnoreCase(customerId.trim())) {
                return c;
            }
        }
        return null;
    }

    public void displayAllCustomers() {
        System.out.println("\n+------------+----------------------+---------------+----------------+------------+---------+");
        System.out.println("| Cust ID    | Customer Name        | Phone Number  | License Number | Membership | Rentals |");
        System.out.println("+------------+----------------------+---------------+----------------+------------+---------+");
        for (Customer c : customers) {
            c.displayCustomerInfo();
        }
        System.out.println("+------------+----------------------+---------------+----------------+------------+---------+");
        System.out.printf("  Total Registered Customers: %d%n", customers.size());
    }

    // ==========================================
    // 5. VEHICLE LOOKUP & VALIDATION
    // ==========================================

    public Vehicle findVehicleById(String vehicleId) throws VehicleNotFoundException {
        if (vehicleId == null || vehicleId.trim().isEmpty()) {
            throw new VehicleNotFoundException("Vehicle ID cannot be blank.");
        }
        for (Vehicle v : inventory) {
            if (v.getVehicleId().equalsIgnoreCase(vehicleId.trim())) {
                return v;
            }
        }
        throw new VehicleNotFoundException(vehicleId, "No vehicle matching this ID was found in the inventory.");
    }

    // ==========================================
    // 6. RENT A VEHICLE (Core Transaction)
    // ==========================================

    /**
     * Rents a vehicle to a customer after verifying availability and duration.
     * Demonstrates custom exception throwing and handling.
     */
    public RentalRecord rentVehicle(String vehicleId, String customerId, int rentalDays)
            throws VehicleNotFoundException, VehicleNotAvailableException, InvalidRentalDurationException {
        
        // Duration validation
        if (rentalDays <= 0 || rentalDays > 365) {
            throw new InvalidRentalDurationException(rentalDays);
        }

        // Vehicle lookup
        Vehicle vehicle = findVehicleById(vehicleId);

        // Check if available
        if (!vehicle.isAvailable()) {
            throw new VehicleNotAvailableException(vehicleId, "Vehicle is currently rented out to another customer.");
        }

        // Customer lookup
        Customer customer = findCustomerById(customerId);
        if (customer == null) {
            throw new IllegalArgumentException("Customer ID [" + customerId + "] not found. Please register first.");
        }

        // Calculate suitable discount based on Discountable interface policy
        double grossCost = vehicle.calculateRentalCost(rentalDays);
        double discountAmount = calculateDiscount(grossCost, rentalDays, customer);
        double discountPercent = (grossCost > 0) ? (discountAmount / grossCost) * 100.0 : 0.0;

        // Perform rental state update using interface reference polymorphism (Requirement 4)
        Rentable rentableVehicle = vehicle; // Variable declared using interface type
        boolean rented = rentableVehicle.rentVehicle(customer, rentalDays);
        if (!rented) {
            throw new VehicleNotAvailableException(vehicleId, "Failed to reserve vehicle.");
        }

        // Create rental record
        RentalRecord record = new RentalRecord(vehicle, customer, rentalDays, discountPercent);
        rentalRecords.add(record);

        return record;
    }

    // ==========================================
    // 7. RETURN A VEHICLE
    // ==========================================

    /**
     * Returns an active rental and processes late fees or final settlement.
     */
    public RentalRecord returnVehicle(String rentalOrVehicleId, int actualDaysUsed)
            throws VehicleNotFoundException {
        
        RentalRecord targetRecord = null;

        // Search by Rental ID first
        for (RentalRecord r : rentalRecords) {
            if (!r.isReturned()) {
                if (r.getRentalId().equalsIgnoreCase(rentalOrVehicleId.trim()) ||
                    r.getVehicle().getVehicleId().equalsIgnoreCase(rentalOrVehicleId.trim())) {
                    targetRecord = r;
                    break;
                }
            }
        }

        if (targetRecord == null) {
            throw new VehicleNotFoundException("No active rental record found matching [" + rentalOrVehicleId + "].");
        }

        targetRecord.markAsReturned(actualDaysUsed);
        return targetRecord;
    }

    // ==========================================
    // 8. DISCOUNTABLE INTERFACE IMPLEMENTATION
    // ==========================================

    /**
     * Calculates discount based on duration tiers and customer loyalty.
     */
    @Override
    public double calculateDiscount(double grossAmount, int rentalDays, Customer customer) {
        double discountRate = 0.0;

        // Duration Tier
        if (rentalDays >= 14) {
            discountRate += 15.0; // 15% discount for 2+ weeks
        } else if (rentalDays >= 7) {
            discountRate += 10.0; // 10% discount for weekly rentals
        } else if (rentalDays >= 3) {
            discountRate += 5.0;  // 5% discount for 3+ days
        }

        // Loyalty membership tier
        if (customer != null) {
            if ("VIP".equalsIgnoreCase(customer.getMembershipType())) {
                discountRate += 5.0; // Additional 5% for VIP members
            } else if ("CORPORATE".equalsIgnoreCase(customer.getMembershipType())) {
                discountRate += 7.0; // Additional 7% for Corporate clients
            }
        }

        // Cap maximum discount at 30%
        if (discountRate > 30.0) {
            discountRate = 30.0;
        }

        return (grossAmount * (discountRate / 100.0));
    }

    // ==========================================
    // 9. VIEW RENTAL RECORDS & STATISTICS
    // ==========================================

    public void displayRentalRecords(boolean activeOnly) {
        String title = activeOnly ? "ACTIVE RENTAL CONTRACTS (VEHICLES ON THE ROAD)" : "ALL RENTAL HISTORY & INVOICE RECORDS";
        System.out.println("\n+------------+-----------+--------------+-----------------+------+------------+----------+");
        System.out.printf("| %-86s |%n", title);
        System.out.println("+------------+-----------+--------------+-----------------+------+------------+----------+");
        System.out.println("| Rental ID  | Veh. ID   | Cust. ID     | Customer Name   | Days | Total Net  | Status   |");
        System.out.println("+------------+-----------+--------------+-----------------+------+------------+----------+");
        
        int count = 0;
        for (RentalRecord r : rentalRecords) {
            if (!activeOnly || !r.isReturned()) {
                r.displayTableRow();
                count++;
            }
        }

        if (count == 0) {
            System.out.println("| No matching rental agreements found.                                                 |");
        }
        System.out.println("+------------+-----------+--------------+-----------------+------+------------+----------+");
        System.out.printf("  Total Records Displayed: %d%n", count);
    }

    /**
     * Agency Fleet & Financial Overview
     */
    public void displaySystemStatistics() {
        double totalRevenue = 0.0;
        int activeCount = 0;
        int completedCount = 0;

        for (RentalRecord r : rentalRecords) {
            totalRevenue += r.getFinalSettledAmount();
            if (r.isReturned()) {
                completedCount++;
            } else {
                activeCount++;
            }
        }

        System.out.println("\n=======================================================================");
        System.out.println("                 AGENCY SYSTEM PERFORMANCE & METRICS                   ");
        System.out.println("=======================================================================");
        System.out.println("  Agency Name         : " + this.agencyName);
        System.out.println("  Location / Branch   : " + this.branchLocation);
        System.out.println("-----------------------------------------------------------------------");
        System.out.println("  Total Fleet Size    : " + inventory.size() + " Vehicles");
        System.out.println("  Available in Fleet  : " + (inventory.size() - Vehicle.getCurrentlyRentedCount()) + " Vehicles");
        System.out.println("  Currently Rented    : " + Vehicle.getCurrentlyRentedCount() + " Vehicles");
        System.out.println("-----------------------------------------------------------------------");
        System.out.println("  Registered Clients  : " + customers.size() + " Customers");
        System.out.println("  Active Contracts    : " + activeCount + " Rentals");
        System.out.println("  Completed Contracts : " + completedCount + " Rentals");
        System.out.printf("  Total Revenue Earned: Rs. %,.2f%n", totalRevenue);
        System.out.println("=======================================================================\n");
    }

    // ==========================================
    // HELPER TABLE FORMATTING METHODS
    // ==========================================

    private void printVehicleTableHeader(String title) {
        System.out.println("\n+-----------+-------------+--------------+----------------+------+-------------+-------------+");
        System.out.printf("| %-89s |%n", title);
        System.out.println("+-----------+-------------+--------------+----------------+------+-------------+-------------+");
        System.out.println("| Veh. ID   | Type        | Make         | Model          | Year | Daily Rate  | Status      |");
        System.out.println("+-----------+-------------+--------------+----------------+------+-------------+-------------+");
    }

    private void printVehicleTableFooter() {
        System.out.println("+-----------+-------------+--------------+----------------+------+-------------+-------------+");
    }

    public List<Vehicle> getInventory() {
        return inventory;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public List<RentalRecord> getRentalRecords() {
        return rentalRecords;
    }
}
