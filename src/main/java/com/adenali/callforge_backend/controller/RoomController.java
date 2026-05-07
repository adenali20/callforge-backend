package com.adenali.callforge_backend.controller;

import com.adenali.callforge_backend.dto.RoomRequest;
import com.adenali.callforge_backend.dto.VideoRoom;
import com.adenali.callforge_backend.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/rooms")
@CrossOrigin(origins = "http://localhost:3000") // Allow your React app
public class RoomController {

    @Autowired
    private RoomRepository roomRepository;

    @PostMapping
    public ResponseEntity<Map<String, String>> createRoom(@RequestBody RoomRequest request) {
        VideoRoom room = new VideoRoom();
        room.setId(UUID.randomUUID().toString()); // The magic UUID for the link
        room.setRoomName(request.getRoomName());
        room.setSecure(request.isSecure());
        room.setPasscode(request.getPasscode());
        room.setStartDate(request.getStartDate());
        room.setExpiryDate(request.getStartDate().plusHours(request.getDurationHours()));

        roomRepository.save(room);

        return ResponseEntity.ok(Map.of("roomId", room.getId()));
    }

    @GetMapping("/{roomId}")
    public ResponseEntity<?> getRoomStatus(@PathVariable String roomId) {
        return roomRepository.findById(roomId).map(room -> {
            LocalDateTime now = LocalDateTime.now();
            String status;

            if (now.isBefore(room.getStartDate())) {
                status = "EARLY";
            } else if (now.isAfter(room.getExpiryDate())) {
                status = "EXPIRED";
            } else {
                status = "STARTED";
            }

            // Return a DTO so we don't leak the passcode
            return ResponseEntity.ok(Map.of(
                    "roomName", room.getRoomName(),
                    "status", status,
                    "isSecure", room.isSecure(),
                    "startDate", room.getStartDate(),
                    "mediaNodeUrl", room.getMediaNodeUrl()
            ));
        }).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{roomId}/verify")
    public ResponseEntity<?> verifyPasscode(@PathVariable String roomId, @RequestBody Map<String, String> body) {
        String inputPasscode = body.get("passcode");

        return roomRepository.findById(roomId).map(room -> {
            // Compare the provided passcode with the one in the database
            if (room.getPasscode() != null && room.getPasscode().equals(inputPasscode)) {
                return ResponseEntity.ok().build(); // 200 OK
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("message", "Incorrect passcode")); // 401
            }
        }).orElse(ResponseEntity.notFound().build()); // 404 if room doesn't exist
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello from backend 🚀";
    }
}
