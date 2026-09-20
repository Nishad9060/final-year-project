// Single shared API client. Every request the app makes to the backend goes
// through the functions exported here — no ad-hoc fetch/axios calls in
// components. Endpoint paths/methods match docs/03_TRD.md §4 exactly; that
// contract is frozen, do not add/rename/reshape endpoints here without
// checking with the contract owner first.

const BASE_URL = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080';

const TOKEN_STORAGE_KEY = 'footonomy_auth_token';

export function getToken() {
  return localStorage.getItem(TOKEN_STORAGE_KEY);
}

export function setToken(token) {
  localStorage.setItem(TOKEN_STORAGE_KEY, token);
}

export function clearToken() {
  localStorage.removeItem(TOKEN_STORAGE_KEY);
}

export class ApiError extends Error {
  constructor(status, message, body) {
    super(message);
    this.name = 'ApiError';
    this.status = status;
    this.body = body;
  }
}

async function request(path, { method = 'GET', body, auth = false, params } = {}) {
  const url = new URL(`${BASE_URL}${path}`);
  if (params) {
    Object.entries(params).forEach(([key, value]) => {
      if (value !== undefined && value !== null && value !== '') {
        url.searchParams.set(key, value);
      }
    });
  }

  const headers = { 'Content-Type': 'application/json' };
  if (auth) {
    const token = getToken();
    if (token) headers.Authorization = `Bearer ${token}`;
  }

  const response = await fetch(url, {
    method,
    headers,
    body: body !== undefined ? JSON.stringify(body) : undefined,
  });

  const contentType = response.headers.get('content-type') || '';
  const payload = contentType.includes('application/json')
    ? await response.json().catch(() => null)
    : await response.text().catch(() => null);

  if (!response.ok) {
    throw new ApiError(response.status, payload?.message || response.statusText, payload);
  }

  return payload;
}

// ---- Public (no auth) ----

export function getMatches({ date, league } = {}) {
  return request('/api/matches', { params: { date, league } });
}

export function getMatch(id) {
  return request(`/api/matches/${id}`);
}

export function getTournaments() {
  return request('/api/tournaments');
}

export function getTournament(id) {
  return request(`/api/tournaments/${id}`);
}

export function getTournamentStandings(id) {
  return request(`/api/tournaments/${id}/standings`);
}

export function getTournamentFixtures(id) {
  return request(`/api/tournaments/${id}/fixtures`);
}

export function search({ q, type } = {}) {
  return request('/api/search', { params: { q, type } });
}

// ---- Auth ----

export function signup({ email, password }) {
  return request('/api/auth/signup', { method: 'POST', body: { email, password } });
}

export function login({ email, password }) {
  return request('/api/auth/login', { method: 'POST', body: { email, password } });
}

export function logout() {
  return request('/api/auth/logout', { method: 'POST', auth: true });
}

// ---- Authenticated ----

export function getMe() {
  return request('/api/users/me', { auth: true });
}

export function updateMe(data) {
  return request('/api/users/me', { method: 'PUT', body: data, auth: true });
}

export function getPreferences() {
  return request('/api/users/me/preferences', { auth: true });
}

export function updatePreferences(data) {
  return request('/api/users/me/preferences', { method: 'PUT', body: data, auth: true });
}

export function getFollowing() {
  return request('/api/users/me/following', { auth: true });
}

export function addFollowing({ entity_type, entity_id }) {
  return request('/api/users/me/following', {
    method: 'POST',
    body: { entity_type, entity_id },
    auth: true,
  });
}

export function deleteFollowing(id) {
  return request(`/api/users/me/following/${id}`, { method: 'DELETE', auth: true });
}
