import { Link } from 'react-router-dom';
import { useFollowing } from '../../context/FollowingContext';

// docs/07_Data_Dictionary.md's Following row only guarantees entity_type +
// entity_id; nested team/tournament detail (name, logo) is a display
// convenience assumed here the same way match/standing mocks assume it —
// falls back to a bare id if the backend doesn't nest it.
export default function FollowedEntityRow({ item }) {
  const { unfollow } = useFollowing();
  const detail = item.team || item.tournament;
  const label = detail?.name || item.entity_id;
  const href = item.entity_type === 'team' ? '#' : `/tournament/${item.entity_id}`;

  return (
    <div className="flex items-center justify-between bg-surface-container rounded-xl p-md shadow-sm">
      <div className="flex items-center gap-sm min-w-0">
        <div className="w-10 h-10 rounded-full bg-surface-container-highest flex items-center justify-center shrink-0">
          <span className="material-symbols-outlined text-on-surface-variant text-[18px]">
            {item.entity_type === 'team' ? 'shield' : 'trophy'}
          </span>
        </div>
        {item.entity_type === 'tournament' ? (
          <Link to={href} className="font-body-md text-body-lg text-on-surface hover:text-primary transition-colors truncate">
            {label}
          </Link>
        ) : (
          <span className="font-body-md text-body-lg text-on-surface truncate">{label}</span>
        )}
      </div>
      <button
        type="button"
        onClick={() => unfollow(item.entity_type, item.entity_id)}
        className="px-md py-xs rounded-full border border-outline-variant text-on-surface-variant hover:text-on-surface font-data-tabular text-data-tabular transition-colors shrink-0"
      >
        Unfollow
      </button>
    </div>
  );
}
