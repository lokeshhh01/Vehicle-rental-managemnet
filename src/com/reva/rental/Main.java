package com.reva.rental;

import com.reva.rental.exceptions.InvalidRentalDurationException;
import com.reva.rental.exceptions.VehicleNotAvailableException;
import com.reva.rental.exceptions.VehicleNotFoundException;
import com.reva.rental.interfaces.Billable;
import com.reva.rental.interfaces.Rentable;
import com.reva.rental.model.Customer;
import com.reva.rental.model.RentalRecord;
import com.reva.rental.model.Vehicle;
import com.reva.rental.service.RentalAgency;
import com.reva.rental.util.ConsoleUtils;

import java.util.List;
import java.util.Scanner;

/**
 * Main Class: Interactive Console Entry Point
 * 
 * Vehicle Rental Management System
 * Academic Practical Project for REVA University
 * 
 * Student Details:
 *   Name       : AVULU LOKESH
 *   SRN        : R24SA007
 *   Program    : B.Sc. (BSTCs)
 *   Semester   : V
 *   Subject    : Java Programming
 * 
 * Demonstrates:
 * - Scanner console input processing
 * - Control flow structures: switch-case, do-while loops, if-else logic
 * - Exception handling: try-catch-finally blocks
 * - Interaction with Encapsulated, Polymorphic OOP services
 */
public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static RentalAgency agency;

    public static void main(String[] args) {
        // Initialize agency with branch details
        agency = new RentalAgency("CityDrive Wheels & Mobility", "Bengaluru North (Yelahanka Branch)");

        boolean running = true;

        try {
            // Demonstrates do-while control loop (Unit I syllabus requirement)
            do {
                ConsoleUtils.printHeader();
                displayMainMenu();

                if (!scanner.hasNextLine()) {
                    running = false;
                    break;
                }

                int choice = ConsoleUtils.readInt(scanner, "  Select an option (1-12): ", 1, 12);

                switch (choice) {
                    case 1:
                        handleViewAllVehicles();
                        break;
                    case 2:
                        handleViewAvailableVehicles();
                        break;
                    case 3:
                        handleSearchVehicles();
                        break;
                    case 4:
                        handleRegisterCustomer();
                        break;
                    case 5:
                        handleRentVehicle();
                        break;
                    case 6:
                        handleReturnVehicle();
                        break;
                    case 7:
                        handleCalculateRentalQuote();
                        break;
                    case 8:
                        handleViewCustomers();
                        break;
                    case 9:
                        handleViewRentalRecords();
                        break;
                    case 10:
                        handleViewAgencyStatistics();
                        break;
                    case 11:
                        handleDisplayVivaOOPGuide();
                        break;
                    case 12:
                        running = false;
                        printExitMessage();
                        break;
                    default:
                        System.out.println("  [!] Invalid choice. Please try again.");
                }
            } while (running);
        } catch (Exception e) {
            System.err.println("  [FATAL ERROR] An unexpected exception occurred: " + e.getMessage());
        } finally {
            // Demonstrates try-catch-finally resource cleanup (Unit II syllabus requirement)
            if (scanner != null) {
                scanner.close();
            }
        }
    }

    private static void displayMainMenu() {
        System.out.println("\n  MAIN NAVIGATION MENU:");
        System.out.println("  --------------------------------------------------");
        System.out.println("   1. View All Fleet Vehicles");
        System.out.println("   2. View Available Vehicles (Ready for Rent)");
        System.out.println("   3. Search Vehicles (by Keyword or Type & Budget)");
        System.out.println("   4. Register New Customer");
        System.out.println("   5. Rent a Vehicle (Create Agreement)");
        System.out.println("   6. Return a Vehicle (Finalize Settlement & Bill)");
        System.out.println("   7. Calculate Rental Quote & Discount Preview");
        System.out.println("   8. View Registered Customers");
        System.out.println("   9. View Rental History & Active Invoices");
        System.out.println("  10. View Fleet Analytics & Financial Summary");
        System.out.println("  11. Viva OOP Concepts Reference Guide");
        System.out.println("  12. Exit Application");
        System.out.println("  --------------------------------------------------");
    }

    // ==========================================
    // 1. VIEW ALL VEHICLES
    // ==========================================
    private static void handleViewAllVehicles() {
        agency.displayAllVehicles();
        ConsoleUtils.pressEnterToContinue(scanner);
    }

    // ==========================================
    // 2. VIEW AVAILABLE VEHICLES
    // ==========================================
    private static void handleViewAvailableVehicles() {
        agency.displayAvailableVehicles();
        ConsoleUtils.pressEnterToContinue(scanner);
    }

    // ==========================================
    // 3. SEARCH VEHICLES (Method Overloading)
    // ==========================================
    private static void handleSearchVehicles() {
        System.out.println("\n--- SEARCH VEHICLES (Demonstrates Method Overloading) ---");
        System.out.println("  1. Search by Keyword (ID, Make, or Model)");
        System.out.println("  2. Search by Vehicle Category & Daily Budget");
        
        int searchType = ConsoleUtils.readInt(scanner, "  Select search method (1-2): ", 1, 2);

        List<Vehicle> results;
        if (searchType == 1) {
            String keyword = ConsoleUtils.readNonEmptyString(scanner, "  Enter search keyword (e.g. Innova, Swift, Royal, TRK): ");
            // Invokes overloaded method: searchVehicles(String keyword)
            results = agency.searchVehicles(keyword);
        } else {
            System.out.println("  Category: 1. Car | 2. Motorcycle | 3. Truck | 4. Any");
            int catChoice = ConsoleUtils.readInt(scanner, "  Select category (1-4): ", 1, 4);
            String category = switch (catChoice) {
                case 1 -> "Car";
                case 2 -> "Motorcycle";
                case 3 -> "Truck";
                default -> "ALL";
            };
            double maxBudget = ConsoleUtils.readDouble(scanner, "  Enter maximum daily budget in Rs. (e.g. 2500): ", 100.0);
            // Invokes overloaded method: searchVehicles(String type, double maxDailyRate)
            results = agency.searchVehicles(category, maxBudget);
        }

        System.out.println("\n+-----------+-------------+--------------+----------------+------+-------------+-------------+");
        System.out.printf("| %-89s |%n", "SEARCH RESULTS (" + results.size() + " matches found)");
        System.out.println("+-----------+-------------+--------------+----------------+------+-------------+-------------+");
        System.out.println("| Veh. ID   | Type        | Make         | Model          | Year | Daily Rate  | Status      |");
        System.out.println("+-----------+-------------+--------------+----------------+------+-------------+-------------+");
        if (results.isEmpty()) {
            System.out.println("| No vehicles matched your search criteria.                                             |");
        } else {
            for (Vehicle v : results) {
                v.displayTableRow();
            }
        }
        System.out.println("+-----------+-------------+--------------+----------------+------+-------------+-------------+");
        ConsoleUtils.pressEnterToContinue(scanner);
    }

    // ==========================================
    // 4. REGISTER CUSTOMER
    // ==========================================
    private static void handleRegisterCustomer() {
        System.out.println("\n--- REGISTER NEW CUSTOMER ---");
        String name = ConsoleUtils.readNonEmptyString(scanner, "  Enter full name: ");
        String phone = ConsoleUtils.readNonEmptyString(scanner, "  Enter 10-digit mobile number: ");
        String license = ConsoleUtils.readNonEmptyString(scanner, "  Enter driving license number (e.g. KA04202300123): ");

        System.out.println("  Membership Tier: 1. REGULAR (Default) | 2. VIP (Loyalty) | 3. CORPORATE");
        int memChoice = ConsoleUtils.readInt(scanner, "  Select membership (1-3): ", 1, 3);
        String membership = switch (memChoice) {
            case 2 -> "VIP";
            case 3 -> "CORPORATE";
            default -> "REGULAR";
        };

        // Overloaded method call
        Customer customer = agency.registerCustomer(name, phone, license, membership);

        System.out.println("\n  [OK] Customer Registered Successfully!");
        System.out.println("  Assigned Customer ID : " + customer.getCustomerId());
        System.out.println("  Registered Name      : " + customer.getName());
        System.out.println("  Membership Tier      : " + customer.getMembershipType());

        ConsoleUtils.pressEnterToContinue(scanner);
    }

    // ==========================================
    // 5. RENT A VEHICLE
    // ==========================================
    private static void handleRentVehicle() {
        System.out.println("\n--- RENT A VEHICLE ---");
        agency.displayAvailableVehicles();

        String vehicleId = ConsoleUtils.readNonEmptyString(scanner, "\n  Enter Vehicle ID to rent (e.g. CAR-101): ");

        // Verify vehicle exists first
        try {
            Vehicle vehicle = agency.findVehicleById(vehicleId);
            if (!vehicle.isAvailable()) {
                System.out.println("  [!] Vehicle [" + vehicleId + "] is currently not available for rent.");
                ConsoleUtils.pressEnterToContinue(scanner);
                return;
            }

            System.out.println("\n  Vehicle Selected: " + vehicle);
            vehicle.displayDetails();

            System.out.println("\n  Available Customer IDs:");
            agency.displayAllCustomers();

            String customerId = ConsoleUtils.readNonEmptyString(scanner, "  Enter Customer ID (or enter 'NEW' to register): ");
            Customer customer;
            if ("NEW".equalsIgnoreCase(customerId)) {
                String name = ConsoleUtils.readNonEmptyString(scanner, "  Enter name: ");
                String phone = ConsoleUtils.readNonEmptyString(scanner, "  Enter phone: ");
                String license = ConsoleUtils.readNonEmptyString(scanner, "  Enter driving license: ");
                customer = agency.registerCustomer(name, phone, license);
                customerId = customer.getCustomerId();
                System.out.println("  [OK] Registered as: " + customerId);
            } else {
                customer = agency.findCustomerById(customerId);
                if (customer == null) {
                    System.out.println("  [!] Customer ID not found. Aborting rental.");
                    ConsoleUtils.pressEnterToContinue(scanner);
                    return;
                }
            }

            int days = ConsoleUtils.readInt(scanner, "  Enter rental duration in days (1-365): ", 1, 365);

            // Execute rental via Agency (demonstrates custom exception handling)
            RentalRecord record = agency.rentVehicle(vehicleId, customerId, days);

            System.out.println("\n  [OK] Rental Agreement Created Successfully!");
            // Interface Reference Polymorphism: variable explicitly declared using interface type (Requirement 4)
            Billable invoice = record;
            invoice.printInvoice();

        } catch (VehicleNotFoundException | VehicleNotAvailableException | InvalidRentalDurationException e) {
            System.out.println("\n  [ERROR] " + e.getMessage());
        } catch (Exception e) {
            System.out.println("\n  [ERROR] Unexpected error during rental: " + e.getMessage());
        }

        ConsoleUtils.pressEnterToContinue(scanner);
    }

    // ==========================================
    // 6. RETURN A VEHICLE
    // ==========================================
    private static void handleReturnVehicle() {
        System.out.println("\n--- RETURN A VEHICLE ---");
        agency.displayRentalRecords(true); // Show active rentals only

        String inputId = ConsoleUtils.readNonEmptyString(scanner, "\n  Enter Rental ID or Vehicle ID to return: ");
        int actualDays = ConsoleUtils.readInt(scanner, "  Enter actual total days the vehicle was kept: ", 1, 400);

        try {
            RentalRecord returnedRecord = agency.returnVehicle(inputId, actualDays);

            System.out.println("\n  [OK] Vehicle Successfully Returned & Inspected!");
            // Interface Reference Polymorphism: variable explicitly declared using interface type (Requirement 4)
            Billable finalInvoice = returnedRecord;
            finalInvoice.printInvoice();

        } catch (VehicleNotFoundException e) {
            System.out.println("\n  [ERROR] " + e.getMessage());
        }

        ConsoleUtils.pressEnterToContinue(scanner);
    }

    // ==========================================
    // 7. CALCULATE RENTAL CHARGES & DISCOUNT
    // ==========================================
    private static void handleCalculateRentalQuote() {
        System.out.println("\n--- CALCULATE RENTAL CHARGES & DISCOUNT QUOTE ---");
        agency.displayAllVehicles();

        String vehicleId = ConsoleUtils.readNonEmptyString(scanner, "\n  Enter Vehicle ID for quote: ");
        try {
            Vehicle vehicle = agency.findVehicleById(vehicleId);
            int days = ConsoleUtils.readInt(scanner, "  Enter prospective rental days: ", 1, 365);

            System.out.println("  Customer Category: 1. REGULAR | 2. VIP | 3. CORPORATE");
            int mem = ConsoleUtils.readInt(scanner, "  Select category (1-3): ", 1, 3);
            String membership = switch (mem) {
                case 2 -> "VIP";
                case 3 -> "CORPORATE";
                default -> "REGULAR";
            };

            Customer tempCustomer = new Customer("Quote Inquirer", "0000000000", "TEMP", membership);

            // Dynamic Dispatch (Polymorphic calculation)
            double grossCost = vehicle.calculateRentalCost(days);
            double discount = agency.calculateDiscount(grossCost, days, tempCustomer);
            double netBeforeTax = grossCost - discount;
            double gst = netBeforeTax * (RentalRecord.GST_PERCENTAGE / 100.0);
            double netTotal = netBeforeTax + gst;

            System.out.println("\n  =======================================================");
            System.out.println("               ESTIMATED RENTAL CHARGES QUOTE            ");
            System.out.println("  =======================================================");
            System.out.println("  Vehicle Selected    : " + vehicle.getBrand() + " " + vehicle.getModel() + " (" + vehicle.getVehicleType() + ")");
            System.out.printf("  Base Daily Rate     : Rs. %.2f / day%n", vehicle.getBaseDailyRate());
            System.out.printf("  Rental Duration     : %d Days%n", days);
            System.out.println("  -------------------------------------------------------");
            System.out.printf("  Gross Rental Cost   : Rs. %.2f%n", grossCost);
            System.out.printf("  Discount Calculated : -Rs. %.2f (Based on duration & %s tier)%n", discount, membership);
            System.out.printf("  GST (%.0f%%)            : +Rs. %.2f%n", RentalRecord.GST_PERCENTAGE, gst);
            System.out.println("  -------------------------------------------------------");
            System.out.printf("  Estimated Net Total : Rs. %.2f%n", netTotal);
            System.out.println("  =======================================================");

        } catch (VehicleNotFoundException e) {
            System.out.println("  [ERROR] " + e.getMessage());
        }

        ConsoleUtils.pressEnterToContinue(scanner);
    }

    // ==========================================
    // 8. VIEW CUSTOMERS
    // ==========================================
    private static void handleViewCustomers() {
        agency.displayAllCustomers();
        ConsoleUtils.pressEnterToContinue(scanner);
    }

    // ==========================================
    // 9. VIEW RENTAL RECORDS
    // ==========================================
    private static void handleViewRentalRecords() {
        System.out.println("\n  Rental Records Filter:");
        System.out.println("   1. View All Rental Records (History)");
        System.out.println("   2. View Only Active Rentals (Currently On Road)");
        int filter = ConsoleUtils.readInt(scanner, "  Select filter (1-2): ", 1, 2);

        agency.displayRentalRecords(filter == 2);
        ConsoleUtils.pressEnterToContinue(scanner);
    }

    // ==========================================
    // 10. FLEET ANALYTICS & STATS
    // ==========================================
    private static void handleViewAgencyStatistics() {
        agency.displaySystemStatistics();
        ConsoleUtils.pressEnterToContinue(scanner);
    }

    // ==========================================
    // 11. VIVA OOP CONCEPTS REFERENCE GUIDE
    // ==========================================
    private static void handleDisplayVivaOOPGuide() {
        System.out.println("\n==========================================================================================");
        System.out.println("             🎓 REVA UNIVERSITY - JAVA OOP VIVA EXAMINATION REFERENCE                     ");
        System.out.println("==========================================================================================");
        System.out.println("  Student Name : AVULU LOKESH             | SRN      : R24SA007                           ");
        System.out.println("  Program      : B.Sc. (BSTCs) - Sem V    | Subject  : Java Programming                   ");
        System.out.println("------------------------------------------------------------------------------------------");
        System.out.println("  1. ENCAPSULATION:");
        System.out.println("     - Every class (Vehicle, Car, Customer, RentalRecord) has private/protected fields.");
        System.out.println("     - State is accessible only through validated getters and setters.");
        System.out.println();
        System.out.println("  2. INHERITANCE:");
        System.out.println("     - Abstract base class 'Vehicle' is inherited by 'Car', 'Motorcycle', and 'Truck'.");
        System.out.println("     - Code reuse of common attributes (vehicleId, brand, model, baseDailyRate, etc.).");
        System.out.println();
        System.out.println("  3. POLYMORPHISM:");
        System.out.println("     - Compile-Time (Overloading): In RentalAgency with searchVehicles(...) and");
        System.out.println("       registerCustomer(...); also Constructor Overloading across all models.");
        System.out.println("     - Run-Time (Overriding / Dynamic Dispatch): Abstract method calculateRentalCost(int days)");
        System.out.println("       in Vehicle is overridden by Car (AC factor), Motorcycle (CC surcharge), and");
        System.out.println("       Truck (cargo tonnage factor). The JVM dynamically selects the correct method!");
        System.out.println();
        System.out.println("  4. ABSTRACTION & INTERFACES:");
        System.out.println("     - 'abstract class Vehicle' provides an incomplete template.");
        System.out.println("     - 'Rentable' interface specifies rental contracts (rent, return, availability).");
        System.out.println("     - 'Discountable' interface specifies dynamic discount calculation logic.");
        System.out.println("     - 'Billable' interface specifies invoice generation and total retrieval.");
        System.out.println();
        System.out.println("  5. 'this' and 'super' KEYWORDS:");
        System.out.println("     - 'this' used to avoid field shadowing and for constructor chaining: this(...).");
        System.out.println("     - 'super' used to invoke parent constructor: super(...) and parent methods: super.displayDetails().");
        System.out.println();
        System.out.println("  6. STATIC MEMBERS:");
        System.out.println("     - Static counters generate unique IDs: Customer.customerCounter, RentalRecord.rentalCounter.");
        System.out.println("     - Static fleet trackers: Vehicle.totalVehiclesInFleet, Vehicle.currentlyRentedCount.");
        System.out.println("     - Static utility methods: ConsoleUtils.formatCurrency(), ConsoleUtils.readInt().");
        System.out.println();
        System.out.println("  7. EXCEPTION HANDLING:");
        System.out.println("     - Custom Exceptions: VehicleNotFoundException, VehicleNotAvailableException,");
        System.out.println("       InvalidRentalDurationException.");
        System.out.println("     - Checked exceptions properly caught with descriptive feedback, preventing crashes.");
        System.out.println("==========================================================================================");
        ConsoleUtils.pressEnterToContinue(scanner);
    }

    private static void printExitMessage() {
        System.out.println("\n=======================================================================");
        System.out.println("  Thank you for using the CityDrive Vehicle Rental Management System! ");
        System.out.println("  Designed and implemented by AVULU LOKESH (SRN: R24SA007).           ");
        System.out.println("  REVA University - Department of Computer Science & Applications     ");
        System.out.println("=======================================================================\n");
    }
}
