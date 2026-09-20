import * as api from '../api/client';
import { useApiData } from '../hooks/useApiData';
import { mockTournaments } from '../mocks/data';
import TournamentCard from '../components/matches/TournamentCard';

// Public route — /tournaments, no auth check. Per docs/01_SRS.md FR-8.
export default function Tournaments() {
  const { data: tournaments } = useApiData(api.getTournaments, mockTournaments, []);

  return (
    <div className="max-w-max-width mx-auto w-full px-margin-mobile lg:px-margin-desktop py-lg">
      <h1 className="font-headline-lg text-headline-lg text-on-surface mb-lg">Tournaments</h1>
      <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-gutter">
        {tournaments.map((t) => (
          <TournamentCard key={t.id} tournament={t} />
        ))}
      </div>
    </div>
  );
}
