package com.myfolio.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Link {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String title;

    @Column(length = 50)
    private String urlType; // 깃허브, 블로그 등

    @Column(length = 255)
    private String iconUrl; // 깃허브 로고, 블로그 로고 등

    @Column(length = 255)
    private String url;

    @Column(nullable = false)
    private Boolean isVisible = true; // 공개 or 비공개
}

