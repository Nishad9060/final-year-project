import { useState } from 'react';
import { useAuth } from '../../context/AuthContext';

export default function LoginForm({ onSuccess }) {
  const { login } = useAuth();
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [showPassword, setShowPassword] = useState(false);
  const [pending, setPending] = useState(false);
  const [error, setError] = useState(null);

  async function handleSubmit(e) {
    e.preventDefault();
    setPending(true);
    setError(null);
    try {
      await login(email, password);
      onSuccess?.();
    } catch (err) {
      setError(err.message || 'Invalid email or password.');
    } finally {
      setPending(false);
    }
  }

  return (
    <form className="flex flex-col gap-md w-full" onSubmit={handleSubmit}>
      <div className="flex flex-col gap-xs group">
        <label className="font-label-caps text-label-caps text-on-surface-variant group-focus-within:text-primary transition-colors" htmlFor="email">
          Email Address
        </label>
        <div className="relative">
          <span className="material-symbols-outlined absolute left-md top-1/2 -translate-y-1/2 text-on-surface-variant">mail</span>
          <input
            id="email"
            type="email"
            required
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            placeholder="manager@club.com"
            className="w-full bg-surface-container-high text-on-surface font-body-md text-body-md rounded-lg py-md pl-[48px] pr-md outline-none focus:ring-2 focus:ring-primary transition-all placeholder:text-on-surface-variant/50"
          />
        </div>
      </div>

      <div className="flex flex-col gap-xs group">
        <label className="font-label-caps text-label-caps text-on-surface-variant group-focus-within:text-primary transition-colors" htmlFor="password">
          Password
        </label>
        <div className="relative">
          <span className="material-symbols-outlined absolute left-md top-1/2 -translate-y-1/2 text-on-surface-variant">lock</span>
          <input
            id="password"
            type={showPassword ? 'text' : 'password'}
            required
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            placeholder="••••••••"
            className="w-full bg-surface-container-high text-on-surface font-body-md text-body-md rounded-lg py-md pl-[48px] pr-md outline-none focus:ring-2 focus:ring-primary transition-all placeholder:text-on-surface-variant/50"
          />
          <button
            type="button"
            onClick={() => setShowPassword((v) => !v)}
            className="absolute right-md top-1/2 -translate-y-1/2 text-on-surface-variant hover:text-on-surface transition-colors"
          >
            <span className="material-symbols-outlined">{showPassword ? 'visibility' : 'visibility_off'}</span>
          </button>
        </div>
      </div>

      {error && <p className="text-live text-data-tabular font-data-tabular">{error}</p>}

      <button
        type="submit"
        disabled={pending}
        className="mt-sm w-full bg-primary-container text-on-primary-container font-headline-md text-[18px] py-md rounded-lg shadow-md hover:bg-inverse-primary hover:shadow-lg transition-all active:scale-[0.98] flex justify-center items-center gap-sm disabled:opacity-70"
      >
        {pending ? (
          <span className="material-symbols-outlined animate-spin">progress_activity</span>
        ) : (
          <>
            <span>Sign In</span>
            <span className="material-symbols-outlined">arrow_forward</span>
          </>
        )}
      </button>
    </form>
  );
}
