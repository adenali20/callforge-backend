package com.adenali.callforge_backend.repository;

import com.adenali.callforge_backend.dto.VideoRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<VideoRoom, String> {
    // Basic CRUD methods like save() and findById() are inherited automatically
}
