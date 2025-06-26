package com.develop.dental_api.api;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.develop.dental_api.model.dto.AvailabilityDTO;
import com.develop.dental_api.model.dto.CreateAvailabilityDTO;
import com.develop.dental_api.model.dto.UpdateAvailabilityDTO;
import com.develop.dental_api.service.AvailabilityService;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "https://odontologiaweb.netlify.app")
@RestController
@RequestMapping("/api/availabilities")
@RequiredArgsConstructor
public class AvailabilityController {

    private final AvailabilityService availabilityService;

    @GetMapping("/dentist/{dentistId}")
    public ResponseEntity<List<AvailabilityDTO>> getDentistAvailabilities(@PathVariable Integer dentistId) {
        return ResponseEntity.ok(availabilityService.getDentistAvailabilities(dentistId));
    }

    @PostMapping
    public ResponseEntity<AvailabilityDTO> addAvailability(@RequestBody CreateAvailabilityDTO dto) {
        return ResponseEntity.ok(availabilityService.addAvailability(dto));
    }

    @DeleteMapping("/{availabilityId}")
    public ResponseEntity<Void> deleteAvailability(@PathVariable Integer availabilityId) {
        availabilityService.deleteAvailability(availabilityId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{availabilityId}")
    public ResponseEntity<AvailabilityDTO> updateAvailability(
            @PathVariable Integer availabilityId,
            @RequestBody UpdateAvailabilityDTO dto) {
        return ResponseEntity.ok(availabilityService.updateAvailability(availabilityId, dto));
    }
}
