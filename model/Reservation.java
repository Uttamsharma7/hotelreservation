package model;

import java.util.Date;

public class Reservation {

    private final Customer customer;
    private final IRoom room;
    private final Date checkInDate;
    private final Date checkOutDate;

    public Reservation(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        this.customer = customer;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }


    //Accessors



    public Customer getCustomer() {
        return customer;
    }



    public Date getCheckOutDate() {

        return checkOutDate;
    }

    public IRoom getRoom() {

        return room;
    }
    public Date getCheckInDate() {

        return checkInDate;
    }



    @Override
    public String toString() {
        return "reservation details -> \t" +
                "customer:- " + customer +
                "\tRoom:- " + room +
                "\tcheckInDate:- " +
                checkInDate +
                "\tcheckOutDate:- " +
                checkOutDate;
    }
}
