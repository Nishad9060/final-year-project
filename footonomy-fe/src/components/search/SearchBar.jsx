export default function SearchBar({ value, onChange, autoFocus = false }) {
  return (
    <div className="relative w-full">
      <span className="material-symbols-outlined absolute left-md top-1/2 -translate-y-1/2 text-on-surface-variant">search</span>
      <input
        type="search"
        value={value}
        onChange={(e) => onChange(e.target.value)}
        autoFocus={autoFocus}
        placeholder="Search teams, tournaments, players..."
        className="w-full bg-surface-container-high text-on-surface font-body-md text-body-lg rounded-lg py-md pl-[48px] pr-md outline-none focus:ring-2 focus:ring-primary transition-all placeholder:text-on-surface-variant/50"
      />
    </div>
  );
}
