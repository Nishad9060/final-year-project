import { useState } from 'react';
import { useParams } from 'react-router-dom';
import * as api from '../api/client';
import { useApiData } from '../hooks/useApiData';
import { findTournament, mockStandings, mockFixtures } from '../mocks/data';
import StandingsTable from '../components/matches/StandingsTable';
import MatchList from '../components/matches/MatchList';
import FollowButton from '../components/shared/FollowButton';
import ErrorState from '../components/shared/ErrorState';

const TABS = ['Standings', 'Fixtures'];

// Public route — /tournament/:id, no auth check. Per docs/01_SRS.md FR-8.
export default function TournamentDetail() {
  const { id } = useParams();
  const [active, setActive] = useState('Standings');

  const { data: tournament } = useApiData(() => api.getTournament(id), findTournament(id), [id]);
  const { data: standings } = useApiData(
    () => api.getTournamentStandings(id),
    mockStandings[id] || [],
    [id],
  );
  const { data: fixtures } = useApiData(
    () => api.getTournamentFixtures(id),
    mockFixtures[id] || [],
    [id],
  );

  if (!tournament) {
    return (
      <div className="max-w-max-width mx-auto w-full px-margin-mobile lg:px-margin-desktop py-xl">
        <ErrorState title="Tournament not found" description="This tournament doesn't exist or its ID is invalid." />
      </div>
    );
  }

  return (
    <div className="max-w-max-width mx-auto w-full px-margin-mobile lg:px-margin-desktop py-lg">
      <div className="flex items-center justify-between mb-lg gap-md">
        <div>
          <h1 className="font-headline-lg text-headline-lg text-on-surface">{tournament.name}</h1>
          <p className="font-body-md text-body-md text-on-surface-variant">{tournament.season}</p>
        </div>
        <FollowButton entityType="tournament" entityId={tournament.id} />
      </div>

      <nav className="flex items-center gap-lg bg-surface-container-lowest rounded-xl p-2 shadow-inner mb-lg w-fit" role="tablist">
        {TABS.map((tab) => (
          <button
            key={tab}
            role="tab"
            aria-selected={active === tab}
            onClick={() => setActive(tab)}
            className={`font-data-tabular text-data-tabular px-6 py-2 rounded-lg transition-all ${
              active === tab
                ? 'text-on-primary-container bg-primary-container shadow-sm'
                : 'text-on-surface-variant hover:text-on-surface hover:bg-surface-container'
            }`}
          >
            {tab}
          </button>
        ))}
      </nav>

      {active === 'Standings' ? (
        <StandingsTable standings={standings} />
      ) : (
        <MatchList matches={fixtures} groupByTournament={false} />
      )}
    </div>
  );
}
