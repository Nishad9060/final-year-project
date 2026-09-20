import { Link } from 'react-router-dom';
import TeamBadge from '../shared/TeamBadge';
import FollowButton from '../shared/FollowButton';
import EmptyState from '../shared/EmptyState';

function Section({ title, children, count }) {
  if (count === 0) return null;
  return (
    <div className="flex flex-col gap-sm">
      <h2 className="font-label-caps text-label-caps text-on-surface-variant uppercase tracking-wider">{title}</h2>
      <div className="flex flex-col gap-xs">{children}</div>
    </div>
  );
}

// Results grouped by entity type per docs/01_SRS.md FR-10. Player results
// depend on data-source coverage (SRS §4.3 note) — the section simply
// doesn't render when empty, same as any other empty group here.
export default function SearchResults({ results, query }) {
  const { teams = [], tournaments = [], players = [] } = results;
  const total = teams.length + tournaments.length + players.length;

  if (query && total === 0) {
    return <EmptyState icon="search_off" title="No results found" description={`Nothing matched "${query}".`} />;
  }

  if (!query) {
    return (
      <EmptyState icon="search" title="Search Footonomy" description="Find teams, tournaments, and players." />
    );
  }

  return (
    <div className="flex flex-col gap-xl">
      <Section title="Teams" count={teams.length}>
        {teams.map((team) => (
          <div key={team.id} className="flex items-center justify-between bg-surface-container rounded-xl p-md shadow-sm">
            <TeamBadge team={team} showName />
            <FollowButton entityType="team" entityId={team.id} />
          </div>
        ))}
      </Section>

      <Section title="Tournaments" count={tournaments.length}>
        {tournaments.map((t) => (
          <div key={t.id} className="flex items-center justify-between bg-surface-container rounded-xl p-md shadow-sm">
            <Link to={`/tournament/${t.id}`} className="font-body-md text-body-lg text-on-surface hover:text-primary transition-colors">
              {t.name}
            </Link>
            <FollowButton entityType="tournament" entityId={t.id} />
          </div>
        ))}
      </Section>

      <Section title="Players" count={players.length}>
        {players.map((p) => (
          <div key={p.id} className="flex items-center bg-surface-container rounded-xl p-md shadow-sm">
            <span className="font-body-md text-body-lg text-on-surface">{p.name}</span>
          </div>
        ))}
      </Section>
    </div>
  );
}
