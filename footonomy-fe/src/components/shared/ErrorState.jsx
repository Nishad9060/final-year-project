import Button from './Button';

// Match/Tournament data temporarily unavailable (API rate-limited or down) —
// this WILL happen given C-2 in the SRS (10 req/min on football-data.org),
// per docs/02_IA_Sitemap.md §5. Never let this collapse to a blank screen.
export default function ErrorState({
  title = 'Data temporarily unavailable',
  description = "We couldn't reach live match data right now. This is usually temporary — try again shortly.",
  onRetry,
}) {
  return (
    <div className="flex flex-col items-center justify-center text-center gap-sm py-xl px-md">
      <span className="material-symbols-outlined text-live text-[40px]">cloud_off</span>
      <h3 className="font-headline-md text-headline-md text-on-surface">{title}</h3>
      <p className="font-body-md text-body-md text-on-surface-variant max-w-md">{description}</p>
      {onRetry && (
        <Button variant="secondary" className="mt-sm" onClick={onRetry}>
          Try again
        </Button>
      )}
    </div>
  );
}
