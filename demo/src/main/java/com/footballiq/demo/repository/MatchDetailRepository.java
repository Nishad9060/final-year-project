package com.footballiq.demo.repository;

import com.footballiq.demo.entity.MatchDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchDetailRepository extends JpaRepository<MatchDetail, String> {
}
