package com.reva.rental.util;

import java.util.Scanner;

/**
 * Utility Class: ConsoleUtils
 * 
 * Demonstrates 'final' class keyword (Requirement 6):
 * Utility classes containing only static methods are declared final with a
 * private constructor to prevent inheritance and direct instantiation.
 * 
 * Demonstrates robust input handling with EOF safety (Requirement 8).
 * 
 * Student: AVULU LOKESH (SRN: R24SA007)
 * REVA University - B.Sc. (BSTCs) Semester V
 */
public final class ConsoleUtils {

    // Private constructor prevents instantiation
    private ConsoleUtils() {}

    /**
     * Formats an amount into standard Indian currency representation (Rs. X,XXX.XX).
     */
    public static String formatCurrency(double amount) {
        return String.format("Rs. %,.2f", amount);
    }

    /**
     * Prints the primary application header banner with student identification.
     */
    public static void printHeader() {
        System.out.println("==================================================================================");
        System.out.println("            CITYDRIVE VEHICLE RENTAL MANAGEMENT SYSTEM - REVA UNIVERSITY          ");
        System.out.println("----------------------------------------------------------------------------------");
        System.out.println("  Student Name : AVULU LOKESH        | SRN     : R24SA007                         ");
        System.out.println("  Degree       : B.Sc. (BSTCs) - V   | Subject : Java Programming (Practical)    ");
        System.out.println("  Institution  : REVA University     | Project : Vehicle Rental OOP Mini-Project  ");
        System.out.println("==================================================================================");
    }

    /**
     * Safely reads an integer from the console within an inclusive range [min, max].
     * Includes EOF safety check against NoSuchElementException.
     */
    public static int readInt(Scanner scanner, String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            if (!scanner.hasNextLine()) {
                return (max == 12) ? max : min; // Safe fallback on stream exhaustion / EOF (Requirement 8)
            }
            String input = scanner.nextLine().trim();
            try {
                int value = Integer.parseInt(input);
                if (value >= min && value <= max) {
                    return value;
                }
                System.out.printf("  [!] Please enter a number between %d and %d.%n", min, max);
            } catch (NumberFormatException e) {
                System.out.println("  [!] Invalid input! Please enter a valid whole number.");
            }
        }
    }

    /**
     * Safely reads a positive double value.
     * Includes EOF safety check against NoSuchElementException.
     */
    public static double readDouble(Scanner scanner, String prompt, double min) {
        while (true) {
            System.out.print(prompt);
            if (!scanner.hasNextLine()) {
                return min; // Safe fallback on stream exhaustion / EOF (Requirement 8)
            }
            String input = scanner.nextLine().trim();
            try {
                double value = Double.parseDouble(input);
                if (value >= min) {
                    return value;
                }
                System.out.printf("  [!] Value must be at least %.2f.%n", min);
            } catch (NumberFormatException e) {
                System.out.println("  [!] Invalid decimal number! Please try again.");
            }
        }
    }

    /**
     * Reads non-empty string input.
     * Includes EOF safety check against NoSuchElementException.
     */
    public static String readNonEmptyString(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            if (!scanner.hasNextLine()) {
                return "N/A"; // Safe fallback on stream exhaustion / EOF (Requirement 8)
            }
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("  [!] Field cannot be empty. Please enter a value.");
        }
    }

    /**
     * Prompts user to press Enter to continue.
     */
    public static void pressEnterToContinue(Scanner scanner) {
        System.out.print("\nPress [Enter] to return to the main menu...");
        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }
    }
}
