import { useState } from 'react';
import { useAuth } from '../../context/AuthContext';

// docs/07_Data_Dictionary.md's User entity only has id/email/created_at as
// user-facing fields (password_hash isn't editable through this form) — so
// "profile" for V1 is just email plus a read-only member-since date.
export default function ProfileSection() {
  const { user, updateProfile } = useAuth();
  const [email, setEmail] = useState(user?.email || '');
  const [pending, setPending] = useState(false);
  const [saved, setSaved] = useState(false);
  const [error, setError] = useState(null);

  async function handleSubmit(e) {
    e.preventDefault();
    setPending(true);
    setError(null);
    setSaved(false);
    try {
      await updateProfile({ email });
      setSaved(true);
    } catch (err) {
      setError(err.message || 'Could not update profile.');
    } finally {
      setPending(false);
    }
  }

  return (
    <div className="bg-surface-container rounded-xl shadow-md p-lg flex flex-col gap-md">
      <h2 className="font-headline-md text-headline-md text-on-surface">Profile</h2>
      <form className="flex flex-col gap-md" onSubmit={handleSubmit}>
        <div className="flex flex-col gap-xs">
          <label className="font-label-caps text-label-caps text-on-surface-variant uppercase tracking-wider" htmlFor="profile-email">
            Email Address
          </label>
          <input
            id="profile-email"
            type="email"
            required
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            className="w-full h-12 bg-surface px-md rounded-lg border border-outline-variant/50 text-on-surface font-body-md focus:outline-none focus:border-primary focus:ring-1 focus:ring-primary transition-all"
          />
        </div>
        {user?.created_at && (
          <p className="font-label-caps text-label-caps text-on-surface-variant">
            Member since {new Date(user.created_at).toLocaleDateString()}
          </p>
        )}
        {error && <p className="text-live text-data-tabular font-data-tabular">{error}</p>}
        {saved && <p className="text-positive text-data-tabular font-data-tabular">Profile updated.</p>}
        <button
          type="submit"
          disabled={pending}
          className="self-start px-lg py-sm bg-primary-container text-on-primary-container font-data-tabular text-data-tabular font-bold rounded-lg hover:bg-inverse-primary transition-all disabled:opacity-70"
        >
          {pending ? 'Saving...' : 'Save Changes'}
        </button>
      </form>
    </div>
  );
}
