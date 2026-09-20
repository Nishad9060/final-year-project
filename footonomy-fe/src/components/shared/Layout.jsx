import NavBar from './NavBar';
import Footer from './Footer';

export default function Layout({ children }) {
  return (
    <div className="w-full min-h-screen bg-surface flex flex-col">
      <NavBar />
      <main className="w-full pt-16 flex-1">{children}</main>
      <Footer />
    </div>
  );
}
