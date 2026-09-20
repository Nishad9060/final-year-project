import { Link } from 'react-router-dom';

// Desktop-only sidebar spotlight on the matches feed, per the Stitch export.
// Uses a token-based gradient instead of the export's remote stock photo so
// the app has no hard dependency on ephemeral third-party image URLs.
export default function MatchSpotlight({ match }) {
  if (!match) return null;
  return (
    <div className="hidden lg:block lg:col-span-4">
      <div className="sticky top-24 bg-surface-container rounded-xl p-lg shadow-md flex flex-col gap-md">
        <div className="flex items-center justify-between mb-sm">
          <h3 className="font-headline-md text-headline-md text-on-surface">Match Spotlight</h3>
          <span className="material-symbols-outlined text-primary animate-pulse">sports_soccer</span>
        </div>
        <div className="w-full aspect-video rounded-lg overflow-hidden relative mb-sm shadow-inner bg-gradient-to-br from-primary-container/40 via-surface-container-high to-surface-container-lowest">
          <div className="absolute inset-0 bg-gradient-to-t from-surface-container via-surface-container/40 to-transparent" />
          <div className="absolute bottom-md left-md right-md flex justify-between items-end">
            <div className="flex flex-col">
              <span className="text-label-caps text-on-surface-variant mb-xs">{match.tournament?.name}</span>
              <span className="font-headline-md text-body-lg text-on-surface">
                {match.home_team?.name} vs {match.away_team?.name}
              </span>
            </div>
          </div>
        </div>
        <Link
          to={`/match/${match.id}`}
          className="w-full py-sm rounded-lg bg-primary hover:bg-primary/90 transition-colors text-on-primary text-center font-bold mt-sm shadow-sm"
        >
          Preview Match
        </Link>
      </div>
    </div>
  );
}
