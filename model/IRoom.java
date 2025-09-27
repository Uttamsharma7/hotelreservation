package model;

public interface IRoom {
    String getRoomId();
    public String getRoomNumber();
    public Double getRoomPrice();
    public RoomType getRoomType();
    public boolean isFree();

}
