# REVA UNIVERSITY — DEPARTMENT OF COMPUTER SCIENCE & APPLICATIONS
## B.Sc. (BSTCs) — Semester V | Java Programming Practical Mini-Project

---

### STUDENT IDENTIFICATION
* **Student Name:** AVULU LOKESH
* **SRN:** R24SA007
* **Program:** Bachelor of Science — BSTCs (Biotech, Stats, Comp Sci)
* **Semester:** V
* **Subject:** Java Programming
* **Project Assigned:** Vehicle Rental Management System
* **Academic Year:** 2026

---

## 1. PROJECT OVERVIEW

The **Vehicle Rental Management System (CityDrive Mobility)** is an end-to-end, console-based Java application simulating a real-world vehicle leasing enterprise. The application manages a diversified automotive fleet consisting of:
1. **Passenger Cars** (Compact hatchbacks, SUVs, and luxury 7-seaters with AC surcharge factors)
2. **Motorcycles** (Daily commuter two-wheelers, sports bikes, and high-displacement cruisers)
3. **Commercial Cargo Trucks** (Light goods carriers and heavy multi-axle freight trucks)

The system manages customer registrations, fleet availability tracking, real-time multi-attribute search queries, dynamic pricing with tiered discounts and GST calculation, rental agreements, return inspections, and penalty settlements.

---

## 2. PROJECT DIRECTORY STRUCTURE

```text
c:\Vehicle_Rental_Management_System\
│
├── compile.bat                  # One-click Windows compilation script
├── run.bat                      # One-click Windows execution launcher
├── README.md                    # Comprehensive documentation and viva guide
│
├── src\
│   └── com\reva\rental\
│       ├── Main.java            # Console UI, interactive menu loop, and viva guide
│       │
│       ├── model\
│       │   ├── VehicleType.java # Enum representing supported vehicle categories
│       │   ├── Vehicle.java     # Abstract superclass implementing Rentable
│       │   ├── Car.java         # Subclass inheriting Vehicle (AC & seating logic)
│       │   ├── Motorcycle.java  # Subclass inheriting Vehicle (CC & gear logic)
│       │   ├── Truck.java       # Subclass inheriting Vehicle (Tonnage & permit logic)
│       │   ├── Customer.java    # Encapsulated client entity with loyalty tiers
│       │   └── RentalRecord.java# Financial agreement model implementing Billable
│       │
│       ├── interfaces\
│       │   ├── Rentable.java    # Contract for vehicle rental lifecycle
│       │   ├── Discountable.java# Contract for duration & loyalty discount calculation
│       │   └── Billable.java    # Contract for invoice printing and payment retrieval
│       │
│       ├── exceptions\
│       │   ├── VehicleNotFoundException.java       # Custom exception for invalid vehicle ID
│       │   ├── VehicleNotAvailableException.java   # Custom exception when vehicle is rented
│       │   └── InvalidRentalDurationException.java # Custom exception for invalid rental days
│       │
│       ├── service\
│       │   └── RentalAgency.java# Business logic controller & polymorphic collection hub
│       │
│       └── util\
│           └── ConsoleUtils.java# Static input validation and UI formatting utilities
│
└── bin\                         # Compiled Java bytecode (.class files)
```

---

## 3. UNIT I & UNIT II OBJECT-ORIENTED CONCEPTS MAPPING

Every programming requirement from Unit I and Unit II is integrated into the core architecture:

| Syllabus OOP Concept | Implementation in this Project | Code Location |
| :--- | :--- | :--- |
| **Classes & Objects** | Real-world entities modeled as blueprints (`Vehicle`, `Customer`, `RentalRecord`, `RentalAgency`). Real runtime objects created via `new`. | All files in `model/` and `service/` |
| **Encapsulation** | All state fields are declared `private` or `protected`. Public getters and setters enforce validation rules (e.g., non-negative daily rates, 10-digit mobile numbers). | `Customer.java`, `Vehicle.java`, `RentalRecord.java` |
| **Inheritance** | Single inheritance hierarchy: `Car extends Vehicle`, `Motorcycle extends Vehicle`, `Truck extends Vehicle`. Subclasses inherit all common vehicle state. | `Car.java`, `Motorcycle.java`, `Truck.java` |
| **Compile-Time Polymorphism (Method Overloading)** | Multiple methods with the same name but different parameter lists: `searchVehicles(String keyword)`, `searchVehicles(String type, double maxBudget)`, `registerCustomer(...)`, and overloaded constructors. | `RentalAgency.java`, `Customer.java`, `Vehicle.java` |
| **Run-Time Polymorphism (Method Overriding / Dynamic Method Dispatch)** | `calculateRentalCost(int days)` is declared `abstract` in `Vehicle` and overridden specifically in `Car`, `Motorcycle`, and `Truck`. The JVM dynamically resolves the appropriate subclass method at runtime. | `Car.java`, `Motorcycle.java`, `Truck.java` |
| **Abstraction & Interfaces** | `abstract class Vehicle` defines the shared base contract. `Rentable`, `Discountable`, and `Billable` interfaces enforce decoupled behaviors across models and services. Interface reference polymorphism: `Billable invoice = record;` and `Rentable rentableVehicle = vehicle;`. | `interfaces/Rentable.java`, `Discountable.java`, `Billable.java`, `Main.java`, `RentalAgency.java` |
| **Enumerations (Enum)** | `VehicleType` enum (`CAR`, `MOTORCYCLE`, `TRUCK`) integrated directly into the `Vehicle` model hierarchy for type-safe categorization. | `model/VehicleType.java`, `Vehicle.java` |
| **`this` Keyword** | Used to resolve variable shadowing (`this.brand = brand;`) and for constructor chaining (`this(id, brand, model, 2023, rate, type);`). | `Customer.java`, `Vehicle.java` |
| **`super` Keyword** | Used in subclasses to invoke parent constructors (`super(vehicleId, brand, ...);`) and reuse parent methods (`super.displayDetails();`). | `Car.java`, `Motorcycle.java`, `Truck.java` |
| **`final` Keyword (Class, Method, Constant)** | `public final class ConsoleUtils` utility class; `final` method `getVehicleId()` in `Vehicle`; domain constant `public static final double GST_PERCENTAGE = 5.0` in `RentalRecord`. | `ConsoleUtils.java`, `Vehicle.java`, `RentalRecord.java` |
| **Static Members** | `static` variables for auto-incrementing IDs (`customerCounter`, `rentalCounter`), fleet tracking (`totalVehiclesInFleet`, `currentlyRentedCount`), and `static` utility functions (`ConsoleUtils.formatCurrency`). | `Customer.java`, `RentalRecord.java`, `ConsoleUtils.java` |
| **Type Casting** | Explicit narrowing primitive cast `(int) finalSettledAmount` and reference downcasting `(Car) vehicle`, `(Vehicle) obj` in `equals()`. | `RentalRecord.java`, `Vehicle.java`, `Customer.java` |
| **Object Equality (`equals` & `hashCode`)** | Overridden in `Vehicle` (identity field `vehicleId`) and `Customer` (identity field `customerId`) using `@Override`. | `Vehicle.java`, `Customer.java` |
| **Exception Handling** | Custom checked exceptions (`VehicleNotFoundException`, `VehicleNotAvailableException`, `InvalidRentalDurationException`) handled with `try-catch-finally` to prevent crashes. | `exceptions/`, `Main.java`, `RentalAgency.java` |
| **Control Flow & Console I/O (Unit I)** | `switch-case`, `while`, `do-while`, `for-each` loops, `continue;` statement to skip rented vehicles in availability listing, `Scanner` input handling with EOF safety, `System.out.printf` tabular formatting. | `Main.java`, `RentalAgency.java`, `ConsoleUtils.java` |

---

## 4. APPLICATION FEATURES

1. **View All Vehicles:** Displays tabular fleet data including Vehicle ID, Vehicle Category, Brand, Model, Manufacturing Year, Base Daily Rate, and Current Availability Status.
2. **View Available Vehicles:** Filters only vehicles currently ready for immediate rental dispatch.
3. **Multi-Attribute Vehicle Search (Method Overloading):**
   * Search by text query (matches ID, Make, or Model).
   * Search by Vehicle Category (Car, Motorcycle, Truck) and maximum daily budget in Rs.
4. **Customer Registration:** Registers clients with full name, contact number, driving license, and membership tier (Regular, VIP, Corporate).
5. **Rental Agreement Creation:**
   * Selects vehicle and verifies availability.
   * Links registered customer.
   * Takes rental duration in days (1 to 365).
   * Applies duration discounts and loyalty tier discounts.
   * Computes 5% GST and security deposits.
   * Generates an itemized ASCII rental invoice.
6. **Vehicle Return & Settlement:**
   * Locates active rental agreement.
   * Inspects returned vehicle.
   * Calculates late days and daily late penalties if kept past expected date.
   * Finalizes total settlement and marks vehicle available again.
7. **Calculate Rental Quote & Discount Preview:** Allows customers to preview estimated charges and eligible discounts before committing to a rental.
8. **View Registered Customers:** Displays all customer profiles with loyalty status and completed rental counts.
9. **Rental Records & History:** View active road contracts or full historical transaction logs.
10. **Agency Fleet Analytics:** Real-time summary of fleet capacity, rented vehicles, active agreements, completed agreements, and gross revenue generated.
11. **Interactive Viva OOP Reference Guide:** Built-in menu option explaining how each OOP requirement is satisfied.

---

## 5. HOW TO COMPILE AND RUN

### Option A: Using Windows Batch Scripts (Recommended)
1. Double-click `compile.bat` to compile all source files into the `bin/` directory.
2. Double-click `run.bat` to start the application.

### Option B: Using Command Prompt / PowerShell
Open your terminal inside `c:\Vehicle_Rental_Management_System` and execute:

```bash
# 1. Compile all Java source files:
javac -d bin -sourcepath src src/com/reva/rental/Main.java

# 2. Run the application:
java -cp bin com.reva.rental.Main
```

---

## 6. VIVA VOCE QUESTIONS & ANSWERS (EXAMINER PREPARATION)

### Q1: What is the difference between Abstraction and Encapsulation? How did you implement both?
* **Answer:**
  * **Encapsulation** is the mechanism of binding code and data together while hiding internal object state. In our project, all variables (`baseDailyRate`, `phoneNumber`, `licenseNumber`, etc.) are declared `private` or `protected` and accessed strictly through validated public getter and setter methods.
  * **Abstraction** is the concept of exposing essential features while hiding the underlying implementation details. We implemented abstraction using:
    1. The `abstract class Vehicle`, which defines the template method `calculateRentalCost(int days)`.
    2. Interfaces such as `Rentable`, `Discountable`, and `Billable`, which define contracts without specifying how classes implement them.

### Q2: How does Dynamic Method Dispatch (Runtime Polymorphism) work in your project?
* **Answer:**
  In `RentalRecord.java`, we call:
  ```java
  this.grossRentalAmount = vehicle.calculateRentalCost(this.plannedDays);
  ```
  Here, `vehicle` is a reference variable of the abstract superclass `Vehicle`. At compile time, the compiler only knows that `Vehicle` has an abstract method called `calculateRentalCost`. At runtime, the Java Virtual Machine (JVM) checks the actual object type assigned to `vehicle` (e.g., `Car`, `Motorcycle`, or `Truck`) and invokes the overridden method in that specific subclass.

### Q3: What is the difference between Method Overloading and Method Overriding?
* **Answer:**
  * **Method Overloading (Compile-Time Polymorphism):** Methods in the same class share the same name but have different parameter lists (different number, type, or order of parameters). Example in `RentalAgency.java`:
    * `searchVehicles(String keyword)`
    * `searchVehicles(String type, double maxDailyRate)`
  * **Method Overriding (Run-Time Polymorphism):** A subclass provides a specific implementation for a method already declared in its superclass, keeping the same method signature and return type. Example: `Car`, `Motorcycle`, and `Truck` overriding `calculateRentalCost(int days)` and `displayDetails()`.

### Q4: Why did you use `this` and `super` keywords?
* **Answer:**
  * `this` refers to the current invoking object. We use it to:
    1. Disambiguate instance variables from constructor parameters: `this.brand = brand;`
    2. Perform constructor chaining within the same class: `this(vehicleId, brand, model, 2023, baseDailyRate, vehicleType);`
  * `super` refers to the immediate parent class. We use it to:
    1. Invoke the superclass constructor from derived class constructors: `super(vehicleId, brand, model, year, rate, "Car");`
    2. Call the parent class's method to avoid duplicate code: `super.displayDetails();`

### Q5: What is the purpose of the `static` keyword in your system?
* **Answer:**
  `static` members belong to the class itself rather than any individual object instance:
  1. `Customer.customerCounter` and `RentalRecord.rentalCounter`: Generate continuous, unique, auto-incrementing serial IDs (`CUST-1004`, `RENT-2002`).
  2. `Vehicle.totalVehiclesInFleet` and `Vehicle.currentlyRentedCount`: Maintain aggregate inventory status shared across all vehicle instances.
  3. `ConsoleUtils.formatCurrency(double amount)`: Static utility methods that can be executed directly without instantiating `ConsoleUtils`.

### Q6: Why did you create custom exceptions instead of using generic `Exception`?
* **Answer:**
  Custom exceptions (`VehicleNotFoundException`, `VehicleNotAvailableException`, `InvalidRentalDurationException`) provide meaningful domain-specific error handling. They allow the controller layer in `Main.java` to catch specific error conditions individually and present clear, informative recovery messages to the user without terminating the console program.

---

### ACKNOWLEDGEMENTS & DECLARATION
This project is developed by **AVULU LOKESH (SRN: R24SA007)** as part of the fifth-semester practical examination in **Java Programming** at **REVA University**. All Object-Oriented principles, business logic, and console interfaces have been written and tested in accordance with university academic standards.
