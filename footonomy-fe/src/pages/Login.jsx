import { Link, useLocation, useNavigate } from 'react-router-dom';
import LoginForm from '../components/auth/LoginForm';

// Public route — /login. Redirect-back target comes from location.state.from,
// set by ProtectedRoute (or FollowButton) when it sent the user here.
export default function Login() {
  const navigate = useNavigate();
  const location = useLocation();
  const from = location.state?.from || '/';

  return (
    <div className="w-full min-h-[calc(100vh-64px)] flex flex-col items-center justify-center p-margin-mobile">
      <div className="relative z-10 w-full max-w-md bg-surface-container rounded-xl shadow-2xl p-lg flex flex-col gap-lg">
        <div className="flex flex-col items-center gap-sm text-center mb-md">
          <div className="w-16 h-16 bg-primary-container rounded-full flex items-center justify-center shadow-lg mb-sm">
            <span className="material-symbols-outlined text-on-primary-container text-[32px]">sports_soccer</span>
          </div>
          <h1 className="font-headline-lg text-headline-lg text-on-surface">Footonomy</h1>
          <p className="font-body-md text-body-md text-on-surface-variant">Access your tactical companion</p>
        </div>

        <LoginForm onSuccess={() => navigate(from, { replace: true })} />

        <div className="text-center mt-sm">
          <p className="font-body-md text-body-md text-on-surface-variant">
            Don't have an account?{' '}
            <Link to="/signup" state={{ from }} className="text-primary hover:text-primary-fixed font-data-tabular text-data-tabular underline decoration-primary/30 underline-offset-4">
              Sign Up
            </Link>
          </p>
        </div>
      </div>
    </div>
  );
}
