package com.myfolio.entity;

import com.myfolio.enums.EducationStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Education {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(length = 100)
    private String schoolName;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private EducationStatus status;

    @Column(length = 100)
    private String major;

    @Column(length = 100)
    private String degree;

    private LocalDate startDate;
    private LocalDate endDate;
}
