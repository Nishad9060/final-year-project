const VARIANTS = {
  primary:
    'bg-primary-container text-on-primary-container hover:bg-inverse-primary shadow-sm hover:shadow-md',
  secondary: 'bg-transparent border border-outline-variant text-on-surface hover:bg-surface-container',
  ghost: 'bg-transparent text-on-surface-variant hover:text-on-surface',
};

export default function Button({
  variant = 'primary',
  className = '',
  children,
  type = 'button',
  ...rest
}) {
  return (
    <button
      type={type}
      className={`px-md py-sm rounded-lg font-data-tabular text-data-tabular font-semibold transition-all active:scale-[0.98] disabled:opacity-50 disabled:pointer-events-none ${VARIANTS[variant]} ${className}`}
      {...rest}
    >
      {children}
    </button>
  );
}
