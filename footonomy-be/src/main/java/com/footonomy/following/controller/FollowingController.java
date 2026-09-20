package com.footonomy.following.controller;

import com.footonomy.auth.security.CurrentUserProvider;
import com.footonomy.following.dto.CreateFollowingRequest;
import com.footonomy.following.dto.FollowingResponse;
import com.footonomy.following.service.FollowingService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Requires a valid JWT — see docs/03_TRD.md §4/§6 and docs/CLAUDE.md hard constraints. */
@RestController
@RequestMapping("/api/users/me/following")
@RequiredArgsConstructor
public class FollowingController {

    private final FollowingService followingService;
    private final CurrentUserProvider currentUserProvider;

    @GetMapping
    public List<FollowingResponse> listFollowing() {
        return followingService.listFollowing(currentUserProvider.requireCurrentUserId());
    }

    @PostMapping
    public ResponseEntity<FollowingResponse> follow(@Valid @RequestBody CreateFollowingRequest request) {
        FollowingResponse response = followingService.follow(currentUserProvider.requireCurrentUserId(), request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> unfollow(@PathVariable UUID id) {
        followingService.unfollow(currentUserProvider.requireCurrentUserId(), id);
        return ResponseEntity.noContent().build();
    }
}
