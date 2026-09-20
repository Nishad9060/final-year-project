import { useState } from 'react';
import { NavLink, useNavigate } from 'react-router-dom';
import { useAuth } from '../../context/AuthContext';

const NAV_LINKS = [
  { to: '/', label: 'Matches', end: true },
  { to: '/tournaments', label: 'Tournaments' },
  { to: '/search', label: 'Search' },
];

const linkClass = ({ isActive }) =>
  `font-body-md text-body-md transition-colors ${
    isActive ? 'text-primary font-semibold' : 'text-on-surface-variant hover:text-on-surface'
  }`;

// Present on every page per docs/02_IA_Sitemap.md §2. Rendering this reads
// isAuthenticated (safe — it never blocks or gates page content, it only
// swaps Login vs Avatar). Following/Settings live inside the avatar
// dropdown per the IA doc's nav rules, not as top-level links — two of the
// nine Stitch exports (Following, Settings) render them inline in the
// header instead; the IA doc is the structural spec so it wins here per its
// own instruction to reconcile deliberately when screens drift from it.
export default function NavBar() {
  const { isAuthenticated, logout } = useAuth();
  const [menuOpen, setMenuOpen] = useState(false);
  const [mobileOpen, setMobileOpen] = useState(false);
  const navigate = useNavigate();

  async function handleLogout() {
    setMenuOpen(false);
    setMobileOpen(false);
    await logout();
    navigate('/');
  }

  return (
    <header className="fixed top-0 w-full z-50 bg-surface/80 backdrop-blur-xl border-b border-outline-variant/30">
      <div className="h-16 w-full max-w-max-width mx-auto px-margin-mobile lg:px-margin-desktop flex items-center justify-between">
        <div className="flex items-center gap-xl">
          <NavLink to="/" className="font-headline-md text-headline-md tracking-tighter text-on-surface select-none">
            FOOTONOMY
          </NavLink>
          <nav className="hidden lg:flex items-center gap-lg">
            {NAV_LINKS.map((link) => (
              <NavLink key={link.to} to={link.to} end={link.end} className={linkClass}>
                {link.label}
              </NavLink>
            ))}
          </nav>
        </div>

        <div className="flex items-center gap-md">
          {isAuthenticated ? (
            <div className="relative hidden lg:block">
              <button
                type="button"
                onClick={() => setMenuOpen((v) => !v)}
                className="w-8 h-8 rounded-full bg-primary flex items-center justify-center hover:opacity-90 transition-opacity"
                aria-haspopup="true"
                aria-expanded={menuOpen}
              >
                <span className="material-symbols-outlined text-on-primary text-[18px]">person</span>
              </button>
              {menuOpen && (
                <div className="absolute right-0 mt-sm w-48 bg-surface-container rounded-lg shadow-2xl border border-outline-variant/30 py-xs overflow-hidden">
                  <NavLink
                    to="/following"
                    onClick={() => setMenuOpen(false)}
                    className="block px-md py-sm text-body-md text-on-surface hover:bg-surface-container-high"
                  >
                    Following
                  </NavLink>
                  <NavLink
                    to="/settings"
                    onClick={() => setMenuOpen(false)}
                    className="block px-md py-sm text-body-md text-on-surface hover:bg-surface-container-high"
                  >
                    Settings
                  </NavLink>
                  <button
                    type="button"
                    onClick={handleLogout}
                    className="w-full text-left px-md py-sm text-body-md text-on-surface hover:bg-surface-container-high"
                  >
                    Logout
                  </button>
                </div>
              )}
            </div>
          ) : (
            <NavLink
              to="/login"
              className="hidden lg:block font-label-caps text-label-caps text-on-surface-variant hover:text-primary transition-colors uppercase tracking-widest"
            >
              Log In
            </NavLink>
          )}

          <button
            type="button"
            onClick={() => setMobileOpen((v) => !v)}
            className="lg:hidden flex items-center justify-center p-xs text-on-surface-variant hover:text-on-surface"
            aria-label="Toggle menu"
            aria-expanded={mobileOpen}
          >
            <span className="material-symbols-outlined">{mobileOpen ? 'close' : 'menu'}</span>
          </button>
        </div>
      </div>

      {mobileOpen && (
        <nav className="lg:hidden border-t border-outline-variant/30 bg-surface px-margin-mobile py-md flex flex-col gap-sm">
          {NAV_LINKS.map((link) => (
            <NavLink
              key={link.to}
              to={link.to}
              end={link.end}
              onClick={() => setMobileOpen(false)}
              className={({ isActive }) =>
                `py-sm text-body-lg ${isActive ? 'text-primary font-semibold' : 'text-on-surface-variant'}`
              }
            >
              {link.label}
            </NavLink>
          ))}
          {isAuthenticated ? (
            <>
              <NavLink to="/following" onClick={() => setMobileOpen(false)} className="py-sm text-body-lg text-on-surface-variant">
                Following
              </NavLink>
              <NavLink to="/settings" onClick={() => setMobileOpen(false)} className="py-sm text-body-lg text-on-surface-variant">
                Settings
              </NavLink>
              <button type="button" onClick={handleLogout} className="py-sm text-body-lg text-left text-on-surface-variant">
                Logout
              </button>
            </>
          ) : (
            <NavLink to="/login" onClick={() => setMobileOpen(false)} className="py-sm text-body-lg text-primary font-semibold">
              Log In
            </NavLink>
          )}
        </nav>
      )}
    </header>
  );
}
