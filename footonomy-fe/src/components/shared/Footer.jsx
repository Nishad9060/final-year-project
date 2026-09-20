export default function Footer() {
  return (
    <footer className="w-full bg-surface-container-lowest border-t border-outline-variant/20 py-xl mt-xl">
      <div className="max-w-max-width mx-auto px-margin-mobile lg:px-margin-desktop">
        <div className="flex flex-col md:flex-row justify-between items-center gap-lg">
          <span className="font-headline-md text-headline-md tracking-tighter text-on-surface-variant opacity-50">
            FOOTONOMY
          </span>
          <div className="flex gap-lg text-on-surface-variant text-data-tabular">
            <a className="hover:text-primary transition-colors" href="#terms">
              Terms
            </a>
            <a className="hover:text-primary transition-colors" href="#privacy">
              Privacy
            </a>
            <a className="hover:text-primary transition-colors" href="#support">
              Support
            </a>
          </div>
        </div>
      </div>
    </footer>
  );
}
