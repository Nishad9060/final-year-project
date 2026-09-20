import { Routes, Route } from 'react-router-dom';
import { AuthProvider } from './context/AuthContext';
import { FollowingProvider } from './context/FollowingContext';
import Layout from './components/shared/Layout';
import ProtectedRoute from './routes/ProtectedRoute';

import Home from './pages/Home';
import MatchDetail from './pages/MatchDetail';
import Tournaments from './pages/Tournaments';
import TournamentDetail from './pages/TournamentDetail';
import Search from './pages/Search';
import Login from './pages/Login';
import Signup from './pages/Signup';
import Following from './pages/Following';
import Settings from './pages/Settings';
import NotFound from './pages/NotFound';

export default function App() {
  return (
    <AuthProvider>
      <FollowingProvider>
        <Layout>
          <Routes>
            {/* Public — docs/01_SRS.md FR-4/FR-8/FR-11: zero auth-check overhead */}
            <Route path="/" element={<Home />} />
            <Route path="/match/:id" element={<MatchDetail />} />
            <Route path="/tournaments" element={<Tournaments />} />
            <Route path="/tournament/:id" element={<TournamentDetail />} />
            <Route path="/search" element={<Search />} />
            <Route path="/login" element={<Login />} />
            <Route path="/signup" element={<Signup />} />

            {/* Auth-gated — docs/02_IA_Sitemap.md §2 */}
            <Route element={<ProtectedRoute />}>
              <Route path="/following" element={<Following />} />
              <Route path="/settings" element={<Settings />} />
            </Route>

            <Route path="*" element={<NotFound />} />
          </Routes>
        </Layout>
      </FollowingProvider>
    </AuthProvider>
  );
}
