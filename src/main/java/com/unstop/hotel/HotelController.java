package com.unstop.hotel;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class HotelController {
    private final HotelService service;

    public HotelController(HotelService service) { this.service = service; }

    @GetMapping("/rooms") public HotelStateDTO rooms() { return service.getState(); }

    @PostMapping("/reset") public HotelStateDTO reset() { service.reset(); return service.getState(); }

    @PostMapping("/randomize") public HotelStateDTO randomize() { service.randomize(); return service.getState(); }

    @PostMapping("/book") public ResponseEntity<?> book(@RequestParam("count") int count) {
        if (count < 1 || count > 5) return ResponseEntity.badRequest().body("Book between 1 and 5 rooms");
        HotelStateDTO res = service.book(count);
        if (res == null) return ResponseEntity.badRequest().body("Not enough rooms available");
        return ResponseEntity.ok(res);
    }
}
