import { useParams } from 'react-router-dom';
import * as api from '../api/client';
import { useApiData } from '../hooks/useApiData';
import { findMatch, mockStandings } from '../mocks/data';
import MatchDetailHeader from '../components/matches/MatchDetailHeader';
import MatchTabs from '../components/matches/MatchTabs';
import ErrorState from '../components/shared/ErrorState';

// Public route — /match/:id, no auth check. Per docs/01_SRS.md FR-3/FR-4.
export default function MatchDetail() {
  const { id } = useParams();

  const { data: match, loading, error } = useApiData(
    () => api.getMatch(id),
    findMatch(id),
    [id],
  );

  if (!loading && !match) {
    return (
      <div className="max-w-max-width mx-auto w-full px-margin-mobile lg:px-margin-desktop py-xl">
        <ErrorState
          title="Match not found"
          description="This match doesn't exist or its ID is invalid."
        />
      </div>
    );
  }

  if (!match) {
    return (
      <div className="max-w-max-width mx-auto w-full px-margin-mobile lg:px-margin-desktop py-xl text-on-surface-variant text-data-tabular">
        {error ? <ErrorState /> : 'Loading match...'}
      </div>
    );
  }

  const standings = mockStandings[match.tournament?.id] || [];

  return (
    <div className="flex flex-col w-full">
      <MatchDetailHeader match={match} />
      <MatchTabs match={match} standings={standings} />
    </div>
  );
}
