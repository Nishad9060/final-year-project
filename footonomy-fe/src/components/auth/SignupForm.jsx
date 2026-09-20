import { useState } from 'react';
import { useAuth } from '../../context/AuthContext';

export default function SignupForm({ onSuccess }) {
  const { signup } = useAuth();
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [pending, setPending] = useState(false);
  const [error, setError] = useState(null);

  async function handleSubmit(e) {
    e.preventDefault();
    if (password !== confirmPassword) {
      setError("Passwords don't match.");
      return;
    }
    setPending(true);
    setError(null);
    try {
      await signup(email, password);
      onSuccess?.();
    } catch (err) {
      setError(err.message || 'Could not create account.');
    } finally {
      setPending(false);
    }
  }

  return (
    <form className="flex flex-col gap-md w-full" onSubmit={handleSubmit}>
      <div className="flex flex-col gap-xs">
        <label className="font-label-caps text-label-caps text-on-surface-variant uppercase tracking-wider" htmlFor="email">
          Email Address
        </label>
        <div className="relative group">
          <span className="absolute left-sm top-1/2 -translate-y-1/2 material-symbols-outlined text-outline-variant group-focus-within:text-primary transition-colors">mail</span>
          <input
            id="email"
            type="email"
            required
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            placeholder="name@example.com"
            className="w-full h-12 bg-surface pl-[40px] pr-sm rounded-lg border border-outline-variant/50 text-on-surface font-body-md placeholder:text-outline-variant/50 focus:outline-none focus:border-primary focus:ring-1 focus:ring-primary transition-all"
          />
        </div>
      </div>

      <div className="flex flex-col gap-xs">
        <label className="font-label-caps text-label-caps text-on-surface-variant uppercase tracking-wider" htmlFor="password">
          Password
        </label>
        <div className="relative group">
          <span className="absolute left-sm top-1/2 -translate-y-1/2 material-symbols-outlined text-outline-variant group-focus-within:text-primary transition-colors">lock</span>
          <input
            id="password"
            type="password"
            required
            minLength={8}
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            placeholder="••••••••"
            className="w-full h-12 bg-surface pl-[40px] pr-sm rounded-lg border border-outline-variant/50 text-on-surface font-body-md placeholder:text-outline-variant/50 focus:outline-none focus:border-primary focus:ring-1 focus:ring-primary transition-all"
          />
        </div>
      </div>

      <div className="flex flex-col gap-xs mb-sm">
        <label className="font-label-caps text-label-caps text-on-surface-variant uppercase tracking-wider" htmlFor="confirm_password">
          Confirm Password
        </label>
        <div className="relative group">
          <span className="absolute left-sm top-1/2 -translate-y-1/2 material-symbols-outlined text-outline-variant group-focus-within:text-primary transition-colors">lock_reset</span>
          <input
            id="confirm_password"
            type="password"
            required
            value={confirmPassword}
            onChange={(e) => setConfirmPassword(e.target.value)}
            placeholder="••••••••"
            className="w-full h-12 bg-surface pl-[40px] pr-sm rounded-lg border border-outline-variant/50 text-on-surface font-body-md placeholder:text-outline-variant/50 focus:outline-none focus:border-primary focus:ring-1 focus:ring-primary transition-all"
          />
        </div>
      </div>

      {error && <p className="text-live text-data-tabular font-data-tabular">{error}</p>}

      <button
        type="submit"
        disabled={pending}
        className="relative w-full h-12 bg-primary-container hover:bg-primary-container/90 active:scale-[0.98] text-on-primary-container font-headline-md text-body-md rounded-lg transition-all duration-200 flex items-center justify-center disabled:opacity-70"
      >
        {pending ? <span className="material-symbols-outlined animate-spin">progress_activity</span> : 'Create Account'}
      </button>
    </form>
  );
}
