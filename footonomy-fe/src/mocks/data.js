// Mock data for pre-backend development. footonomy-be has no controllers
// yet (only a DB schema + 2 JPA entities as of this build), so every screen
// renders against this module until real endpoints exist — swap by pointing
// src/api/client.js's BASE_URL at a live backend and removing these imports.
//
// Shape assumptions beyond the frozen contract (docs/03_TRD.md §4 only
// specifies paths/methods, not JSON body shapes; docs/07_Data_Dictionary.md
// is the source of truth for field names, used verbatim/snake_case here):
//   - Match/Standing responses nest full `team`/`tournament` objects rather
//     than bare *_id foreign keys, since the frontend has no other way to
//     render a name/crest without a second round-trip. Reconcile with
//     whoever owns the API contract once matches/standings endpoints exist.
//   - `minute` on a live match is NOT in the Data Dictionary. Treated as
//     optional UI enrichment — components must render correctly without it.

export const mockTeams = {
  arsenal: { id: 't-ars', name: 'Arsenal', short_name: 'ARS', logo_url: null, external_id: 'ext-ars' },
  liverpool: { id: 't-liv', name: 'Liverpool', short_name: 'LIV', logo_url: null, external_id: 'ext-liv' },
  mancity: { id: 't-mci', name: 'Man City', short_name: 'MCI', logo_url: null, external_id: 'ext-mci' },
  chelsea: { id: 't-che', name: 'Chelsea', short_name: 'CHE', logo_url: null, external_id: 'ext-che' },
  realmadrid: { id: 't-rma', name: 'Real Madrid', short_name: 'RMA', logo_url: null, external_id: 'ext-rma' },
  barcelona: { id: 't-bar', name: 'Barcelona', short_name: 'BAR', logo_url: null, external_id: 'ext-bar' },
  bayern: { id: 't-bay', name: 'Bayern Munich', short_name: 'BAY', logo_url: null, external_id: 'ext-bay' },
  psg: { id: 't-psg', name: 'PSG', short_name: 'PSG', logo_url: null, external_id: 'ext-psg' },
};

export const mockTournaments = [
  { id: 'tr-pl', name: 'Premier League', season: '2025/26', external_id: 'ext-pl' },
  { id: 'tr-ll', name: 'La Liga', season: '2025/26', external_id: 'ext-ll' },
  { id: 'tr-ucl', name: 'UEFA Champions League', season: '2025/26', external_id: 'ext-ucl' },
  { id: 'tr-bl', name: 'Bundesliga', season: '2025/26', external_id: 'ext-bl' },
  { id: 'tr-sa', name: 'Serie A', season: '2025/26', external_id: 'ext-sa' },
  { id: 'tr-l1', name: 'Ligue 1', season: '2025/26', external_id: 'ext-l1' },
];

const now = new Date();
const iso = (offsetMinutes) => new Date(now.getTime() + offsetMinutes * 60000).toISOString();

export const mockMatches = [
  {
    id: 'm-1',
    tournament: mockTournaments[0],
    home_team: mockTeams.arsenal,
    away_team: mockTeams.liverpool,
    kickoff_time: iso(-72),
    status: 'live',
    minute: 72,
    home_score: 2,
    away_score: 1,
    last_synced_at: iso(-1),
    // Timeline/Stats are marked "(if data available)" in
    // docs/02_IA_Sitemap.md, and player-level detail is called out as
    // out-of-scope for the free data tier in docs/01_SRS.md. Only this one
    // mock match carries events/stats, so the UI demonstrates both the
    // populated state and the "not available" fallback other matches use.
    events: [
      { id: 'e-1', minute: 14, type: 'goal', team: 'home', player: 'Bukayo Saka', assist: 'Martin Ødegaard' },
      { id: 'e-2', minute: 41, type: 'goal', team: 'away', player: 'Darwin Núñez', assist: 'Trent Alexander-Arnold' },
      { id: 'e-3', minute: 58, type: 'yellow_card', team: 'home', player: 'Declan Rice' },
      { id: 'e-4', minute: 67, type: 'goal', team: 'home', player: 'Gabriel Martinelli' },
    ],
    stats: {
      possession: [54, 46],
      shots: [12, 9],
      xg: [1.84, 1.12],
    },
  },
  {
    id: 'm-2',
    tournament: mockTournaments[0],
    home_team: mockTeams.mancity,
    away_team: mockTeams.chelsea,
    kickoff_time: iso(180),
    status: 'scheduled',
    home_score: null,
    away_score: null,
    last_synced_at: iso(-1),
  },
  {
    id: 'm-3',
    tournament: mockTournaments[1],
    home_team: mockTeams.realmadrid,
    away_team: mockTeams.barcelona,
    kickoff_time: iso(-300),
    status: 'finished',
    home_score: 3,
    away_score: 0,
    last_synced_at: iso(-60),
  },
  {
    id: 'm-4',
    tournament: mockTournaments[2],
    home_team: mockTeams.bayern,
    away_team: mockTeams.psg,
    kickoff_time: iso(240),
    status: 'scheduled',
    home_score: null,
    away_score: null,
    last_synced_at: iso(-1),
  },
];

export const mockStandings = {
  'tr-pl': [
    { id: 's-1', team: mockTeams.arsenal, position: 1, played: 24, won: 18, drawn: 4, lost: 2, points: 58 },
    { id: 's-2', team: mockTeams.mancity, position: 2, played: 24, won: 17, drawn: 5, lost: 2, points: 56 },
    { id: 's-3', team: mockTeams.liverpool, position: 3, played: 24, won: 16, drawn: 6, lost: 2, points: 54 },
    { id: 's-4', team: mockTeams.chelsea, position: 4, played: 24, won: 14, drawn: 5, lost: 5, points: 47 },
  ],
  'tr-ll': [
    { id: 's-5', team: mockTeams.realmadrid, position: 1, played: 25, won: 19, drawn: 4, lost: 2, points: 61 },
    { id: 's-6', team: mockTeams.barcelona, position: 2, played: 25, won: 18, drawn: 5, lost: 2, points: 59 },
  ],
};

export const mockFixtures = {
  'tr-pl': mockMatches.filter((m) => m.tournament.id === 'tr-pl'),
  'tr-ll': mockMatches.filter((m) => m.tournament.id === 'tr-ll'),
  'tr-ucl': mockMatches.filter((m) => m.tournament.id === 'tr-ucl'),
};

export function findMatch(id) {
  return mockMatches.find((m) => m.id === id) || null;
}

export function findTournament(id) {
  return mockTournaments.find((t) => t.id === id) || null;
}

export function searchAll(query) {
  const q = query.trim().toLowerCase();
  if (!q) return { teams: [], tournaments: [], players: [] };
  return {
    teams: Object.values(mockTeams).filter((t) => t.name.toLowerCase().includes(q)),
    tournaments: mockTournaments.filter((t) => t.name.toLowerCase().includes(q)),
    players: [],
  };
}
