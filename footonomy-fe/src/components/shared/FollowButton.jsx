import { useState } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import { useAuth } from '../../context/AuthContext';
import { useFollowing } from '../../context/FollowingContext';

// Appears on team/tournament context (Search results, Tournament Detail,
// Match Detail) — same visual state logic everywhere per
// docs/02_IA_Sitemap.md §3.
export default function FollowButton({ entityType, entityId, className = '' }) {
  const { isAuthenticated } = useAuth();
  const { isFollowing, follow, unfollow } = useFollowing();
  const navigate = useNavigate();
  const location = useLocation();
  const [pending, setPending] = useState(false);

  const following = isAuthenticated && isFollowing(entityType, entityId);

  async function handleClick() {
    if (!isAuthenticated) {
      navigate('/login', { state: { from: `${location.pathname}${location.search}` } });
      return;
    }
    setPending(true);
    try {
      if (following) {
        await unfollow(entityType, entityId);
      } else {
        await follow(entityType, entityId);
      }
    } finally {
      setPending(false);
    }
  }

  return (
    <button
      type="button"
      onClick={handleClick}
      disabled={pending}
      aria-pressed={following}
      className={`flex items-center gap-xs px-md py-xs rounded-full font-data-tabular text-data-tabular font-semibold transition-all active:scale-[0.98] disabled:opacity-60 ${
        following
          ? 'bg-surface-container-high text-on-surface border border-outline-variant'
          : 'bg-primary-container text-on-primary-container hover:bg-inverse-primary'
      } ${className}`}
    >
      <span className="material-symbols-outlined text-[16px]">
        {following ? 'check' : 'add'}
      </span>
      {following ? 'Following' : 'Follow'}
    </button>
  );
}
