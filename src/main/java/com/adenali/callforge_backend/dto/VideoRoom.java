package com.adenali.callforge_backend.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class VideoRoom {
    @Id
    private String id; // We will generate a UUID here
    private String roomName;
    private String passcode;
    private boolean isSecure;
    private LocalDateTime startDate;
    private LocalDateTime expiryDate;
//    private String mediaNodeUrl = "http://localhost:3002"; // Default for now
    private String mediaNodeUrl = "https://dev.adenali.com"; // Default for now
}
