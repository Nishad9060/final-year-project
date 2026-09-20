import { useEffect, useState } from 'react';

// Shared fetch pattern for public pages: render `fallback` (mock/cached
// data) immediately, then try the live endpoint in the background. On
// failure we keep showing whatever we already have rather than blanking
// the page, per docs/03_TRD.md §5 ("serve last-cached data... do not show
// a blank/broken state"). Callers decide whether `error` is worth surfacing
// (e.g. only when `data` is still empty).
export function useApiData(fetcher, fallback, deps = []) {
  const [data, setData] = useState(fallback);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    let cancelled = false;
    setLoading(true);
    fetcher()
      .then((result) => {
        if (!cancelled) {
          setData(result);
          setError(null);
        }
      })
      .catch((err) => {
        if (!cancelled) setError(err);
      })
      .finally(() => {
        if (!cancelled) setLoading(false);
      });
    return () => {
      cancelled = true;
    };
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, deps);

  return { data, loading, error };
}
