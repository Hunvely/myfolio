package com.myfolio.service;

import com.myfolio.dto.userdto.UserRequestDTO;
import com.myfolio.dto.userdto.UserResponseDTO;
import com.myfolio.entity.User;
import com.myfolio.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User toEntity(UserRequestDTO userRequestDTO) {
        return User.builder()
                .username(userRequestDTO.getUsername())
                .password(userRequestDTO.getPassword())
                .email(userRequestDTO.getEmail())
                .phoneNumber(userRequestDTO.getPhoneNumber())
                .name(userRequestDTO.getName())
                .profileImage(userRequestDTO.getProfileImage())
                .bio(userRequestDTO.getBio())
                .build();
    }

    public UserResponseDTO toDTO(User user) {
        return UserResponseDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .name(user.getName())
                .profileImage(user.getProfileImage())
                .bio(user.getBio())
                .createdAt(user.getCreatedAt())
                .build();
    }

    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {
        User user = toEntity(userRequestDTO);
        User savedUser = userRepository.save(user);
        return toDTO(savedUser);
    }

    public User findUserEntityById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + id));
    }

    public UserResponseDTO getUserById(Long id) {
        return toDTO(findUserEntityById(id));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public UserResponseDTO updateUser(Long id, UserRequestDTO userRequestDTO) {
        User user = findUserEntityById(id);
        user.setUsername(userRequestDTO.getUsername());
        user.setPassword(userRequestDTO.getPassword());
        user.setEmail(userRequestDTO.getEmail());
        user.setPhoneNumber(userRequestDTO.getPhoneNumber());
        user.setName(userRequestDTO.getName());
        user.setProfileImage(userRequestDTO.getProfileImage());
        user.setBio(userRequestDTO.getBio());
        User updatedUser =  userRepository.save(user);
        return toDTO(updatedUser);
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException("user not found with id: " + id);
        }
        userRepository.deleteById(id);
    }
}
