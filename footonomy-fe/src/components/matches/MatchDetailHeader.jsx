import TeamBadge from '../shared/TeamBadge';

export default function MatchDetailHeader({ match }) {
  const finished = match.status === 'finished';
  const live = match.status === 'live';

  return (
    <div className="relative w-full rounded-xl bg-surface-container shadow-2xl overflow-hidden mt-6 mx-auto max-w-max-width">
      <div className="relative z-10 flex flex-col items-center pt-lg pb-md px-margin-mobile">
        <div className="flex items-center gap-sm bg-surface/80 backdrop-blur-md rounded-full px-4 py-1 shadow-md mb-lg">
          <span className="material-symbols-outlined text-outline text-[16px]">trophy</span>
          <span className="font-label-caps text-label-caps text-on-surface uppercase tracking-widest opacity-80">
            {match.tournament?.name}
          </span>
        </div>

        <div className="flex w-full max-w-3xl justify-between items-center gap-md md:gap-xl">
          <div className="flex flex-col items-center flex-1 gap-md">
            <TeamBadge team={match.home_team} size="lg" />
            <h2 className="font-headline-md text-headline-md md:font-headline-lg md:text-headline-lg text-on-surface text-center">
              {match.home_team?.name}
            </h2>
            <span className="font-label-caps text-label-caps text-on-surface-variant uppercase bg-surface-container-lowest px-2 py-1 rounded-sm shadow-sm">
              Home
            </span>
          </div>

          <div className="flex flex-col items-center justify-center gap-xs">
            {live && (
              <div className="flex items-center gap-2 bg-error-container/20 rounded-full px-3 py-1 shadow-sm mb-2">
                <div className="w-2 h-2 rounded-full bg-live animate-pulse" />
                <span className="font-data-tabular text-data-tabular text-live font-bold">
                  {match.minute ? `${match.minute}'` : 'LIVE'}
                </span>
              </div>
            )}
            {finished && (
              <span className="font-label-caps text-label-caps text-outline uppercase tracking-widest mb-2">Full Time</span>
            )}
            {!live && !finished && (
              <span className="font-label-caps text-label-caps text-outline uppercase tracking-widest mb-2">Upcoming</span>
            )}
            <div className="flex items-center gap-md">
              <span className="font-score-display text-score-display md:text-[72px] md:leading-[72px] text-on-surface">
                {match.home_score ?? '-'}
              </span>
              <span className="font-headline-md text-headline-md text-outline-variant">-</span>
              <span className="font-score-display text-score-display md:text-[72px] md:leading-[72px] text-on-surface opacity-80">
                {match.away_score ?? '-'}
              </span>
            </div>
          </div>

          <div className="flex flex-col items-center flex-1 gap-md">
            <TeamBadge team={match.away_team} size="lg" />
            <h2 className="font-headline-md text-headline-md md:font-headline-lg md:text-headline-lg text-on-surface text-center">
              {match.away_team?.name}
            </h2>
            <span className="font-label-caps text-label-caps text-on-surface-variant uppercase bg-surface-container-lowest px-2 py-1 rounded-sm shadow-sm">
              Away
            </span>
          </div>
        </div>
      </div>
    </div>
  );
}
