package com.myfolio.service;

import com.myfolio.dto.educationdto.EducationRequestDTO;
import com.myfolio.dto.educationdto.EducationResponseDTO;
import com.myfolio.entity.Education;
import com.myfolio.entity.User;
import com.myfolio.repository.EducationRepository;
import com.myfolio.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EducationService {

    private final UserRepository userRepository;
    private final EducationRepository educationRepository;

    public Education toEntity(EducationRequestDTO educationRequestDTO, User user) {
        return Education.builder()
                .user(user)
                .schoolName(educationRequestDTO.getSchoolName())
                .status(educationRequestDTO.getStatus())
                .major(educationRequestDTO.getMajor())
                .degree(educationRequestDTO.getDegree())
                .startDate(educationRequestDTO.getStartDate())
                .endDate(educationRequestDTO.getEndDate())
                .build();
    }

    public EducationResponseDTO toDto(Education education) {
        return EducationResponseDTO.builder()
                .id(education.getId())
                .username(education.getUser().getUsername())
                .schoolName(education.getSchoolName())
                .status(education.getStatus())
                .major(education.getMajor())
                .degree(education.getDegree())
                .startDate(education.getStartDate())
                .endDate(education.getEndDate())
                .build();
    }

    public EducationResponseDTO createEducation(EducationRequestDTO educationRequestDTO) {
        User user = userRepository.findById(educationRequestDTO.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
        Education education = toEntity(educationRequestDTO, user);
        Education savedEducation = educationRepository.save(education);
        return toDto(savedEducation);
    }

    public EducationResponseDTO getEducation(Long id) {
        return toDto(findEducationEntityById(id));
    }

    public Education findEducationEntityById(Long id) {
        return educationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Education not found with id: " + id));
    }

    public List<EducationResponseDTO> getAllEducations() {
        return educationRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    public EducationResponseDTO updateEducation(Long id, EducationRequestDTO educationRequestDTO) {
        Education education = findEducationEntityById(id);
        education.setSchoolName(educationRequestDTO.getSchoolName());
        education.setStatus(educationRequestDTO.getStatus());
        education.setMajor(educationRequestDTO.getMajor());
        education.setDegree(educationRequestDTO.getDegree());
        education.setStartDate(educationRequestDTO.getStartDate());
        education.setEndDate(educationRequestDTO.getEndDate());
        Education educationSaved = educationRepository.save(education);
        return toDto(educationSaved);
    }

    public void deleteEducation(Long id) {
        educationRepository.deleteById(id);
    }
}
