import { useState } from 'react';
import EmptyState from '../shared/EmptyState';
import StandingsTable from './StandingsTable';

const TABS = ['Overview', 'Stats', 'Timeline', 'Standings'];

function StatRow({ label, values, format = (v) => v }) {
  const [home, away] = values;
  const total = home + away || 1;
  return (
    <div className="flex flex-col gap-1">
      <div className="flex justify-between font-data-tabular text-data-tabular">
        <span className="text-primary-fixed">{format(home)}</span>
        <span className="text-on-surface-variant uppercase text-[10px] tracking-widest">{label}</span>
        <span className="text-secondary">{format(away)}</span>
      </div>
      <div className="flex w-full h-2 bg-surface-container-highest rounded-full overflow-hidden shadow-inner">
        <div className="h-full bg-primary-container" style={{ width: `${(home / total) * 100}%` }} />
        <div className="h-full bg-secondary-container" style={{ width: `${(away / total) * 100}%` }} />
      </div>
    </div>
  );
}

const EVENT_ICON = { goal: 'sports_soccer', yellow_card: 'square', red_card: 'square' };

function EventRow({ event, homeTeamName }) {
  const isHome = event.team === 'home';
  return (
    <div className="flex w-full items-center justify-between">
      <div className={`w-[45%] flex flex-col ${isHome ? 'items-end text-right' : 'items-end text-right opacity-0'}`}>
        {isHome && (
          <>
            <span className="font-data-tabular text-data-tabular text-on-surface">{event.player}</span>
            {event.assist && <span className="font-label-caps text-label-caps text-outline">Assist: {event.assist}</span>}
          </>
        )}
      </div>
      <div className="w-8 h-8 rounded-full bg-surface-container-highest shadow-sm flex items-center justify-center z-10 shrink-0">
        <span className="material-symbols-outlined text-[16px] text-on-surface">{EVENT_ICON[event.type] || 'circle'}</span>
      </div>
      <div className={`w-[45%] flex flex-col ${isHome ? 'items-start opacity-0' : 'items-start text-left'}`}>
        {!isHome && (
          <>
            <span className="font-data-tabular text-data-tabular text-on-surface">{event.player}</span>
            {event.assist && <span className="font-label-caps text-label-caps text-outline">Assist: {event.assist}</span>}
          </>
        )}
      </div>
      <span className="font-data-tabular text-data-tabular text-primary-fixed font-bold w-10 text-center shrink-0">
        {event.minute}'
      </span>
    </div>
  );
}

export default function MatchTabs({ match, standings }) {
  const [active, setActive] = useState('Overview');

  return (
    <div className="w-full max-w-max-width mx-auto px-margin-mobile lg:px-margin-desktop mt-xl">
      <nav className="flex items-center gap-lg bg-surface-container-lowest rounded-xl p-2 shadow-inner mb-lg overflow-x-auto" role="tablist">
        {TABS.map((tab) => (
          <button
            key={tab}
            role="tab"
            aria-selected={active === tab}
            onClick={() => setActive(tab)}
            className={`font-data-tabular text-data-tabular px-6 py-2 rounded-lg whitespace-nowrap transition-all ${
              active === tab
                ? 'text-on-primary-container bg-primary-container shadow-sm'
                : 'text-on-surface-variant hover:text-on-surface hover:bg-surface-container'
            }`}
          >
            {tab}
          </button>
        ))}
      </nav>

      <div className="bg-surface-container rounded-xl shadow-md p-md md:p-lg">
        {active === 'Overview' && (
          <div className="flex flex-col gap-md">
            <h3 className="font-headline-md text-headline-md text-on-surface">Overview</h3>
            <p className="font-body-md text-body-md text-on-surface-variant">
              {match.home_team?.name} vs {match.away_team?.name} — {match.tournament?.name}
            </p>
          </div>
        )}

        {active === 'Stats' &&
          (match.stats ? (
            <div className="flex flex-col gap-md">
              <StatRow label="Possession" values={match.stats.possession} format={(v) => `${v}%`} />
              <StatRow label="Shots" values={match.stats.shots} />
              <StatRow label="Expected Goals (xG)" values={match.stats.xg} format={(v) => v.toFixed(2)} />
            </div>
          ) : (
            <EmptyState icon="bar_chart" title="Stats not available" description="Detailed stats aren't available for this match yet." />
          ))}

        {active === 'Timeline' &&
          (match.events && match.events.length > 0 ? (
            <div className="flex flex-col gap-4 relative">
              <div className="absolute left-1/2 top-0 bottom-0 w-px bg-surface-container-highest -translate-x-1/2" />
              {match.events.map((event) => (
                <EventRow key={event.id} event={event} />
              ))}
            </div>
          ) : (
            <EmptyState icon="timeline" title="Timeline not available" description="Match events aren't available for this match yet." />
          ))}

        {active === 'Standings' && <StandingsTable standings={standings} />}
      </div>
    </div>
  );
}
