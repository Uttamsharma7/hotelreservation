package commandlineui;

import api.HotelResource;
import model.IRoom;
import model.Reservation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class MainMenu {
    private static final HotelResource hotelResource = HotelResource.getInstance();
    private static final Scanner sc = new Scanner(System.in);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

    public static void MenuDisplay() {
        int stop = 0;
        while (stop == 0) {
            try {
                System.out.println("------MAIN_MENU------");
                System.out.println("1. Find and reserve a room");
                System.out.println("2. See my reservations");
                System.out.println("3. Create an account");
                System.out.println("4. Admin");
                System.out.println("5. Exit");
                System.out.println("please select a number between 1 to 5 ");

                int choice2 = Integer.parseInt(sc.nextLine());

                switch (choice2) {
                    case 1:
                        findAndReserveRoom();
                        System.out.println("Please find and reserve your room here ");
                        break;
                    case 2:
                        seeMyReservations();
                        System.out.println("Please find your reservation here");
                        break;
                    case 3:
                        createAnAccount();
                        System.out.println("please create your account here");
                        break;
                    case 4:
                        AdminMenu.AdminDisplay(sc); // <-- TO THIS
                        System.out.println("Displaying Admin Menu");
                        break;
                    case 5:
                        System.out.println("exiting from application:- Good Bye ");
                        stop = 1;
                        break;
                    default:
                        System.out.println("Error :- Please do enter valid numbers");
                        break;
                }
            } catch (NumberFormatException ex) {
                System.out.println("The provided input is invalid, kindly enter a number");
            }
        }
    }

    private static Date addWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, 7);
        return calendar.getTime();
    }

    private static void findAndReserveRoom() {
        try {
            System.out.println("enter check in date in mm/dd/yyyy format: ");
            Date checkInDate = dateFormat.parse(sc.nextLine());

            System.out.println("enter check out date in mm/dd/yyyy format: ");
            Date checkOutDate = dateFormat.parse(sc.nextLine());

            Collection<IRoom> roomsAccess = hotelResource.findARoom(checkInDate, checkOutDate);

            if (roomsAccess.isEmpty()) {
                System.out.println("no rooms are available for the selected dates :(");

                Date newCheckInDate = addWeek(checkInDate);
                Date newCheckOutDate = addWeek(checkOutDate);
                Collection<IRoom> alternateRoom = hotelResource.findARoom(newCheckInDate, newCheckOutDate);

                if (alternateRoom.isEmpty()) {
                    System.out.println("no alternative rooms founds");
                } else {
                    System.out.println("new check In Date:- " + dateFormat.format(newCheckInDate));
                    System.out.println("new check Out Date:- " + dateFormat.format(newCheckOutDate));

                    bookRoom(alternateRoom, newCheckInDate, newCheckOutDate);
                }
            } else {
                System.out.println("Rooms are available in yours selected dates:-");
                bookRoom(roomsAccess, checkInDate, checkOutDate);
            }
        } catch (ParseException ex) {
            System.out.println("Invalid date format :- try MM/dd/yyyy");
        }
    }

    private static void createAnAccount() {
        try {
            System.out.println("Enter your email in name@domain.com format:- ");
            String email = sc.nextLine();

            System.out.println("Enter first name:- ");
            String firstName = sc.nextLine();

            System.out.println("Enter last name:- ");
            String lastName = sc.nextLine();

            hotelResource.createCustomer(email, firstName, lastName);
            System.out.println("Account Created Successfully");
        } catch (IllegalArgumentException ex) {
            System.out.println("Error creating an account: " + ex.getMessage());
        }
    }

    private static void seeMyReservations() {
        System.out.println("Enter your email:- ");
        String customerEmail = sc.nextLine();
        Collection<Reservation> customerReservations = hotelResource.getReservations(customerEmail);

        System.out.println(customerReservations == null || customerReservations.isEmpty() ?
                "Reservation are not found for that email!" : "Your reservations are here :- ");

        if (customerReservations != null && !customerReservations.isEmpty()) {
            customerReservations.forEach(System.out::println);
        }
    }

    private static void bookRoom(Collection<IRoom> roomsAccess, Date checkIn, Date checkOut) {
        if (roomsAccess.isEmpty()) {
            System.out.println("no available rooms to book:-");
            return;
        }

        System.out.println("Here are some available rooms for you:-");
        roomsAccess.forEach(System.out::println);

        boolean bookingInProgress = true;
        while (bookingInProgress) {
            System.out.println("Do you want to book a room? (y/n):-");
            String bookingChoice = sc.nextLine();

            if ("y".equalsIgnoreCase(bookingChoice)) {
                System.out.println("Do you have an account? (y/n):- ");
                String accountChoice = sc.nextLine();

                if ("y".equalsIgnoreCase(accountChoice)) {
                    System.out.println("Enter your email here (e.g., name@domain.com):");
                    String customerEmail = sc.nextLine();

                    if (hotelResource.getCustomer(customerEmail) == null) {
                        System.out.println("Your Account not found, Please create it first:-");
                        return;
                    }

                    System.out.println("Please enter room number to reserve:");
                    String roomNumber = sc.nextLine();
                    IRoom roomToBook = hotelResource.getRoom(roomNumber);

                    if (roomsAccess.contains(roomToBook)) {
                        hotelResource.bookRoom(customerEmail, roomToBook, checkIn, checkOut);
                        System.out.println("reservation successfully done! ");
                    } else {
                        System.out.println("error: CAN NOT FOUND ROOMS FOR THESE DAYS ");
                    }
                } else if ("n".equalsIgnoreCase(accountChoice)) {
                    createAnAccount();
                } else {
                    System.out.println("invalid input:- please enter y or n!");
                }
                bookingInProgress = false;
            } else if ("n".equalsIgnoreCase(bookingChoice)) {
                bookingInProgress = false; // Exit the loop
            } else {
                System.out.println("invalid input:- please enter y or n!");
            }
        }
    }
}