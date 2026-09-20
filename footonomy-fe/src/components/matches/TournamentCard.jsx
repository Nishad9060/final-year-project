import { Link } from 'react-router-dom';
import FollowButton from '../shared/FollowButton';

export default function TournamentCard({ tournament }) {
  return (
    <div className="bg-surface-container hover:bg-surface-container-high transition-colors rounded-xl p-lg flex flex-col gap-md shadow-sm">
      <div className="flex items-center justify-between">
        <div className="w-12 h-12 rounded-full bg-surface-container-highest flex items-center justify-center">
          <span className="material-symbols-outlined text-primary">trophy</span>
        </div>
        <FollowButton entityType="tournament" entityId={tournament.id} />
      </div>
      <div>
        <Link to={`/tournament/${tournament.id}`} className="font-headline-md text-headline-md text-on-surface hover:text-primary transition-colors">
          {tournament.name}
        </Link>
        <p className="font-body-md text-body-md text-on-surface-variant mt-1">{tournament.season}</p>
      </div>
    </div>
  );
}
