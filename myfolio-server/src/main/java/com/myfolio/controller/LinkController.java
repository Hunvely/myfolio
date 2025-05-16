package com.myfolio.controller;

import com.myfolio.dto.linkdto.LinkRequestDTO;
import com.myfolio.dto.linkdto.LinkResponseDTO;
import com.myfolio.service.LinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/links")
public class LinkController {

    private final LinkService linkService;

    @PostMapping
    public ResponseEntity<LinkResponseDTO> createLink(@RequestBody LinkRequestDTO linkRequestDTO) {
        return ResponseEntity.ok(linkService.createLink(linkRequestDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LinkResponseDTO> getLink(@PathVariable Long id) {
        return ResponseEntity.ok(linkService.getLink(id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<LinkResponseDTO>> getLinksByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(linkService.getLinksByUserId(userId));
    }

    @GetMapping
    public ResponseEntity<List<LinkResponseDTO>> getAllLinks() {
        return ResponseEntity.ok(linkService.getAllLinks());
    }

    @PutMapping("/{id}")
    public ResponseEntity<LinkResponseDTO> updateLink(@PathVariable Long id, @RequestBody LinkRequestDTO linkRequestDTO) {
        return ResponseEntity.ok(linkService.updateLink(id, linkRequestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLink(@PathVariable Long id) {
        linkService.deleteLink(id);
        return ResponseEntity.ok().build();
    }
}
