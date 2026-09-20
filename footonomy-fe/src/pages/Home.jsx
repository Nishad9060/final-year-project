import { useMemo, useState } from 'react';
import * as api from '../api/client';
import { useApiData } from '../hooks/useApiData';
import { mockMatches } from '../mocks/data';
import MatchFilterBar from '../components/matches/MatchFilterBar';
import MatchList from '../components/matches/MatchList';
import MatchSpotlight from '../components/matches/MatchSpotlight';
import DataStalenessBanner from '../components/shared/DataStalenessBanner';
import { MatchCardSkeleton } from '../components/shared/Skeleton';

function quickDateToIso(key) {
  const offsetDays = { yesterday: -1, today: 0, tomorrow: 1 }[key] ?? 0;
  const d = new Date();
  d.setDate(d.getDate() + offsetDays);
  return d.toISOString().slice(0, 10);
}

// Public route — renders immediately for guests, no auth check anywhere in
// this page. Per docs/01_SRS.md FR-4.
export default function Home() {
  const [quickDate, setQuickDate] = useState('today');
  const [league, setLeague] = useState('');

  const { data: matches, loading, error } = useApiData(
    () => api.getMatches({ date: quickDateToIso(quickDate), league }),
    mockMatches,
    [quickDate, league],
  );

  const filtered = useMemo(
    () => (league ? matches.filter((m) => m.tournament?.external_id === league) : matches),
    [matches, league],
  );

  const spotlightMatch = filtered.find((m) => m.status === 'live') || filtered[0];
  const mostRecentSync = filtered.reduce(
    (latest, m) => (m.last_synced_at && m.last_synced_at > latest ? m.last_synced_at : latest),
    '',
  );

  return (
    <div className="max-w-max-width mx-auto w-full px-margin-mobile lg:px-margin-desktop py-lg">
      <MatchFilterBar
        activeQuickDate={quickDate}
        onQuickDateChange={setQuickDate}
        league={league}
        onLeagueChange={setLeague}
      />
      <DataStalenessBanner lastSyncedAt={mostRecentSync} />
      {error && <p className="text-live text-data-tabular mb-md">Showing cached results — live data is temporarily unavailable.</p>}
      <div className="grid grid-cols-1 lg:grid-cols-12 gap-xl relative">
        <div className="lg:col-span-8 flex flex-col gap-xl">
          {loading && filtered.length === 0 ? (
            <div className="flex flex-col gap-xs">
              {Array.from({ length: 4 }).map((_, i) => (
                <MatchCardSkeleton key={i} />
              ))}
            </div>
          ) : (
            <MatchList matches={filtered} />
          )}
        </div>
        <MatchSpotlight match={spotlightMatch} />
      </div>
    </div>
  );
}
