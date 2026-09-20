const QUICK_DATES = [
  { key: 'yesterday', label: 'Yesterday', offsetDays: -1 },
  { key: 'today', label: 'Today', offsetDays: 0 },
  { key: 'tomorrow', label: 'Tomorrow', offsetDays: 1 },
];

const LEAGUES = [
  { id: '', label: 'All Leagues' },
  { id: 'ext-pl', label: 'Premier League' },
  { id: 'ext-ll', label: 'La Liga' },
  { id: 'ext-bl', label: 'Bundesliga' },
  { id: 'ext-sa', label: 'Serie A' },
  { id: 'ext-l1', label: 'Ligue 1' },
  { id: 'ext-ucl', label: 'Champions League' },
];

export default function MatchFilterBar({ activeQuickDate, onQuickDateChange, league, onLeagueChange }) {
  return (
    <div className="flex flex-col sm:flex-row sm:items-center sm:justify-between gap-sm mb-xl">
      <div className="flex gap-sm overflow-x-auto pb-xs">
        {QUICK_DATES.map((d) => (
          <button
            key={d.key}
            type="button"
            onClick={() => onQuickDateChange(d.key)}
            className={`px-md py-sm rounded-full transition-colors text-data-tabular whitespace-nowrap ${
              activeQuickDate === d.key
                ? 'bg-primary-container text-on-primary-container font-bold shadow-sm'
                : 'bg-surface-container hover:bg-surface-container-high text-on-surface'
            }`}
          >
            {d.label}
          </button>
        ))}
      </div>
      <select
        value={league}
        onChange={(e) => onLeagueChange(e.target.value)}
        className="px-md py-sm rounded-full bg-surface-container hover:bg-surface-container-high transition-colors text-on-surface text-data-tabular outline-none border border-transparent focus:border-primary"
      >
        {LEAGUES.map((l) => (
          <option key={l.id} value={l.id}>
            {l.label}
          </option>
        ))}
      </select>
    </div>
  );
}
