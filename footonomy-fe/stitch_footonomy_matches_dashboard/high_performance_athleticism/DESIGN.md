---
name: High-Performance Athleticism
colors:
  surface: '#131314'
  surface-dim: '#131314'
  surface-bright: '#3a393a'
  surface-container-lowest: '#0e0e0f'
  surface-container-low: '#1c1b1c'
  surface-container: '#201f20'
  surface-container-high: '#2a2a2b'
  surface-container-highest: '#353436'
  on-surface: '#e5e2e3'
  on-surface-variant: '#ccc3d8'
  inverse-surface: '#e5e2e3'
  inverse-on-surface: '#313031'
  outline: '#958da1'
  outline-variant: '#4a4455'
  surface-tint: '#d2bbff'
  primary: '#d2bbff'
  on-primary: '#3f008e'
  primary-container: '#7c3aed'
  on-primary-container: '#ede0ff'
  inverse-primary: '#732ee4'
  secondary: '#ffb3ad'
  on-secondary: '#68000a'
  secondary-container: '#a40217'
  on-secondary-container: '#ffaea8'
  tertiary: '#4ae176'
  on-tertiary: '#003915'
  tertiary-container: '#007733'
  on-tertiary-container: '#84ff9c'
  error: '#ffb4ab'
  on-error: '#690005'
  error-container: '#93000a'
  on-error-container: '#ffdad6'
  primary-fixed: '#eaddff'
  primary-fixed-dim: '#d2bbff'
  on-primary-fixed: '#25005a'
  on-primary-fixed-variant: '#5a00c6'
  secondary-fixed: '#ffdad7'
  secondary-fixed-dim: '#ffb3ad'
  on-secondary-fixed: '#410004'
  on-secondary-fixed-variant: '#930013'
  tertiary-fixed: '#6bff8f'
  tertiary-fixed-dim: '#4ae176'
  on-tertiary-fixed: '#002109'
  on-tertiary-fixed-variant: '#005321'
  background: '#131314'
  on-background: '#e5e2e3'
  surface-variant: '#353436'
typography:
  score-display:
    fontFamily: Inter
    fontSize: 48px
    fontWeight: '700'
    lineHeight: 48px
    letterSpacing: -0.02em
  score-display-mobile:
    fontFamily: Inter
    fontSize: 36px
    fontWeight: '700'
    lineHeight: 36px
    letterSpacing: -0.02em
  headline-lg:
    fontFamily: Inter
    fontSize: 32px
    fontWeight: '600'
    lineHeight: 40px
    letterSpacing: -0.01em
  headline-md:
    fontFamily: Inter
    fontSize: 24px
    fontWeight: '600'
    lineHeight: 32px
  body-lg:
    fontFamily: Inter
    fontSize: 18px
    fontWeight: '400'
    lineHeight: 28px
  body-md:
    fontFamily: Inter
    fontSize: 16px
    fontWeight: '400'
    lineHeight: 24px
  label-caps:
    fontFamily: Inter
    fontSize: 12px
    fontWeight: '600'
    lineHeight: 16px
    letterSpacing: 0.05em
  data-tabular:
    fontFamily: Inter
    fontSize: 14px
    fontWeight: '500'
    lineHeight: 20px
rounded:
  sm: 0.125rem
  DEFAULT: 0.25rem
  md: 0.375rem
  lg: 0.5rem
  xl: 0.75rem
  full: 9999px
spacing:
  base: 4px
  xs: 4px
  sm: 8px
  md: 16px
  lg: 24px
  xl: 40px
  gutter: 16px
  margin-mobile: 16px
  margin-desktop: 32px
  max-width: 1200px
---

## Brand & Style

This design system is built for a high-intensity football companion app. The aesthetic leans into a sophisticated **Modern Minimalism** with a technical, data-driven edge. It prioritizes clarity and speed of information consumption, reflecting the fast-paced nature of live sports.

The interface utilizes a "dark-first" strategy to reduce eye strain during night matches and to allow the vibrant accent colors (Accent and Live Indicator) to pop with maximum visual priority. The emotional response is one of precision, authority, and athletic energy—avoiding unnecessary decorative elements in favor of a structural, grid-based hierarchy that respects the density of sports data.

## Colors

The palette is anchored by a deep obsidian background to create a "void" where content takes center stage. 

- **Primary Accent (#7C3AED):** Used for interactive states, primary actions, and brand identification. 
- **Live Indicator (#EF4444):** Reserved exclusively for active matches and urgent "breaking" updates. 
- **Positive (#22C55E):** Used for winning streaks, upward trends, and successful outcomes.
- **Surface Strategy:** Layers are built using increasing luminosity. The background is the darkest point, with cards and modals stepping up in value to create a sense of physical proximity to the user.

## Typography

The design system utilizes **Inter** for its exceptional legibility and neutral, systematic tone. 

- **Numerical Priority:** For match scores and clock timings, use `score-display`. These should always utilize tabular figures (`tnum`) to ensure numbers align perfectly in lists.
- **Hierarchy:** Use `label-caps` for league headers and metadata categories to create a clear structural break from content.
- **Contrast:** High weight contrast (Bold vs Regular) is preferred over color variance to denote importance, ensuring the UI remains readable in high-glare environments.

## Layout & Spacing

This design system uses a **4px baseline grid** to ensure mathematical harmony. 

- **Grid Model:** A 12-column fluid grid for desktop, transitioning to a 1-column stack for mobile.
- **Match Cards:** Use `md` (16px) padding for standard cards. Match lists should have `xs` (4px) vertical spacing between cards within the same league group to emphasize their connection.
- **Grouping:** Use `xl` (40px) spacing to separate different leagues or major content sections.
- **Alignment:** Content should be strictly aligned to the left; center alignment is reserved only for match scoreboards and empty state illustrations.

## Elevation & Depth

Depth is communicated through **Tonal Layers** and **Low-contrast Outlines** rather than traditional shadows.

- **Level 0 (Background):** #0A0A0B - The canvas.
- **Level 1 (Cards/Containers):** #131316 - Use a 1px solid border (#27272A) to define edges. No shadow.
- **Level 2 (Modals/Overlays):** #1C1C21 - Elevated elements receive a subtle, 1px border. A very soft, 20% opacity black shadow (0px 8px 24px) may be used to provide separation from Level 1 surfaces.
- **Interaction:** On hover, cards may transition their border color to #3F3F46 to indicate interactivity without moving the element.

## Shapes

The design system uses **Soft (Level 1)** roundedness to maintain a clean, professional appearance while avoiding the harshness of sharp corners.

- **Small Components:** Checkboxes, tags, and small buttons use 0.25rem (4px).
- **Standard Cards:** Match cards and containers use 0.5rem (8px).
- **Large Elements:** Modals and prominent hero sections use 0.75rem (12px).
- **Special Case:** The "Live" dot indicator and certain team crest avatars may use a full circle (999px) for immediate recognition.

## Components

### Match Cards
The core unit of the design system. Features a #131316 background with a 1px border. Team names are `body-md`, scores are `headline-md`. The "Live" state adds a #EF4444 pulsing dot next to the match clock.

### Buttons
- **Primary:** Solid #7C3AED with #FFFFFF text.
- **Secondary:** Transparent with #27272A border.
- **Ghost:** No border, #A1A1AA text, transitions to #FFFFFF on hover.

### League Headers
Sticky headers using `label-caps` typography. Background is #0A0A0B with 80% opacity and a backdrop blur of 12px to maintain context during scrolling.

### Form Inputs
Minimalist style. Background #0A0A0B, 1px border #27272A. Focus state changes border to #7C3AED. Labels use `text-secondary`.

### Chips / Filters
Used for toggling between "All Matches," "Live," and "Finished." Inactive: #131316 background. Active: #7C3AED background with white text.

### Stat Bars
Horizontal progress bars for possession or shots. Use a neutral #27272A for the background track and #7C3AED or #EF4444 for the fill.