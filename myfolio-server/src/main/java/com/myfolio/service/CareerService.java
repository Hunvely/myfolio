package com.myfolio.service;

import com.myfolio.dto.careerdto.CareerRequestDTO;
import com.myfolio.dto.careerdto.CareerResponseDTO;
import com.myfolio.entity.Career;
import com.myfolio.entity.User;
import com.myfolio.repository.CareerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CareerService {

    private final CareerRepository careerRepository;

    public Career toEntity(CareerRequestDTO careerRequestDTO, User user) {
        return Career.builder()
                .user(user)
                .companyName(careerRequestDTO.getCompanyName())
                .location(careerRequestDTO.getLocation())
                .employmentType(careerRequestDTO.getEmploymentType())
                .position(careerRequestDTO.getPosition())
                .startDate(careerRequestDTO.getStartDate())
                .endDate(careerRequestDTO.getEndDate())
                .description(careerRequestDTO.getDescription())
                .build();
    }

    public CareerResponseDTO toDto(Career career) {
        return CareerResponseDTO.builder()
                .id(career.getId())
                .username(career.getUser().getUsername())
                .companyName(career.getCompanyName())
                .location(career.getLocation())
                .employmentType(career.getEmploymentType())
                .position(career.getPosition())
                .startDate(career.getStartDate())
                .endDate(career.getEndDate())
                .description(career.getDescription())
                .build();
    }

    public CareerResponseDTO createCareer(CareerRequestDTO careerRequestDTO, User user) {
        Career career = toEntity(careerRequestDTO, user);
        Career careerSaved = careerRepository.save(career);
        return toDto(careerSaved);
    }

    public Career findCareerEntityById(Long id) {
        return careerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Career not found with id: " + id));
    }

    public CareerResponseDTO getCareer(Long id) {
        return toDto(findCareerEntityById(id));
    }

    public List<CareerResponseDTO> getAllCareers() {
        return careerRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    public CareerResponseDTO updateCareer(Long id, CareerRequestDTO careerRequestDTO) {
        Career career = findCareerEntityById(id);
        career.setCompanyName(careerRequestDTO.getCompanyName());
        career.setLocation(careerRequestDTO.getLocation());
        career.setEmploymentType(careerRequestDTO.getEmploymentType());
        career.setPosition(careerRequestDTO.getPosition());
        career.setStartDate(careerRequestDTO.getStartDate());
        career.setEndDate(careerRequestDTO.getEndDate());
        career.setDescription(careerRequestDTO.getDescription());
        Career careerSaved = careerRepository.save(career);
        return toDto(careerSaved);
    }

    public void deleteCareer(Long id) {
        careerRepository.deleteById(id);
    }
}
