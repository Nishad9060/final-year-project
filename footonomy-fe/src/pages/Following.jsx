import { Link } from 'react-router-dom';
import * as api from '../api/client';
import { useApiData } from '../hooks/useApiData';
import { mockMatches } from '../mocks/data';
import { useFollowing } from '../context/FollowingContext';
import FollowedEntityRow from '../components/following/FollowedEntityRow';
import FollowingFeed from '../components/following/FollowingFeed';
import EmptyState from '../components/shared/EmptyState';

// Auth-gated route — /following. Only reachable once ProtectedRoute has
// confirmed isAuthenticated. Per docs/01_SRS.md FR-15/FR-16/FR-17.
export default function Following() {
  const { items, loading } = useFollowing();
  const { data: matches } = useApiData(() => api.getMatches({}), mockMatches, []);

  if (!loading && items.length === 0) {
    return (
      <div className="max-w-max-width mx-auto w-full px-margin-mobile lg:px-margin-desktop py-lg">
        <EmptyState
          icon="favorite"
          title="No follows yet"
          description="Follow teams and tournaments to see their matches here."
          action={
            <Link
              to="/tournaments"
              className="inline-block px-md py-sm rounded-lg font-data-tabular text-data-tabular font-semibold bg-primary-container text-on-primary-container hover:bg-inverse-primary transition-all"
            >
              Browse Tournaments
            </Link>
          }
        />
      </div>
    );
  }

  return (
    <div className="max-w-max-width mx-auto w-full px-margin-mobile lg:px-margin-desktop py-lg flex flex-col gap-xl">
      <div>
        <h1 className="font-headline-lg text-headline-lg text-on-surface mb-md">Your Follows</h1>
        <div className="grid grid-cols-1 sm:grid-cols-2 gap-sm">
          {items.map((item) => (
            <FollowedEntityRow key={item.id} item={item} />
          ))}
        </div>
      </div>
      <div>
        <h2 className="font-headline-md text-headline-md text-on-surface mb-md">Feed</h2>
        <FollowingFeed followingItems={items} matches={matches} />
      </div>
    </div>
  );
}
