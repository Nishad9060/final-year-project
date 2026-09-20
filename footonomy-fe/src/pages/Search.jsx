import { useEffect, useState } from 'react';
import { useSearchParams } from 'react-router-dom';
import * as api from '../api/client';
import { searchAll } from '../mocks/data';
import SearchBar from '../components/search/SearchBar';
import SearchResults from '../components/search/SearchResults';

// Public route — /search, no auth check. Per docs/01_SRS.md FR-9/FR-11.
export default function Search() {
  const [params, setParams] = useSearchParams();
  const initialQuery = params.get('q') || '';
  const [query, setQuery] = useState(initialQuery);
  const [results, setResults] = useState({ teams: [], tournaments: [], players: [] });

  useEffect(() => {
    const trimmed = query.trim();
    setParams(trimmed ? { q: trimmed } : {}, { replace: true });

    if (!trimmed) {
      setResults({ teams: [], tournaments: [], players: [] });
      return;
    }

    const timeout = setTimeout(() => {
      api
        .search({ q: trimmed })
        .then(setResults)
        .catch(() => setResults(searchAll(trimmed)));
      // eslint-disable-next-line react-hooks/exhaustive-deps
    }, 250);
    return () => clearTimeout(timeout);
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [query]);

  return (
    <div className="max-w-max-width mx-auto w-full px-margin-mobile lg:px-margin-desktop py-lg flex flex-col gap-xl">
      <SearchBar value={query} onChange={setQuery} autoFocus />
      <SearchResults results={results} query={query.trim()} />
    </div>
  );
}
