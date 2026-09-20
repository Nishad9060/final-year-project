function minutesAgo(isoString) {
  return Math.max(0, Math.round((Date.now() - new Date(isoString).getTime()) / 60000));
}

// Per docs/03_TRD.md §5: on sync failure/rate-limit, the backend serves
// last-cached data with a last_synced_at timestamp — the frontend uses it to
// show "data may be delayed" rather than silently implying real-time scores
// (C-1 in the SRS: this product is near-live, not sub-second).
export default function DataStalenessBanner({ lastSyncedAt, thresholdMinutes = 10 }) {
  if (!lastSyncedAt) return null;
  const mins = minutesAgo(lastSyncedAt);
  if (mins < thresholdMinutes) return null;

  return (
    <div className="flex items-center gap-xs px-md py-sm rounded-lg bg-surface-container text-on-surface-variant text-data-tabular font-data-tabular mb-md">
      <span className="material-symbols-outlined text-[16px]">schedule</span>
      Data may be delayed — last synced {mins} min ago
    </div>
  );
}
