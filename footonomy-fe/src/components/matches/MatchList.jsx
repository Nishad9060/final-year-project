import MatchCard from '../shared/MatchCard';
import EmptyState from '../shared/EmptyState';

// Groups matches by tournament with a sticky league header, used on Home.
// Tournament Detail (fixtures) and Following (feed) already know the
// tournament context, so they pass groupByTournament={false} for a flat
// list — same MatchCard either way, keeping it visually identical
// everywhere per docs/02_IA_Sitemap.md §3.
export default function MatchList({ matches, groupByTournament = true }) {
  if (!matches || matches.length === 0) {
    return (
      <EmptyState
        icon="sports_soccer"
        title="No matches found"
        description="Try a different date or league filter."
      />
    );
  }

  if (!groupByTournament) {
    return (
      <div className="flex flex-col gap-xs">
        {matches.map((match) => (
          <MatchCard key={match.id} match={match} />
        ))}
      </div>
    );
  }

  const groups = matches.reduce((acc, match) => {
    const key = match.tournament?.id || 'unknown';
    if (!acc[key]) acc[key] = { tournament: match.tournament, matches: [] };
    acc[key].matches.push(match);
    return acc;
  }, {});

  return (
    <div className="flex flex-col gap-xl">
      {Object.values(groups).map((group) => (
        <div key={group.tournament?.id} className="flex flex-col gap-xs relative">
          <div className="sticky top-16 z-40 bg-surface/80 backdrop-blur-md py-sm flex items-center gap-sm">
            <div className="w-6 h-6 rounded-full bg-surface-container flex items-center justify-center">
              <span className="material-symbols-outlined text-[14px] text-on-surface-variant">trophy</span>
            </div>
            <h2 className="font-label-caps text-label-caps text-on-surface-variant uppercase tracking-wider">
              {group.tournament?.name}
            </h2>
          </div>
          <div className="flex flex-col gap-xs">
            {group.matches.map((match) => (
              <MatchCard key={match.id} match={match} />
            ))}
          </div>
        </div>
      ))}
    </div>
  );
}
