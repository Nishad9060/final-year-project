package com.footonomy.following.controller;

import com.footonomy.following.dto.SearchResponse;
import com.footonomy.following.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/** Public — no auth. See docs/03_TRD.md §4 and docs/CLAUDE.md hard constraints. */
@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @GetMapping
    public SearchResponse search(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) String type) {
        return searchService.search(q, type);
    }
}
