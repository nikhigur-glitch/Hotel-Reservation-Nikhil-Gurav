package com.unstop.hotel;

import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class HotelService {
    private final Map<Integer, List<Room>> floors = new HashMap<>();
    private List<Integer> lastBooked = new ArrayList<>();

    public HotelService() { reset(); }

    public synchronized void reset() {
        floors.clear();
        for (int f = 1; f <= 10; f++) {
            int count = (f == 10) ? 7 : 10;
            List<Room> list = new ArrayList<>();
            for (int p = 1; p <= count; p++) list.add(new Room(f, p));
            floors.put(f, list);
        }
        lastBooked = new ArrayList<>();
    }

    public synchronized HotelStateDTO getState() {
        HotelStateDTO dto = new HotelStateDTO();
        for (int f = 1; f <= 10; f++) {
            for (Room r : floors.get(f)) {
                dto.rooms.add(new HotelStateDTO.RoomDTO(r.getFloor(), r.getPosition(), r.getNumber(), r.isBooked()));
            }
        }
        dto.justBooked = new ArrayList<>(lastBooked);
        return dto;
    }

    public synchronized void randomize() {
        Random rnd = new Random();
        for (int f = 1; f <= 10; f++) {
            for (Room r : floors.get(f)) {
                r.setBooked(rnd.nextDouble() < 0.5);
            }
        }
        lastBooked = new ArrayList<>();
    }

    private List<Room> availableRoomsOnFloor(int floor) {
        return floors.get(floor).stream().filter(r -> !r.isBooked()).sorted(Comparator.comparingInt(Room::getPosition)).collect(Collectors.toList());
    }

    private int spanCost(List<Room> set) {
        if (set.isEmpty() || set.size() == 1) return 0;
        int minFloor = set.stream().mapToInt(Room::getFloor).min().getAsInt();
        int maxFloor = set.stream().mapToInt(Room::getFloor).max().getAsInt();
        if (minFloor == maxFloor) {
            int minPos = set.stream().mapToInt(Room::getPosition).min().getAsInt();
            int maxPos = set.stream().mapToInt(Room::getPosition).max().getAsInt();
            return maxPos - minPos;
        }
        int vertical = 2 * (maxFloor - minFloor);
        return vertical;
    }

    public synchronized HotelStateDTO book(int k) {
        List<Room> chosen = null;
        for (int f = 1; f <= 10; f++) {
            List<Room> avail = availableRoomsOnFloor(f);
            if (avail.size() >= k) {
                chosen = avail.subList(0, k);
                break;
            }
        }
        if (chosen == null) return null;
        for (Room r : chosen) r.setBooked(true);
        lastBooked = chosen.stream().map(Room::getNumber).collect(Collectors.toList());
        return getState();
    }
}
