const API_BASE = 'https://manzil-production-73bf.up.railway.app/api';

const AuthService = {

  async signup(name, email, phone, password, favouriteCategories = []) {
    try {
      const response = await fetch(`${API_BASE}/users/register`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ name, email, phone, password, favouriteCategories })
      });

      if (!response.ok) {
        const error = await response.json();
        throw new Error(error.message || 'Signup failed');
      }

      const data = await response.json(); // returns UserResponseDTO
      localStorage.setItem('manzilUser', JSON.stringify({
        userId:    data.userId,
        name:      data.name,
        email:     data.email,
        phone:     data.phone,
        role:      data.role,
        loggedIn:  true
      }));

      return data;
    } catch (error) {
      console.error('Signup error:', error);
      throw error;
    }
  },

  async login(email, password) {
    try {
      const response = await fetch(
        `${API_BASE}/users/login?email=${encodeURIComponent(email)}&password=${encodeURIComponent(password)}`,
        { method: 'POST' }
      );

      if (!response.ok) {
        const error = await response.json();
        throw new Error(error.message || 'Login failed');
      }

      const data = await response.json(); // returns UserResponseDTO
      localStorage.setItem('manzilUser', JSON.stringify({
        userId:   data.userId,
        name:     data.name,
        email:    data.email,
        phone:    data.phone,
        role:     data.role,
        loggedIn: true
      }));

      return data;
    } catch (error) {
      console.error('Login error:', error);
      throw error;
    }
  },

  logout() {
    localStorage.removeItem('manzilUser');
    window.location.href = 'index.html';
  },

  getCurrentUser() {
    const u = localStorage.getItem('manzilUser');
    return u ? JSON.parse(u) : null;
  },

  isLoggedIn() {
    return !!this.getCurrentUser()?.loggedIn;
  },

  getUserId() {
    return this.getCurrentUser()?.userId || null;
  }
};

const PlaceService = {

  async getAll() {
    try {
      const r = await fetch(`${API_BASE}/places`);
      return r.ok ? r.json() : [];
    } catch { return []; }
  },

  // GET /api/places/{id}
  async getById(placeId) {
    try {
      const r = await fetch(`${API_BASE}/places/${placeId}`);
      return r.ok ? r.json() : null;
    } catch { return null; }
  },

  // GET /api/places/category/{id}
  async getByCategory(categoryId) {
    try {
      const r = await fetch(`${API_BASE}/places/category/${categoryId}`);
      return r.ok ? r.json() : [];
    } catch { return []; }
  },

  // GET /api/places/vibe?vibeID={id}
  async getByVibe(vibeId) {
    try {
      const r = await fetch(`${API_BASE}/places/vibe?vibeID=${vibeId}`);
      return r.ok ? r.json() : [];
    } catch { return []; }
  },

  // GET /api/places/near?lat=&lng=&radius=
  async getNearby(lat, lng, radius = 5000) {
    try {
      const r = await fetch(`${API_BASE}/places/near?lat=${lat}&lng=${lng}&radius=${radius}`);
      return r.ok ? r.json() : [];
    } catch { return []; }
  },

  // GET /api/places/open
  async getOpen() {
    try {
      const r = await fetch(`${API_BASE}/places/open`);
      return r.ok ? r.json() : [];
    } catch { return []; }
  },

  // GET /api/places/search?query=
  async search(query) {
    try {
      const r = await fetch(`${API_BASE}/places/search?query=${encodeURIComponent(query)}`);
      return r.ok ? r.json() : [];
    } catch { return []; }
  },

  // GET /api/places/city?city=
  async searchByCity(city) {
    try {
      const r = await fetch(`${API_BASE}/places/city?city=${encodeURIComponent(city)}`);
      return r.ok ? r.json() : [];
    } catch { return []; }
  }
};

// ── BOOKMARKS ─────────────────────────────────────────────────
const BookmarkService = {

  // GET /bookmark/user/{userId}
  async getAll() {
    const userId = AuthService.getUserId();
    if (!userId) return [];
    try {
      const r = await fetch(`https://manzil-production-73bf.up.railway.app/bookmark/user/${userId}`);
      return r.ok ? r.json() : [];
    } catch { return []; }
  },

  // POST /bookmark — body: { userId, placeId }
  async add(placeId) {
    const userId = AuthService.getUserId();
    if (!userId) return false;
    try {
      const r = await fetch(`https://manzil-production-73bf.up.railway.app/bookmark`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ userId, placeId })
      });
      return r.ok;
    } catch { return false; }
  },

  // DELETE /bookmark/user/{userId}/place/{placeId}
  async remove(placeId) {
    const userId = AuthService.getUserId();
    if (!userId) return false;
    try {
      const r = await fetch(`https://manzil-production-73bf.up.railway.app/bookmark/user/${userId}/place/${placeId}`, {
        method: 'DELETE'
      });
      return r.ok;
    } catch { return false; }
  },

  // GET /bookmark/user/{userId}/place/{placeId}
  async isBookmarked(placeId) {
    const userId = AuthService.getUserId();
    if (!userId) return false;
    try {
      const r = await fetch(`https://manzil-production-73bf.up.railway.app/bookmark/user/${userId}/place/${placeId}`);
      return r.ok;
    } catch { return false; }
  }
};

// ── USER ──────────────────────────────────────────────────────
const UserService = {

  // GET /api/users/{id}
  async getProfile() {
    const userId = AuthService.getUserId();
    if (!userId) return null;
    try {
      const r = await fetch(`${API_BASE}/users/${userId}`);
      return r.ok ? r.json() : null;
    } catch { return null; }
  },

  // PUT /api/users/{id} — body matches UserUpdateDTO
  async updateProfile(name, email, phone, profilePhoto, favouriteCategories) {
    const userId = AuthService.getUserId();
    if (!userId) return false;
    try {
      const r = await fetch(`${API_BASE}/users/${userId}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ name, email, phone, profilePhoto, favouriteCategories })
      });
      return r.ok;
    } catch { return false; }
  },

  // DELETE /api/users/{id}
  async deleteAccount() {
    const userId = AuthService.getUserId();
    if (!userId) return false;
    try {
      const r = await fetch(`${API_BASE}/users/${userId}`, { method: 'DELETE' });
      if (r.ok) AuthService.logout();
      return r.ok;
    } catch { return false; }
  },

  // PATCH /api/users/{id}/online
  async setOnline() {
    const userId = AuthService.getUserId();
    if (!userId) return;
    try {
      await fetch(`${API_BASE}/users/${userId}/online`, { method: 'PATCH' });
    } catch {}
  },

  // PATCH /api/users/{id}/offline
  async setOffline() {
    const userId = AuthService.getUserId();
    if (!userId) return;
    try {
      await fetch(`${API_BASE}/users/${userId}/offline`, { method: 'PATCH' });
    } catch {}
  }
};
const LikeService = {
  // GET /api/liked/user/{id}
  async getAll() {
    const userId = AuthService.getUserId();
    if (!userId) return [];
    try {
      const r = await fetch(`${BASE}/liked/user/${userId}`);
      return r.ok ? r.json() : [];
    } catch { return []; }
  },

  // POST /api/liked/toggle/user/{uId}/place/{pId}
  async toggle(placeId) {
    const userId = AuthService.getUserId();
    if (!userId) return false;
    try {
      const r = await fetch(`${BASE}/liked/toggle/user/${userId}/place/${placeId}`, {
        method: 'POST'
      });
      return r.ok ? r.text() : false; // returns "Liked" or "Unliked"
    } catch { return false; }
  },

  // GET /api/liked/user/{uId}/place/{pId}
  async isLiked(placeId) {
    const userId = AuthService.getUserId();
    if (!userId) return false;
    try {
      const r = await fetch(`${BASE}/liked/user/${userId}/place/${placeId}`);
      return r.ok;
    } catch { return false; }
  }
};