import TeamBadge from '../shared/TeamBadge';
import EmptyState from '../shared/EmptyState';

export default function StandingsTable({ standings }) {
  if (!standings || standings.length === 0) {
    return <EmptyState icon="leaderboard" title="No standings yet" description="Check back once matches have been played." />;
  }

  return (
    <div className="bg-surface-container rounded-xl shadow-md overflow-hidden">
      <div className="overflow-x-auto">
        <table className="w-full text-left border-collapse">
          <thead>
            <tr className="text-label-caps font-label-caps text-on-surface-variant uppercase tracking-wider border-b border-outline-variant/20">
              <th className="px-md py-sm w-12">#</th>
              <th className="px-md py-sm">Team</th>
              <th className="px-sm py-sm text-center">P</th>
              <th className="px-sm py-sm text-center">W</th>
              <th className="px-sm py-sm text-center">D</th>
              <th className="px-sm py-sm text-center">L</th>
              <th className="px-md py-sm text-center">Pts</th>
            </tr>
          </thead>
          <tbody>
            {standings.map((row) => (
              <tr key={row.id} className="border-b border-outline-variant/10 last:border-none hover:bg-surface-container-high transition-colors">
                <td className="px-md py-sm font-data-tabular text-data-tabular text-on-surface-variant">{row.position}</td>
                <td className="px-md py-sm">
                  <TeamBadge team={row.team} size="sm" showName />
                </td>
                <td className="px-sm py-sm text-center font-data-tabular text-data-tabular text-on-surface-variant">{row.played}</td>
                <td className="px-sm py-sm text-center font-data-tabular text-data-tabular text-on-surface-variant">{row.won}</td>
                <td className="px-sm py-sm text-center font-data-tabular text-data-tabular text-on-surface-variant">{row.drawn}</td>
                <td className="px-sm py-sm text-center font-data-tabular text-data-tabular text-on-surface-variant">{row.lost}</td>
                <td className="px-md py-sm text-center font-data-tabular text-data-tabular text-on-surface font-bold">{row.points}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}
