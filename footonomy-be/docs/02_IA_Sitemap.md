# Information Architecture & Sitemap — Footonomy V1

This document defines screen inventory and navigation structure. It should be checked against whatever Google Stitch produces — if the generated UI has screens or flows not listed here, reconcile one direction deliberately, don't let both drift independently.

## 1. Site Structure

```
Footonomy (Web App)
│
├── / (Home / Matches)
│   ├── Filter: Date, League
│   └── → Match Detail
│
├── /match/:id (Match Detail)
│   ├── Tab: Overview
│   ├── Tab: Stats (if data available)
│   ├── Tab: Timeline/Events (if data available)
│   └── Tab: Standings (mini, tournament context)
│
├── /tournaments (Tournament List)
│   └── → Tournament Detail
│
├── /tournament/:id (Tournament Detail)
│   ├── Tab: Standings
│   └── Tab: Fixtures
│
├── /search (Search)
│   ├── Results: Teams
│   ├── Results: Tournaments
│   └── Results: Players
│
├── /login (Auth — Login)
├── /signup (Auth — Signup)
│
├── /following (Following) — AUTH REQUIRED
│   ├── Empty state (no follows)
│   └── Feed of followed teams/tournaments
│
└── /settings (Settings) — AUTH REQUIRED
    ├── Profile
    └── Preferences
```

## 2. Navigation Rules
- Top nav present on all pages: Logo, Matches, Tournaments, Search, Account/Login
- Guest users see "Log In" in nav; authenticated users see Avatar → Following, Settings, Logout
- Attempting to access `/following` or `/settings` while unauthenticated redirects to `/login`, then back to the originally requested page after successful login

## 3. Cross-Screen Components (must be consistent everywhere they appear)
- **Match card**: used on Home, Tournament Detail (fixtures), Following feed — same component, same states (live/upcoming/finished) in all three places
- **Team badge/row**: used on Search results, Standings table, Following feed
- **Follow button**: appears on team/tournament context (Search results, Tournament Detail, Match Detail) — same visual state logic everywhere

## 4. Responsive Behavior
- Desktop: persistent top nav
- Tablet/Mobile web: nav collapses to hamburger or bottom tab bar (pick one, apply consistently — do not mix patterns across screens)

## 5. Empty & Error States to Design For
- Following: no follows yet
- Search: no results found
- Match/Tournament data temporarily unavailable (API rate-limited or down) — this WILL happen given C-2 in the SRS; do not skip this state
