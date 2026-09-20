export default function EmptyState({ icon = 'inbox', title, description, action }) {
  return (
    <div className="flex flex-col items-center justify-center text-center gap-sm py-xl px-md">
      <span className="material-symbols-outlined text-outline-variant text-[40px]">{icon}</span>
      <h3 className="font-headline-md text-headline-md text-on-surface">{title}</h3>
      {description && (
        <p className="font-body-md text-body-md text-on-surface-variant max-w-md">{description}</p>
      )}
      {action && <div className="mt-sm">{action}</div>}
    </div>
  );
}
