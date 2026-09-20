import { Link, useLocation, useNavigate } from 'react-router-dom';
import SignupForm from '../components/auth/SignupForm';

// Public route — /signup. Same redirect-back convention as /login.
export default function Signup() {
  const navigate = useNavigate();
  const location = useLocation();
  const from = location.state?.from || '/';

  return (
    <div className="w-full min-h-[calc(100vh-64px)] flex items-center justify-center p-margin-mobile">
      <div className="relative w-full max-w-md mx-auto p-margin-desktop bg-surface-container rounded-xl border border-outline-variant/30 shadow-2xl">
        <div className="mb-xl text-center">
          <div className="inline-flex items-center justify-center w-12 h-12 rounded-full bg-primary-container text-on-primary-container mb-lg">
            <span className="material-symbols-outlined text-headline-md">sports_soccer</span>
          </div>
          <h1 className="font-headline-lg text-headline-lg text-on-surface mb-xs tracking-tight">Join Footonomy</h1>
          <p className="font-body-md text-body-md text-on-surface-variant">Your ultimate football companion.</p>
        </div>

        <SignupForm onSuccess={() => navigate(from, { replace: true })} />

        <div className="mt-lg pt-lg border-t border-outline-variant/20 text-center">
          <p className="font-body-md text-body-md text-on-surface-variant">
            Already have an account?{' '}
            <Link to="/login" state={{ from }} className="text-primary hover:text-primary-fixed hover:underline transition-colors">
              Sign In
            </Link>
          </p>
        </div>
      </div>
    </div>
  );
}
