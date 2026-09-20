const SIZES = {
  sm: 'w-6 h-6 text-[10px]',
  md: 'w-10 h-10 text-body-md',
  lg: 'w-20 h-20 md:w-28 md:h-28 text-headline-md',
};

function initials(name = '') {
  return name
    .split(' ')
    .map((word) => word[0])
    .slice(0, 2)
    .join('')
    .toUpperCase();
}

// Used on Search results, Standings table, Following feed — same crest
// component everywhere per docs/02_IA_Sitemap.md §3.
export default function TeamBadge({ team, size = 'md', showName = false }) {
  if (!team) return null;
  return (
    <div className="flex items-center gap-sm">
      <div
        className={`${SIZES[size]} rounded-full bg-surface-container-highest shadow-sm flex items-center justify-center overflow-hidden shrink-0`}
      >
        {team.logo_url ? (
          <img src={team.logo_url} alt={team.name} className="w-full h-full object-contain" />
        ) : (
          <span className="font-headline-md text-on-surface-variant font-bold leading-none">
            {initials(team.short_name || team.name)}
          </span>
        )}
      </div>
      {showName && <span className="font-body-md text-body-lg text-on-surface">{team.name}</span>}
    </div>
  );
}
