# Test Plan — Footonomy V1

Lightweight by design given the 6-day timeline. Purpose is to catch the failures most likely given this specific build (rate limits, mock-to-real drift, auth boundary bugs) — not exhaustive coverage.

## 1. Test Types in Scope
- **Manual functional testing** — primary method given timeline; each person tests their own module against SRS requirements before integration
- **API contract testing** — verify each endpoint matches the TRD §4 contract (request/response shape) before frontend integration begins
- **Cross-browser check** — Chrome + one other (Firefox or Safari), desktop + mobile web viewport, before submission

## 2. Priority Test Cases by Module

**Matches (Person A)**
- Match list loads and displays correct status (live/upcoming/finished)
- Filtering by date/league returns correct subset
- Match Detail loads for a valid match ID; handles invalid ID gracefully (no crash)
- Behavior when football-data.org rate limit is hit — cached data with staleness indicator shown, not a broken page

**Tournaments (Person A)**
- Standings table renders with correct sort order (by position)
- Fixtures list matches tournament

**Auth (Person B)**
- Signup with valid data succeeds; duplicate email rejected with clear error
- Login with correct/incorrect credentials
- Expired/invalid JWT correctly redirects to login, does not crash authenticated pages
- Public pages (Matches, Tournaments, Search) load with zero auth check delay for guest users — this is a specific regression risk since auth gets added after these pages exist

**Following (Person C)**
- Follow/unfollow updates persist and reflect immediately in UI
- Empty state displays correctly for new users
- Attempting to access `/following` while logged out redirects to login, then returns to `/following` after successful login

**Search (Person C)**
- Search returns results across teams/tournaments/players
- No-results state displays correctly
- Query with special characters/empty query doesn't break the endpoint

## 3. Integration Test Checklist (Aug 12–13 window)
- [ ] Each person's mock-data build swapped for real API — verify no silent field mismatches
- [ ] Follow button component behaves identically whether embedded on Match Detail, Tournament Detail, or Search results
- [ ] Auth boundary correctly gates Following/Settings but never gates Matches/Tournaments/Search
- [ ] Full user journey: guest browses matches → signs up → follows a team → sees it in Following feed

## 4. Acceptance Criteria for Submission
- All FR items in SRS §4 demonstrably working
- No unhandled crashes on core flows (match browsing, search, auth, following)
- Data staleness communicated in UI, not hidden (per TRD §5 failure handling)
