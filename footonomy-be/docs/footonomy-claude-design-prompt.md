# Claude Design Prompt — Footonomy Web App (UI Only, Non-Functional)

## Context
Design the complete UI for **Footonomy**, a football (soccer) companion web app — live scores, match stats, tournaments/standings, personalized following, and search. Positioned as a broad, all-club competitor in the category of Sofascore/FotMob/OneFootball — not affiliated with or branded around any single club.

This is a **static, non-functional UI design pass** — like a Figma file, not a working app. No backend, no real data logic. Use realistic placeholder content (real team names, competitions, plausible scores/stats) so it reads as a real product, not a wireframe with "Lorem Ipsum."

**Platform:** Web app only, responsive (desktop primary, must also hold up at tablet and mobile web breakpoints — this is not a native mobile app screen set).

## Step 1 — Establish the design system first
Before screens, define and hold to a consistent system:
- **Color palette**: dark mode as the default/primary theme. Propose 2–3 accent color options (e.g., a fresh green, an electric blue, an amber/orange) as separate palette swatches I can choose between — don't commit to just one.
- **Typography**: clean, minimal, high-legibility sans-serif system (FotMob-like restraint) — clear hierarchy for scores, team names, stats, body text.
- **Spacing/grid system**, corner radius, elevation/surface layering for dark mode (cards, modals, nav).
- **Core components**: nav bar/sidebar, match card (live/upcoming/finished states), team badge treatment, stat bar/comparison component, tab bar, buttons (primary/secondary), input fields, empty states, badges/tags (e.g., "LIVE", league tags).

Apply this system identically across every screen below — no per-screen style drift.

## Step 2 — Screens (full app, end-to-end)

**Navigation shell**
- Persistent top nav (desktop) with logo ("Footonomy" placeholder wordmark), primary sections, search entry point, account/avatar
- Responsive collapse behavior for tablet/mobile web (indicate how nav adapts, doesn't need every breakpoint fully drawn — desktop + one mobile-web reference per screen is enough)

**1. Matches / Home**
- Live scores feed, grouped by league/date
- Filter/tab controls (Live / Today / Upcoming)
- Match card states: live (with minute/score), upcoming (with kickoff time), finished (final score)

**2. Match Detail**
- Score header (teams, score, status)
- Tabs: Overview, Lineups, Stats, Timeline/Events, Standings (mini)
- At least Overview and Stats tabs fully designed; others can show tab structure with representative content

**3. Tournaments**
- League/competition browse list
- Tournament detail page: standings table, fixtures list, top stats (optional)

**4. Following**
- Personalized feed of followed teams/tournaments
- Empty state (no follows yet) — design this explicitly, first-run experience matters
- Follow/unfollow control shown in context on a team or match card

**5. Search**
- Search bar with live-typing state (show as a static "results appearing" mock)
- Results grouped by type: Teams, Tournaments, Players (if in scope — treat players as optional/secondary)

**6. Auth**
- Login screen
- Signup screen
- (Password reset optional, lower priority)

**7. Settings**
- Profile/account section
- Preferences (favorite teams/leagues, notification toggles — static toggle states only)

## Step 3 — Deliverable expectations
- Consistent component reuse across all screens — same match card, same nav, same buttons everywhere, not reinvented per screen
- Show at least one desktop frame and one mobile-web responsive frame per major screen (Matches, Match Detail, Following minimum)
- Realistic placeholder data throughout (real competitions/teams is fine and preferred over generic placeholders)
- No functional logic, no working links — this is a visual reference set for developers to build against, equivalent to a Figma handoff file

## Explicit constraints
- Do NOT reference or visually resemble any single football club's official branding or colors as the app's primary identity
- Do NOT design this as a native iOS/Android screen set — web app, responsive, only
- Wordmark/logo: use "Footonomy" as placeholder text logo — simple typographic treatment, not a final logo design
