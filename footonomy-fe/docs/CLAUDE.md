# Footonomy V1 — Agent Project Brief

Read this first. Then reference the linked docs for detail before writing code — do not infer scope or architecture from partial context.

## Project
Football companion web app — top 5 European leagues + Champions League. Live-ish scores, tournaments/standings, following, search. Guest-accessible for browsing; auth required only for Following and Settings.

## Docs (read in this order)
1. `01_SRS.md` — functional/non-functional requirements, explicit scope boundaries
2. `02_IA_Sitemap.md` — screens, navigation, routing structure
3. `03_TRD.md` — architecture, data model, API contract, tech stack
4. `04_Test_Plan.md` — what must work before this is considered done
5. `05_Risk_Register.md` — known failure modes, read before touching auth or third-party API integration

## Hard Constraints — do not deviate without human sign-off
- Tech stack: React (frontend), Spring Boot (Java, backend), PostgreSQL. Do not substitute frameworks.
- Data source: football-data.org free tier — 10 req/min. All third-party calls MUST go through backend caching. Never call it directly from frontend code.
- Auth: JWT-based, email+password. Public pages (Matches, Tournaments, Search) must have zero auth-check overhead — do not add middleware that gates these.
- Do NOT build: Games module, Content Pipeline, native mobile, player lineups/stats (not in free tier), real-time WebSocket updates.
- API contract in `03_TRD.md` §4 is frozen. If a change seems necessary, flag it to the human — do not silently modify request/response shapes.

## Visual Design System
- Dark theme default. Palette:
  - Background: `#0A0A0B` | Elevated surface: `#131316` | Higher elevation: `#1C1C21` | Border: `#27272A`
  - Text primary: `#FFFFFF` | Secondary: `#A1A1AA` | Tertiary: `#71717A`
  - Accent (primary interactive color): `#7C3AED`, hover `#9333EA` — use for buttons, active states, links. Do NOT apply broadly to backgrounds/borders — accent should stay sparse and intentional.
  - Live indicator: `#EF4444` (red) — reserved exclusively for live-match status, never reused for other meaning
  - Positive stat: `#22C55E`
- Actual screen layouts/components: [PENDING — Google Stitch export not yet generated. Do not invent layout from scratch; flag if this is missing when you reach frontend component work.]

## Module Ownership (for commit/branch conventions, not enforcement logic)
- Person A: Data ingestion, Match/Tournament API + UI
- Person B: Auth, User/Preferences + UI
- Person C: Following, Search + UI

## App Name
Placeholder: "Footonomy" (NOT FINAL — domain unavailable, real name pending). Do not hardcode this name into anything hard to find/replace later (avoid embedding in package names, database names, etc. where possible — use a generic project identifier instead).

## Secrets
Never commit API keys, DB credentials, or JWT secrets to the repo. Use environment variables / platform secret management per `03_TRD.md` §7.
