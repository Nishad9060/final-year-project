import { createContext, useContext, useEffect, useMemo, useState, useCallback } from 'react';
import * as api from '../api/client';

const AuthContext = createContext(null);

export function AuthProvider({ children }) {
  const [user, setUser] = useState(null);
  // authLoading only matters to ProtectedRoute (deciding whether to redirect
  // to /login before an existing token has had a chance to hydrate). Public
  // pages never read this — they render immediately regardless.
  const [authLoading, setAuthLoading] = useState(true);

  useEffect(() => {
    const token = api.getToken();
    if (!token) {
      setAuthLoading(false);
      return;
    }
    api
      .getMe()
      .then((me) => setUser(me))
      .catch(() => api.clearToken())
      .finally(() => setAuthLoading(false));
  }, []);

  const login = useCallback(async (email, password) => {
    const result = await api.login({ email, password });
    api.setToken(result.token);
    const me = await api.getMe();
    setUser(me);
    return me;
  }, []);

  const signup = useCallback(
    async (email, password) => {
      await api.signup({ email, password });
      return login(email, password);
    },
    [login],
  );

  const updateProfile = useCallback(async (data) => {
    const me = await api.updateMe(data);
    setUser(me);
    return me;
  }, []);

  const logout = useCallback(async () => {
    try {
      await api.logout();
    } catch {
      // best-effort — still clear local session even if the request fails
    }
    api.clearToken();
    setUser(null);
  }, []);

  const value = useMemo(
    () => ({
      user,
      isAuthenticated: Boolean(user),
      authLoading,
      login,
      signup,
      logout,
      updateProfile,
    }),
    [user, authLoading, login, signup, logout, updateProfile],
  );

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
}

export function useAuth() {
  const ctx = useContext(AuthContext);
  if (!ctx) throw new Error('useAuth must be used within an AuthProvider');
  return ctx;
}
