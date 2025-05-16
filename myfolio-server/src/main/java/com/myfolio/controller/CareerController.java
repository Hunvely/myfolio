package com.myfolio.controller;

import com.myfolio.dto.careerdto.CareerRequestDTO;
import com.myfolio.dto.careerdto.CareerResponseDTO;
import com.myfolio.entity.User;
import com.myfolio.repository.UserRepository;
import com.myfolio.service.CareerService;
import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/careers")
public class CareerController {

    private final CareerService careerService;
    private final UserRepository userRepository;

    @PostMapping
    public ResponseEntity<CareerResponseDTO> createCareer(@RequestBody CareerRequestDTO careerRequestDTO) {
        User user = userRepository.findById(careerRequestDTO.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + careerRequestDTO.getUserId()));
        return ResponseEntity.ok(careerService.createCareer(careerRequestDTO, user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CareerResponseDTO> getCareer(@PathVariable Long id) {
        return ResponseEntity.ok(careerService.getCareer(id));
    }

    @GetMapping
    public ResponseEntity<List<CareerResponseDTO>> getAllCareers() {
        return ResponseEntity.ok(careerService.getAllCareers());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CareerResponseDTO> updateCareer(@PathVariable Long id, @RequestBody CareerRequestDTO careerRequestDTO) {
        return ResponseEntity.ok(careerService.updateCareer(id, careerRequestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCareer(@PathVariable Long id) {
        careerService.deleteCareer(id);
        return ResponseEntity.noContent().build();
    }
}
