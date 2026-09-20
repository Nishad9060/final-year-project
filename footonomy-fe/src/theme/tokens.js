// Design tokens — source of truth for both tailwind.config.js and any JS-side
// color access (e.g. inline SVG fills). Extracted from the Stitch export
// ("High-Performance Athleticism") in stitch_footonomy_matches_dashboard/,
// reconciled with the palette frozen in docs/CLAUDE.md.
//
// docs/CLAUDE.md's flat palette maps onto this M3-style token set as:
//   Background #0A0A0B      -> close to surface-container-lowest (#0e0e0f)
//   Surface #131316         -> surface / background (#131314)
//   Elevated #1C1C21        -> surface-container-high (#2a2a2b)
//   Border #27272A          -> outline-variant (#4a4455)
//   Text primary #FFFFFF    -> on-surface (#e5e2e3, near-white)
//   Text secondary #A1A1AA  -> on-surface-variant (#ccc3d8)
//   Text tertiary #71717A   -> outline (#958da1)
//   Accent #7C3AED          -> primary-container (#7c3aed) — exact match
//   Live indicator #EF4444  -> error (kept as literal #EF4444, see note below)
//   Positive #22C55E        -> tertiary-container region (kept as literal #22C55E)
//
// Live indicator and Positive are pinned to the literal hex values from
// docs/CLAUDE.md rather than the Stitch export's error/tertiary values,
// since docs/CLAUDE.md explicitly reserves those two colors for single
// meanings ("never reused") and calls them out as hard constraints.

export const colors = {
  // Material-3-style surface/content tokens (from Stitch export)
  background: '#131314',
  surface: '#131314',
  'surface-dim': '#131314',
  'surface-bright': '#3a393a',
  'surface-variant': '#353436',
  'surface-container-lowest': '#0e0e0f',
  'surface-container-low': '#1c1b1c',
  'surface-container': '#201f20',
  'surface-container-high': '#2a2a2b',
  'surface-container-highest': '#353436',

  'on-background': '#e5e2e3',
  'on-surface': '#e5e2e3',
  'on-surface-variant': '#ccc3d8',
  'inverse-surface': '#e5e2e3',
  'inverse-on-surface': '#313031',

  outline: '#958da1',
  'outline-variant': '#4a4455',
  'surface-tint': '#d2bbff',

  primary: '#d2bbff',
  'on-primary': '#3f008e',
  'primary-container': '#7c3aed',
  'on-primary-container': '#ede0ff',
  'inverse-primary': '#732ee4',
  'primary-fixed': '#eaddff',
  'primary-fixed-dim': '#d2bbff',
  'on-primary-fixed': '#25005a',
  'on-primary-fixed-variant': '#5a00c6',

  secondary: '#ffb3ad',
  'on-secondary': '#68000a',
  'secondary-container': '#a40217',
  'on-secondary-container': '#ffaea8',
  'secondary-fixed': '#ffdad7',
  'secondary-fixed-dim': '#ffb3ad',
  'on-secondary-fixed': '#410004',
  'on-secondary-fixed-variant': '#930013',

  tertiary: '#4ae176',
  'on-tertiary': '#003915',
  'tertiary-container': '#007733',
  'on-tertiary-container': '#84ff9c',
  'tertiary-fixed': '#6bff8f',
  'tertiary-fixed-dim': '#4ae176',
  'on-tertiary-fixed': '#002109',
  'on-tertiary-fixed-variant': '#005321',

  'error-m3': '#ffb4ab',
  'on-error': '#690005',
  'error-container': '#93000a',
  'on-error-container': '#ffdad6',

  // Hard-constrained literal colors from docs/CLAUDE.md — single reserved
  // meaning each, do not derive from the M3 scale above.
  live: '#EF4444',
  positive: '#22C55E',
  error: '#EF4444',
};

export const fontFamily = {
  'score-display': ['Inter', 'sans-serif'],
  'score-display-mobile': ['Inter', 'sans-serif'],
  'headline-lg': ['Inter', 'sans-serif'],
  'headline-md': ['Inter', 'sans-serif'],
  'body-lg': ['Inter', 'sans-serif'],
  'body-md': ['Inter', 'sans-serif'],
  'label-caps': ['Inter', 'sans-serif'],
  'data-tabular': ['Inter', 'sans-serif'],
};

export const fontSize = {
  'score-display': ['48px', { lineHeight: '48px', letterSpacing: '-0.02em', fontWeight: '700' }],
  'score-display-mobile': ['36px', { lineHeight: '36px', letterSpacing: '-0.02em', fontWeight: '700' }],
  'headline-lg': ['32px', { lineHeight: '40px', letterSpacing: '-0.01em', fontWeight: '600' }],
  'headline-md': ['24px', { lineHeight: '32px', fontWeight: '600' }],
  'body-lg': ['18px', { lineHeight: '28px', fontWeight: '400' }],
  'body-md': ['16px', { lineHeight: '24px', fontWeight: '400' }],
  'label-caps': ['12px', { lineHeight: '16px', letterSpacing: '0.05em', fontWeight: '600' }],
  'data-tabular': ['14px', { lineHeight: '20px', fontWeight: '500' }],
};

export const spacing = {
  base: '4px',
  xs: '4px',
  sm: '8px',
  md: '16px',
  lg: '24px',
  xl: '40px',
  gutter: '16px',
  'margin-mobile': '16px',
  'margin-desktop': '32px',
  'max-width': '1200px',
};

// Sourced from DESIGN.md's `rounded` scale, NOT the inline tailwind.config
// embedded in each screen's code.html. Every code.html carries an identical
// borderRadius.full of 0.75rem (12px), which cannot produce a circle on the
// avatars/live-dot it's used on — DESIGN.md's own component notes call for
// a true circle there ("full circle (999px) for immediate recognition"),
// so this is treated as a copy-paste bug in the per-screen export, not intent.
export const borderRadius = {
  sm: '0.125rem',
  DEFAULT: '0.25rem',
  md: '0.375rem',
  lg: '0.5rem',
  xl: '0.75rem',
  full: '9999px',
};
