# Google Stitch Prompt Sequence — Footonomy Web App

Use in order. Run Prompt 1 first to establish the design system on one screen, review it, THEN proceed to batches. Do not paste all of this as one giant prompt — Stitch produces more consistent results from staged, incremental prompts than from one massive request. After Prompt 1, use Stitch's multi-select (Shift+Click on generated screens) + a short "match this style" follow-up prompt to keep later screens consistent.

Mode: Web (not mobile). Model: latest available Gemini Pro option if offered, for better design reasoning.

---

## PROMPT 1 — Foundation (run first, alone)

```
Design a responsive web app homescreen for "Footonomy," a football (soccer) 
companion web app — live scores, tournaments, and personalized following. 
Target users: everyday football fans across all clubs and leagues, not fans 
of any single team. Platform: responsive web (desktop primary, must also 
work as tablet and mobile web layouts — not a native app).

Design direction: clean, minimal, restrained layout with strong information 
hierarchy — similar in spirit to FotMob's calm, uncluttered approach, but 
NOT boring: the interface should feel modern and energetic through color 
and typography choices, not through clutter or decoration.

Theme: dark mode as default and primary.

Color palette (use exactly):
- Background: #0A0A0B
- Card/elevated surface: #131316
- Higher elevation (modals/dropdowns): #1C1C21
- Border/divider: #27272A
- Primary text: #FFFFFF
- Secondary text: #A1A1AA
- Tertiary/muted text: #71717A
- Accent (primary interactive color, use sparingly and deliberately — 
  buttons, active nav state, links, follow-button active state): #7C3AED
- Accent hover state: #9333EA
- Live match indicator (reserved ONLY for live status, never anything 
  else): #EF4444
- Positive stat/highlight: #22C55E

Typography: clean modern sans-serif, strong size contrast between 
scores/numbers (large, bold) and supporting text (smaller, restrained). 
Scores should feel like the visual hero of the match card, not buried 
in body text.

Build this screen: Home / Matches feed.
- Persistent top navigation bar: logo wordmark "Footonomy" (simple 
  typographic treatment, not a polished logo), nav links (Matches, 
  Tournaments, Search), account/login control on the right
- Below nav: filter controls for Date and League (tabs or dropdown, 
  your choice — keep minimal)
- Main content: list of match cards grouped by league, each showing 
  team names/badges, score, and match status
- Design THREE match card states clearly distinguishable: LIVE (uses 
  the red indicator + minute marker), UPCOMING (shows kickoff time, 
  no score yet), FINISHED (final score, muted/de-emphasized compared 
  to live)
- The accent violet should appear only on: active nav item, any 
  primary button, and small deliberate highlight touches — not as a 
  dominant color across the page
```

---

## PROMPT 2 — Match Detail (run after reviewing Prompt 1 output; select the Home screen first, then prompt)

```
Using the same design system and components as the Home screen, design 
the Match Detail page. Layout: score header at top (both teams, badges, 
score, match status same as card states), followed by a tab bar: 
Overview, Stats, Timeline, Standings. Design the Overview and Stats 
tabs in full detail; Timeline and Standings tabs can show the tab 
structure with placeholder content indicating what will go there. 
Keep spacing, card styling, and color usage identical to Home.
```

---

## PROMPT 3 — Tournaments (batch with Standings + Fixtures)

```
Using the same design system, design the Tournaments section:
1. Tournament list/browse screen — simple list of the top 5 European 
   leagues plus Champions League, each with league badge/logo and name
2. Tournament Detail screen with two tabs: Standings (a clean table — 
   position, team, played, won, drawn, lost, points) and Fixtures 
   (reuse the same match card component from Home, not a new design)
Keep all card, table, and typography styling consistent with the 
previous screens.
```

---

## PROMPT 4 — Search + Auth (batch)

```
Using the same design system, design two screens:
1. Search — a search bar at top, results grouped into three sections: 
   Teams, Tournaments, Players. Each result row uses the same 
   badge/avatar + name pattern as elsewhere in the app. Include an 
   empty "no results found" state.
2. Login and Signup screens — simple centered forms, minimal fields 
   (email, password; signup adds a confirm field), primary button 
   using the accent violet, clean and uncluttered, dark theme 
   consistent with the rest of the app.
```

---

## PROMPT 5 — Following + Settings (batch)

```
Using the same design system, design two screens:
1. Following — a personalized feed of followed teams/tournaments 
   using the same match card component. Design an explicit EMPTY 
   STATE for users with no follows yet — friendly, clear call-to-action 
   to go discover teams to follow, not just blank space.
2. Settings — profile section (avatar, name, email, edit control) and 
   a preferences section with toggle controls for notification/display 
   preferences. Keep toggles and form styling consistent with the 
   Auth screens.
```

---

## PROMPT 6 — Responsive check (run last)

```
Show the Home/Matches screen and Match Detail screen adapted for 
mobile web viewport (375px width). Keep the same design system. Show 
how the top navigation collapses (your recommendation — hamburger 
menu or bottom tab bar, pick one and apply it consistently across 
both screens).
```

---

## After generation
- Export via Stitch's Figma or HTML/CSS export (Figma export is only available in Standard Mode — confirm you're in that mode before relying on it)
- Do a manual consistency pass across all screens before handing to devs — Stitch is documented as stronger on mobile-app generation than web, so web output specifically may need manual correction on spacing/layout edge cases
- Once finalized, update `CLAUDE.md` to point to the actual exported screens/Figma link instead of "PENDING"
