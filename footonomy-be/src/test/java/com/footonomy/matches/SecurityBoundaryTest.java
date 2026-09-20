package com.footonomy.matches;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Guards the exact boundary docs/CLAUDE.md and docs/01_SRS.md NFR-4 call out: public pages
 * (Matches, Tournaments, Search) must work with zero Authorization header, and only
 * /api/users/me/** may reject an unauthenticated request. This is R5 in docs/05_Risk_Register.md
 * — a common regression when auth is bolted on after the public pages already exist.
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class SecurityBoundaryTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void matchesIsPublicWithNoAuthorizationHeader() throws Exception {
        mockMvc.perform(get("/api/matches")).andExpect(status().isOk());
    }

    @Test
    void tournamentsIsPublicWithNoAuthorizationHeader() throws Exception {
        mockMvc.perform(get("/api/tournaments")).andExpect(status().isOk());
    }

    @Test
    void searchIsPublicWithNoAuthorizationHeader() throws Exception {
        mockMvc.perform(get("/api/search").param("q", "arsenal")).andExpect(status().isOk());
    }

    @Test
    void usersMeIsRejectedWithoutAToken() throws Exception {
        mockMvc.perform(get("/api/users/me")).andExpect(status().isUnauthorized());
    }

    @Test
    void usersMeFollowingIsRejectedWithoutAToken() throws Exception {
        mockMvc.perform(get("/api/users/me/following")).andExpect(status().isUnauthorized());
    }
}
