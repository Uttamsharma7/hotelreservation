package Service;

import model.Customer;
import model.IRoom;
import model.Reservation;

import java.util.*;

public class ReservationService {

    private static ReservationService inst;

    private final Map<String, IRoom> irooms;
    private final List<Reservation> reserv;

    private ReservationService() {
        this.irooms = new HashMap<>();
        this.reserv = new ArrayList<>();
    }

    public static ReservationService getInstance() {
        if (inst == null) {
            inst = new ReservationService();
        }
        return inst;
    }

    public void addARoom(IRoom room) {
        if (irooms.containsKey(room.getRoomId())) {
            System.out.println("Sorry this room is already available");
            return;
        }
        irooms.put(room.getRoomId(), room);
    }

    public IRoom getARoom(String roomId) {
        return irooms.get(roomId);
    }

    //method for reserving a room
    public Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        Reservation newR = new Reservation(customer, room, checkInDate, checkOutDate);
        reserv.add(newR);
        return newR;
    }

    //method for finding a room
    public Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate) {
        Collection<IRoom> roomsAccess = new ArrayList<>(irooms.values());

        List<IRoom> roomsToRemove = new ArrayList<>();
        for (Reservation reservation1 : reserv) {
            // Updated logic to check for date overlap, considering same day check-in/out
            if (reservation1.getCheckInDate().before(checkOutDate) && checkInDate.before(reservation1.getCheckOutDate())) {
                roomsToRemove.add(reservation1.getRoom());
            }
        }
        roomsAccess.removeAll(roomsToRemove);

        return roomsAccess;
    }

    public Collection<Reservation> getCustomersReservation(Customer customer) {
        if (reserv == null) {
            return Collections.emptyList();
        }

        Collection<Reservation> customerReserv = new ArrayList<>();
        for (Reservation reservation : reserv) {
            if (reservation.getCustomer().equals(customer)) {
                customerReserv.add(reservation);
            }
        }
        return customerReserv;
    }

    public void printAllReservation() {
        for (Reservation reservation : reserv) {
            System.out.println(reservation + "\t");
        }
        if (reserv.isEmpty()) {
            System.out.println("Sorry no reservation is currently showing:-");
        }
    }

    public Collection<IRoom> getAllRooms() {
        return irooms.values();
    }
}