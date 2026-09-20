package com.footonomy.matches.repository;

import com.footonomy.matches.entity.MatchDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MatchDetailRepository extends JpaRepository<MatchDetail, UUID> {
}
