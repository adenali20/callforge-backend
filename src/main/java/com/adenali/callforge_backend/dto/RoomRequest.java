package com.adenali.callforge_backend.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class RoomRequest {
    private String roomName;
    private boolean isSecure;
    private String passcode;
    private LocalDateTime startDate;
    private Integer durationHours; // Use Integer so it can be null
    private boolean isNonExpiry; // New field
}
