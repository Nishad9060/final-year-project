import MatchList from '../matches/MatchList';

// docs/03_TRD.md §4 has no dedicated "matches for my follows" endpoint, so
// the feed is composed client-side: fetch matches and keep only the ones
// involving a followed team or a followed tournament. Flag to the contract
// owner if this should become a real endpoint once match volume grows.
export default function FollowingFeed({ followingItems, matches }) {
  const followedTeamIds = new Set(
    followingItems.filter((f) => f.entity_type === 'team').map((f) => f.entity_id),
  );
  const followedTournamentIds = new Set(
    followingItems.filter((f) => f.entity_type === 'tournament').map((f) => f.entity_id),
  );

  const relevant = matches.filter(
    (m) =>
      followedTeamIds.has(m.home_team?.id) ||
      followedTeamIds.has(m.away_team?.id) ||
      followedTournamentIds.has(m.tournament?.id),
  );

  return <MatchList matches={relevant} />;
}
