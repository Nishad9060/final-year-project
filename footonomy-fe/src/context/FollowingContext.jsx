import { createContext, useContext, useEffect, useMemo, useState, useCallback } from 'react';
import * as api from '../api/client';
import { useAuth } from './AuthContext';

const FollowingContext = createContext(null);

export function FollowingProvider({ children }) {
  const { isAuthenticated } = useAuth();
  const [items, setItems] = useState([]);
  const [loading, setLoading] = useState(false);

  const refresh = useCallback(async () => {
    if (!isAuthenticated) return;
    setLoading(true);
    try {
      const result = await api.getFollowing();
      setItems(result || []);
    } finally {
      setLoading(false);
    }
  }, [isAuthenticated]);

  useEffect(() => {
    if (isAuthenticated) {
      refresh();
    } else {
      setItems([]);
    }
  }, [isAuthenticated, refresh]);

  const isFollowing = useCallback(
    (entityType, entityId) =>
      items.some((item) => item.entity_type === entityType && item.entity_id === entityId),
    [items],
  );

  const follow = useCallback(async (entityType, entityId) => {
    const created = await api.addFollowing({ entity_type: entityType, entity_id: entityId });
    setItems((prev) => [...prev, created]);
  }, []);

  const unfollow = useCallback(async (entityType, entityId) => {
    const existing = items.find(
      (item) => item.entity_type === entityType && item.entity_id === entityId,
    );
    if (!existing) return;
    await api.deleteFollowing(existing.id);
    setItems((prev) => prev.filter((item) => item.id !== existing.id));
  }, [items]);

  const value = useMemo(
    () => ({ items, loading, isFollowing, follow, unfollow, refresh }),
    [items, loading, isFollowing, follow, unfollow, refresh],
  );

  return <FollowingContext.Provider value={value}>{children}</FollowingContext.Provider>;
}

export function useFollowing() {
  const ctx = useContext(FollowingContext);
  if (!ctx) throw new Error('useFollowing must be used within a FollowingProvider');
  return ctx;
}
