package com.myfolio.service;

import com.myfolio.dto.linkdto.LinkRequestDTO;
import com.myfolio.dto.linkdto.LinkResponseDTO;
import com.myfolio.entity.Link;
import com.myfolio.entity.User;
import com.myfolio.repository.LinkRepository;
import com.myfolio.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LinkService {

    private final LinkRepository linkRepository;
    private final UserRepository userRepository;

    public Link toEntity(LinkRequestDTO linkRequestDTO, User user) {
        return Link.builder()
                .user(user)
                .title(linkRequestDTO.getTitle())
                .urlType(linkRequestDTO.getUrlType())
                .iconUrl(linkRequestDTO.getIconUrl())
                .url(linkRequestDTO.getUrl())
                .isVisible(linkRequestDTO.getIsVisible())
                .build();
    }

    public LinkResponseDTO toDto(Link link) {
        return LinkResponseDTO.builder()
                .id(link.getId())
                .username(link.getUser().getUsername())
                .title(link.getTitle())
                .urlType(link.getUrlType())
                .iconUrl(link.getIconUrl())
                .url(link.getUrl())
                .isVisible(link.getIsVisible())
                .build();
    }

    public LinkResponseDTO createLink(LinkRequestDTO linkRequestDTO) {
        User user = userRepository.findById(linkRequestDTO.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
        Link link = toEntity(linkRequestDTO, user);
        Link savedLink = linkRepository.save(link);
        return toDto(savedLink);
    }

    public LinkResponseDTO getLink(Long id) {
        return toDto(findLinkEntityById(id));
    }

    public Link findLinkEntityById(Long id) {
        return linkRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Link not found with id: " + id));
    }

    public List<LinkResponseDTO> getLinksByUserId(Long userId) {
        return linkRepository.findByUserId(userId)
                .stream().map(this::toDto)
                .toList();
    }

    public List<LinkResponseDTO> getAllLinks() {
        return linkRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    public LinkResponseDTO updateLink(Long id, LinkRequestDTO linkRequestDTO) {
        Link link = findLinkEntityById(id);
        link.setTitle(linkRequestDTO.getTitle());
        link.setUrlType(linkRequestDTO.getUrlType());
        link.setIconUrl(linkRequestDTO.getIconUrl());
        link.setUrl(linkRequestDTO.getUrl());
        link.setIsVisible(linkRequestDTO.getIsVisible());
        Link savedLink = linkRepository.save(link);
        return toDto(savedLink);
    }

    public void deleteLink(Long id) {
        if (!linkRepository.existsById(id)) {
            throw new IllegalArgumentException("Link not found with id: " + id);
        }
        linkRepository.deleteById(id);
    }
}
