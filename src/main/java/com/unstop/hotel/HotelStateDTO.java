package com.unstop.hotel;

import java.util.*;

public class HotelStateDTO {
    public List<RoomDTO> rooms = new ArrayList<>();
    public List<Integer> justBooked = new ArrayList<>();

    public static class RoomDTO {
        public int floor;
        public int position;
        public int number;
        public boolean booked;

        public RoomDTO(int floor, int position, int number, boolean booked) {
            this.floor = floor;
            this.position = position;
            this.number = number;
            this.booked = booked;
        }
    }
}
