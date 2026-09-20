# Risk Register — Footonomy V1

| # | Risk | Likelihood | Impact | Mitigation |
|---|---|---|---|---|
| R1 | 6-day timeline (Aug 8–14) insufficient for full scope across 3 people | High | High | Docs kept lightweight and actionable rather than exhaustive; cut scope (e.g., player search, notification functionality) before cutting quality on core Matches/Auth/Following |
| R2 | football-data.org 10 req/min limit exceeded during dev/testing with multiple people hitting it simultaneously | Medium | High | Server-side caching mandatory (TRD §5); never call third-party API directly from client; each dev should test against cached/mocked data, not live API, during development |
| R3 | Spring Boot unfamiliarity slows backend build if team hasn't used it before | Medium (confirm with team) | High | Confirm team comfort level immediately; if low, budget extra day 1-2 for setup/learning rather than discovering the gap mid-sprint |
| R4 | Mock-to-real data integration reveals field mismatches late (Aug 12-13 window) | High | Medium | API contract frozen early (target Aug 9); integration explicitly budgeted as 1-2 days of real work, not treated as trivial glue |
| R5 | Auth boundary bug — public pages accidentally gated behind login, or protected pages accidentally left open | Medium | Medium | Explicit test cases in Test Plan §2 (Auth); this is a common regression when auth is added after pages already exist |
| R6 | "Live" scores are actually delayed (data source limitation) and this isn't communicated, undermining the product's core pitch | High if unaddressed | Medium (reputational/grading risk) | UI explicitly shows staleness/last-synced indicator (TRD §5); frame as "near-live" in any submission documentation, don't overclaim |
| R7 | Contract ownership undefined — no one has authority to freeze/approve API contract changes | Medium | Medium | Assign explicit contract owner before build starts (TRD §2) |
| R8 | Player search (confirmed in scope) may not be well-supported by football-data.org free tier | Medium | Medium | Verify player entity availability in the API before building search UI around it; fall back to teams+tournaments only if player data is inadequate |
| R9 | Hosting platform undecided (Railway vs Render) causes late deployment scramble | Low-Medium | Medium | Decide by Aug 9, not during final submission prep |
| R10 | Games/Content Pipeline status ("done"/"rest done") unverified — may need rework once actually tested against V1 web app | Medium | Low (out of scope for this doc set, but affects later integration) | Verify actual functional state before assuming zero remaining work in future planning |
