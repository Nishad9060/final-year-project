import { useEffect, useState } from 'react';
import * as api from '../../api/client';

// docs/03_TRD.md §3 documents `notify_followed_matches` as the one concrete
// example preference key; other keys are generic (key/value strings). We
// render the known key as a toggle and any other keys the backend returns
// generically, rather than inventing additional keys not in the docs.
const KNOWN_PREFS = {
  notify_followed_matches: 'Notify me about followed matches',
};

export default function PreferencesSection() {
  const [preferences, setPreferences] = useState([{ key: 'notify_followed_matches', value: 'true' }]);
  const [pending, setPending] = useState(false);
  const [saved, setSaved] = useState(false);

  useEffect(() => {
    api
      .getPreferences()
      .then((result) => {
        if (result && result.length > 0) setPreferences(result);
      })
      .catch(() => {
        // No live backend yet — keep the default preference shown above.
      });
  }, []);

  async function toggle(key) {
    const next = preferences.map((p) =>
      p.key === key ? { ...p, value: p.value === 'true' ? 'false' : 'true' } : p,
    );
    setPreferences(next);
    setSaved(false);
    setPending(true);
    try {
      await api.updatePreferences(next);
      setSaved(true);
    } catch {
      // Keep the optimistic UI state — this is expected until the
      // preferences endpoint exists on the backend.
    } finally {
      setPending(false);
    }
  }

  const known = preferences.filter((p) => KNOWN_PREFS[p.key]);
  const other = preferences.filter((p) => !KNOWN_PREFS[p.key]);

  return (
    <div className="bg-surface-container rounded-xl shadow-md p-lg flex flex-col gap-md">
      <h2 className="font-headline-md text-headline-md text-on-surface">Preferences</h2>
      <div className="flex flex-col gap-sm">
        {known.map((pref) => (
          <label key={pref.key} className="flex items-center justify-between py-sm border-b border-outline-variant/10 last:border-none cursor-pointer">
            <span className="font-body-md text-body-md text-on-surface">{KNOWN_PREFS[pref.key]}</span>
            <button
              type="button"
              role="switch"
              aria-checked={pref.value === 'true'}
              onClick={() => toggle(pref.key)}
              disabled={pending}
              className={`w-11 h-6 rounded-full transition-colors relative shrink-0 ${
                pref.value === 'true' ? 'bg-primary-container' : 'bg-surface-container-highest'
              }`}
            >
              <span
                className={`absolute top-0.5 w-5 h-5 rounded-full bg-on-primary-container transition-transform ${
                  pref.value === 'true' ? 'translate-x-[22px]' : 'translate-x-0.5'
                }`}
              />
            </button>
          </label>
        ))}
        {other.map((pref) => (
          <div key={pref.key} className="flex items-center justify-between py-sm text-on-surface-variant font-data-tabular text-data-tabular">
            <span>{pref.key}</span>
            <span>{pref.value}</span>
          </div>
        ))}
      </div>
      {saved && <p className="text-positive text-data-tabular font-data-tabular">Preferences saved.</p>}
    </div>
  );
}
