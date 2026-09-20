import { Link } from 'react-router-dom';

export default function NotFound() {
  return (
    <div className="max-w-max-width mx-auto w-full px-margin-mobile py-xl flex flex-col items-center text-center gap-md">
      <h1 className="font-headline-lg text-headline-lg text-on-surface">Page not found</h1>
      <p className="font-body-md text-body-md text-on-surface-variant">
        The page you're looking for doesn't exist.
      </p>
      <Link to="/" className="text-primary hover:text-primary-fixed font-data-tabular text-data-tabular">
        Back to Matches
      </Link>
    </div>
  );
}
