import ProfileSection from '../components/settings/ProfileSection';
import PreferencesSection from '../components/settings/PreferencesSection';

// Auth-gated route — /settings. Rendered only after ProtectedRoute confirms
// isAuthenticated, per docs/01_SRS.md FR-19/FR-20.
export default function Settings() {
  return (
    <div className="max-w-max-width mx-auto w-full px-margin-mobile lg:px-margin-desktop py-lg flex flex-col gap-lg">
      <h1 className="font-headline-lg text-headline-lg text-on-surface">Settings</h1>
      <ProfileSection />
      <PreferencesSection />
    </div>
  );
}
