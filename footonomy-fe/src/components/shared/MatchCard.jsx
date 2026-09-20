import { Link } from 'react-router-dom';
import TeamBadge from './TeamBadge';

function formatKickoff(isoString) {
  return new Date(isoString).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' });
}

function ClockCell({ match }) {
  if (match.status === 'live') {
    return (
      <span className="text-live font-bold text-data-tabular flex items-center gap-xs">
        <span className="w-2 h-2 rounded-full bg-live animate-pulse" />
        {match.minute ? `${match.minute}'` : 'LIVE'}
      </span>
    );
  }
  if (match.status === 'finished') {
    return <span className="text-outline text-label-caps">FT</span>;
  }
  return <span className="text-on-surface-variant text-data-tabular">{formatKickoff(match.kickoff_time)}</span>;
}

function ScoreCell({ value, dimmed }) {
  if (value === null || value === undefined) {
    return <span className="font-score-display text-headline-md text-outline-variant">-</span>;
  }
  return (
    <span
      className={`font-score-display text-headline-lg ${dimmed ? 'text-outline' : 'text-on-surface'}`}
    >
      {value}
    </span>
  );
}

// The core reused unit: Home, Tournament Detail (fixtures), Following feed —
// same component, same live/upcoming/finished states everywhere per
// docs/02_IA_Sitemap.md §3.
export default function MatchCard({ match }) {
  const finished = match.status === 'finished';
  const homeWon = finished && match.home_score > match.away_score;
  const awayWon = finished && match.away_score > match.home_score;

  return (
    <Link
      to={`/match/${match.id}`}
      className={`${
        finished ? 'bg-surface-container/50 hover:bg-surface-container' : 'bg-surface-container hover:bg-surface-container-high'
      } transition-colors rounded-xl p-md flex items-center shadow-sm cursor-pointer group`}
    >
      <div className="w-16 flex flex-col items-center justify-center border-r border-outline-variant/20 pr-md mr-md shrink-0">
        <ClockCell match={match} />
      </div>
      <div className={`flex-1 flex flex-col gap-sm min-w-0 ${finished ? 'opacity-80' : ''}`}>
        <div className="flex justify-between items-center gap-sm">
          <TeamBadge team={match.home_team} size="sm" showName />
          <ScoreCell value={match.home_score} dimmed={awayWon} />
        </div>
        <div className="flex justify-between items-center gap-sm">
          <TeamBadge team={match.away_team} size="sm" showName />
          <ScoreCell value={match.away_score} dimmed={homeWon} />
        </div>
      </div>
      <div className="w-12 flex justify-end pl-md opacity-0 group-hover:opacity-100 transition-opacity shrink-0">
        <span className="material-symbols-outlined text-outline">chevron_right</span>
      </div>
    </Link>
  );
}
