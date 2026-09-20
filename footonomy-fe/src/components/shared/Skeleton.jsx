export default function Skeleton({ className = '' }) {
  return <div className={`animate-pulse bg-surface-container-high rounded-lg ${className}`} />;
}

export function MatchCardSkeleton() {
  return (
    <div className="bg-surface-container rounded-xl p-md flex items-center gap-md">
      <Skeleton className="w-16 h-8 shrink-0" />
      <div className="flex-1 flex flex-col gap-sm">
        <Skeleton className="h-6 w-2/3" />
        <Skeleton className="h-6 w-1/2" />
      </div>
    </div>
  );
}
