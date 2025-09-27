package model;

import java.util.Objects;

public class Room implements IRoom {

    private final String roomNumber;
    private final Double price;
    private final RoomType roomType;

    public Room(String roomNumber, Double price, RoomType roomType) {
        this.roomNumber = roomNumber;
        this.price = price;
        this.roomType = roomType;
    }

    @Override
    public RoomType getRoomType() {
        return this.roomType;
    }

    @Override
    public boolean isFree() {
        if (this.price != null && this.price == 0.0d) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String getRoomId() {
        return this.roomNumber;
    }

    @Override
    public String getRoomNumber() {
        return this.roomNumber;
    }

    @Override
    public Double getRoomPrice() {
        return this.price;
    }

    @Override
    public boolean equals(Object obz) {
        if (this == obz)
            return true;
        if (obz == null || getClass() != obz.getClass())
            return false;
        Room room = (Room) obz;
        return Objects.equals(roomNumber, room.roomNumber);
    }

    @Override
    public String toString() {
        final String rupeeDisplay = this.isFree() ? "Free" : "Rupee " + this.price;
        return "room number:- " +
                this.roomNumber +
                " price:- " +
                rupeeDisplay +
                " room type:- " +
                this.roomType;
    }
}