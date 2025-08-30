package com.unstop.hotel;

public class Room {
    public final int floor;
    public final int position;
    public final int number;
    private boolean booked;

    public Room(int floor, int position) {
        this.floor = floor;
        this.position = position;
        if (floor == 10) {
            this.number = 1000 + position;
        } else {
            this.number = floor * 100 + position;
        }
        this.booked = false;
    }

    public int getFloor() { return floor; }
    public int getPosition() { return position; }
    public int getNumber() { return number; }
    public boolean isBooked() { return booked; }
    public void setBooked(boolean booked) { this.booked = booked; }

    public int toStairsCost() { return position - 1; }
}
