package commandlineui;

import api.AdminResource;
import model.Customer;
import model.IRoom;
import model.Room;
import model.RoomType;

import java.util.Collection;
import java.util.Collections;
import java.util.Scanner;

public class AdminMenu {
    private static final AdminResource adminResource = AdminResource.getInstance();

    public static void AdminDisplay(Scanner scanner) { // Accept scanner as a parameter
        int stop = 0;
        while (stop == 0) {
            try {
                System.out.println("------Admin Menu-----");
                System.out.println("1. see all customers");
                System.out.println("2. see all rooms");
                System.out.println("3. see all reservations");
                System.out.println("4. add a room");
                System.out.println("5. back to main menu");

                try {
                    int choice = Integer.parseInt(scanner.nextLine());
                    switch (choice) {
                        case 1:
                            Collection<Customer> allC = adminResource.getAllCustomers();
                            if (allC.isEmpty()) {
                                System.out.println("Sorry no customers found now");
                            } else {
                                allC.forEach(customer -> System.out.println(customer));
                            }
                            break;

                        case 2:
                            Collection<IRoom> allR = adminResource.getAllRooms();
                            if (allR.isEmpty()) {
                                System.out.println("Sorry no rooms found ");
                            } else {
                                allR.forEach(System.out::println);
                            }
                            break;
                        case 3:
                            adminResource.displayAllReservations();
                            System.out.println("This is all reservations");
                            break;
                        case 4:
                            addARoom(scanner);
                            System.out.println("Showing all rooms");
                            break;
                        case 5:
                            System.out.println("Back to MAIN_MENU");
                            stop = 1;
                            break;
                        default:
                            System.out.println("Please enter a number between 1 - 5 Thank you!");
                            break;
                    }
                } catch (NumberFormatException ex) {
                    System.out.println("Error:- Please enter a number between 1 - 5.");
                }
            }
        catch (Exception e){}
        }
    }

    private static void addARoom(Scanner scanner) {
        int keepAdding = 0;
        while (keepAdding == 0) {
            System.out.println("enter the room number:- ");
            String roomNumber = scanner.nextLine();

            double roomPrice = getValidPriceInput(scanner);

            System.out.println("enter room type for single press 1 and double press 2");
            int roomType = Integer.parseInt(scanner.nextLine());

            RoomType type;
            if (roomType == 1) {
                type = RoomType.SINGLE;
            } else if (roomType == 2) {
                type = RoomType.DOUBLE;
            } else {
                System.out.println("Error:- please give valid input");
                continue;
            }
            IRoom newIR;
            newIR = new Room(roomNumber, roomPrice, type);
            adminResource.addARoom(Collections.singletonList(newIR));
            System.out.println("room added successfully!!!");

            System.out.println("do you want to add another room? (y/n) ");
            String input = scanner.nextLine();
            if (!"y".equalsIgnoreCase(input)) {
                keepAdding = 1;
            }
        }
    }

    private static double getValidPriceInput(Scanner scanner) {
        while (true) {
            try {
                System.out.println("enter room price: ");
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException numberFE) {
                System.out.println("invalid input, kindly enter a valid number for the price.");
            }
        }
    }
}