package com.footonomy.following.service;

import com.footonomy.auth.repository.UserRepository;
import com.footonomy.common.exception.DuplicateResourceException;
import com.footonomy.common.exception.ResourceNotFoundException;
import com.footonomy.following.dto.CreateFollowingRequest;
import com.footonomy.following.dto.FollowingResponse;
import com.footonomy.following.entity.FollowedTeam;
import com.footonomy.following.entity.FollowedTournament;
import com.footonomy.following.repository.FollowedTeamRepository;
import com.footonomy.following.repository.FollowedTournamentRepository;
import com.footonomy.matches.repository.TeamRepository;
import com.footonomy.matches.repository.TournamentRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FollowingService {

    private final FollowedTeamRepository followedTeamRepository;
    private final FollowedTournamentRepository followedTournamentRepository;
    private final TeamRepository teamRepository;
    private final TournamentRepository tournamentRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<FollowingResponse> listFollowing(UUID userId) {
        List<FollowingResponse> result = new ArrayList<>();
        followedTeamRepository.findByUserId(userId).forEach(f -> result.add(FollowingResponse.fromTeam(f)));
        followedTournamentRepository.findByUserId(userId).forEach(f -> result.add(FollowingResponse.fromTournament(f)));
        return result;
    }

    @Transactional
    public FollowingResponse follow(UUID userId, CreateFollowingRequest request) {
        return switch (request.entityType()) {
            case "team" -> followTeam(userId, request.entityId());
            case "tournament" -> followTournament(userId, request.entityId());
            default -> throw new IllegalArgumentException("entityType must be 'team' or 'tournament'");
        };
    }

    @Transactional
    public void unfollow(UUID userId, UUID followingId) {
        var followedTeam = followedTeamRepository.findByIdAndUserId(followingId, userId);
        if (followedTeam.isPresent()) {
            followedTeamRepository.delete(followedTeam.get());
            return;
        }
        var followedTournament = followedTournamentRepository.findByIdAndUserId(followingId, userId);
        if (followedTournament.isPresent()) {
            followedTournamentRepository.delete(followedTournament.get());
            return;
        }
        throw new ResourceNotFoundException("Following not found: " + followingId);
    }

    private FollowingResponse followTeam(UUID userId, UUID teamId) {
        if (!teamRepository.existsById(teamId)) {
            throw new ResourceNotFoundException("Team not found: " + teamId);
        }
        if (followedTeamRepository.existsByUserIdAndTeamId(userId, teamId)) {
            throw new DuplicateResourceException("Already following this team");
        }
        FollowedTeam followedTeam = new FollowedTeam();
        followedTeam.setUser(userRepository.getReferenceById(userId));
        followedTeam.setTeam(teamRepository.getReferenceById(teamId));
        return FollowingResponse.fromTeam(followedTeamRepository.save(followedTeam));
    }

    private FollowingResponse followTournament(UUID userId, UUID tournamentId) {
        if (!tournamentRepository.existsById(tournamentId)) {
            throw new ResourceNotFoundException("Tournament not found: " + tournamentId);
        }
        if (followedTournamentRepository.existsByUserIdAndTournamentId(userId, tournamentId)) {
            throw new DuplicateResourceException("Already following this tournament");
        }
        FollowedTournament followedTournament = new FollowedTournament();
        followedTournament.setUser(userRepository.getReferenceById(userId));
        followedTournament.setTournament(tournamentRepository.getReferenceById(tournamentId));
        return FollowingResponse.fromTournament(followedTournamentRepository.save(followedTournament));
    }
}
