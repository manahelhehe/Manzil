/**
 * Manzil API Service Layer
 * Handles all communication with the backend
 */

const API_BASE = 'http://localhost:8080/api';

// ── AUTH ──────────────────────────────────────────────────────
const AuthService = {
  async login(email, password) {
    try {
      const response = await fetch(`${API_BASE}/auth/login`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ email, password })
      });
      
      if (!response.ok) {
        const error = await response.json();
        throw new Error(error.message || 'Login failed');
      }
      
      const data = await response.json();
      // Store user info and token
      localStorage.setItem('manzilUser', JSON.stringify({
        userId: data.userId,
        email: data.email,
        fullname: data.fullname,
        token: data.token,
        loggedIn: true
      }));
      
      return data;
    } catch (error) {
      console.error('Login error:', error);
      throw error;
    }
  },

  async signup(email, password, fullname) {
    try {
      const response = await fetch(`${API_BASE}/auth/register`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ email, password, fullname })
      });
      
      if (!response.ok) {
        const error = await response.json();
        throw new Error(error.message || 'Signup failed');
      }
      
      const data = await response.json();
      localStorage.setItem('manzilUser', JSON.stringify({
        userId: data.userId,
        email: data.email,
        fullname: data.fullname,
        token: data.token,
        loggedIn: true
      }));
      
      return data;
    } catch (error) {
      console.error('Signup error:', error);
      throw error;
    }
  },

  logout() {
    localStorage.removeItem('manzilUser');
    window.location.href = 'index.html';
  },

  getCurrentUser() {
    const userStr = localStorage.getItem('manzilUser');
    return userStr ? JSON.parse(userStr) : null;
  },

  getToken() {
    const user = this.getCurrentUser();
    return user?.token || null;
  },

  isLoggedIn() {
    return !!this.getCurrentUser()?.token;
  }
};

// ── PLACES ────────────────────────────────────────────────────
const PlaceService = {
  getHeaders() {
    const token = AuthService.getToken();
    return {
      'Content-Type': 'application/json',
      ...(token && { 'Authorization': `Bearer ${token}` })
    };
  },

  async getAll() {
    try {
      const response = await fetch(`${API_BASE}/places`, {
        headers: this.getHeaders()
      });
      return response.ok ? response.json() : [];
    } catch (error) {
      console.error('Error fetching places:', error);
      return [];
    }
  },

  async getById(placeId) {
    try {
      const response = await fetch(`${API_BASE}/places/${placeId}`, {
        headers: this.getHeaders()
      });
      return response.ok ? response.json() : null;
    } catch (error) {
      console.error('Error fetching place:', error);
      return null;
    }
  },

  async getByCategory(categoryId) {
    try {
      const response = await fetch(`${API_BASE}/places/category/${categoryId}`, {
        headers: this.getHeaders()
      });
      return response.ok ? response.json() : [];
    } catch (error) {
      console.error('Error fetching places by category:', error);
      return [];
    }
  },

  async getByVibe(vibeId) {
    try {
      const response = await fetch(`${API_BASE}/places/vibe?vibeID=${vibeId}`, {
        headers: this.getHeaders()
      });
      return response.ok ? response.json() : [];
    } catch (error) {
      console.error('Error fetching places by vibe:', error);
      return [];
    }
  },

  async getNearby(lat, lng, radius = 5000) {
    try {
      const response = await fetch(
        `${API_BASE}/places/near?lat=${lat}&lng=${lng}&radius=${radius}`,
        { headers: this.getHeaders() }
      );
      return response.ok ? response.json() : [];
    } catch (error) {
      console.error('Error fetching nearby places:', error);
      return [];
    }
  },

  async getOpen() {
    try {
      const response = await fetch(`${API_BASE}/places/open`, {
        headers: this.getHeaders()
      });
      return response.ok ? response.json() : [];
    } catch (error) {
      console.error('Error fetching open places:', error);
      return [];
    }
  },

  async search(query) {
    try {
      const response = await fetch(
        `${API_BASE}/places/search?query=${encodeURIComponent(query)}`,
        { headers: this.getHeaders() }
      );
      return response.ok ? response.json() : [];
    } catch (error) {
      console.error('Error searching places:', error);
      return [];
    }
  },

  async searchByCity(city) {
    try {
      const response = await fetch(
        `${API_BASE}/places/city?city=${encodeURIComponent(city)}`,
        { headers: this.getHeaders() }
      );
      return response.ok ? response.json() : [];
    } catch (error) {
      console.error('Error searching by city:', error);
      return [];
    }
  }
};

// ── BOOKMARKS ─────────────────────────────────────────────────
const BookmarkService = {
  getHeaders() {
    const token = AuthService.getToken();
    return {
      'Content-Type': 'application/json',
      ...(token && { 'Authorization': `Bearer ${token}` })
    };
  },

  async getAll() {
    if (!AuthService.isLoggedIn()) {
      console.warn('Not logged in - cannot fetch bookmarks');
      return [];
    }
    try {
      const response = await fetch(`${API_BASE}/bookmarks`, {
        headers: this.getHeaders()
      });
      return response.ok ? response.json() : [];
    } catch (error) {
      console.error('Error fetching bookmarks:', error);
      return [];
    }
  },

  async add(placeId) {
    if (!AuthService.isLoggedIn()) {
      console.warn('Not logged in - cannot add bookmark');
      return false;
    }
    try {
      const response = await fetch(`${API_BASE}/bookmarks`, {
        method: 'POST',
        headers: this.getHeaders(),
        body: JSON.stringify({ placeId })
      });
      return response.ok;
    } catch (error) {
      console.error('Error adding bookmark:', error);
      return false;
    }
  },

  async remove(placeId) {
    if (!AuthService.isLoggedIn()) {
      console.warn('Not logged in - cannot remove bookmark');
      return false;
    }
    try {
      const response = await fetch(`${API_BASE}/bookmarks/${placeId}`, {
        method: 'DELETE',
        headers: this.getHeaders()
      });
      return response.ok;
    } catch (error) {
      console.error('Error removing bookmark:', error);
      return false;
    }
  },

  async isBookmarked(placeId) {
    if (!AuthService.isLoggedIn()) return false;
    try {
      const bookmarks = await this.getAll();
      return bookmarks.some(b => b.placeId === placeId);
    } catch {
      return false;
    }
  }
};

// ── LIKED PLACES ──────────────────────────────────────────────
const LikeService = {
  getHeaders() {
    const token = AuthService.getToken();
    return {
      'Content-Type': 'application/json',
      ...(token && { 'Authorization': `Bearer ${token}` })
    };
  },

  async getAll() {
    if (!AuthService.isLoggedIn()) {
      console.warn('Not logged in - cannot fetch liked places');
      return [];
    }
    try {
      const response = await fetch(`${API_BASE}/likedplaces`, {
        headers: this.getHeaders()
      });
      return response.ok ? response.json() : [];
    } catch (error) {
      console.error('Error fetching liked places:', error);
      return [];
    }
  },

  async add(placeId) {
    if (!AuthService.isLoggedIn()) {
      console.warn('Not logged in - cannot like place');
      return false;
    }
    try {
      const response = await fetch(`${API_BASE}/likedplaces`, {
        method: 'POST',
        headers: this.getHeaders(),
        body: JSON.stringify({ placeId })
      });
      return response.ok;
    } catch (error) {
      console.error('Error liking place:', error);
      return false;
    }
  },

  async remove(placeId) {
    if (!AuthService.isLoggedIn()) {
      console.warn('Not logged in - cannot unlike place');
      return false;
    }
    try {
      const response = await fetch(`${API_BASE}/likedplaces/${placeId}`, {
        method: 'DELETE',
        headers: this.getHeaders()
      });
      return response.ok;
    } catch (error) {
      console.error('Error unliking place:', error);
      return false;
    }
  },

  async isLiked(placeId) {
    if (!AuthService.isLoggedIn()) return false;
    try {
      const likedPlaces = await this.getAll();
      return likedPlaces.some(l => l.placeId === placeId);
    } catch {
      return false;
    }
  }
};

// ── REVIEWS ───────────────────────────────────────────────────
const ReviewService = {
  getHeaders() {
    const token = AuthService.getToken();
    return {
      'Content-Type': 'application/json',
      ...(token && { 'Authorization': `Bearer ${token}` })
    };
  },

  async getByPlace(placeId) {
    try {
      const response = await fetch(`${API_BASE}/reviews?placeId=${placeId}`, {
        headers: this.getHeaders()
      });
      return response.ok ? response.json() : [];
    } catch (error) {
      console.error('Error fetching reviews:', error);
      return [];
    }
  },

  async create(placeId, rating, comment) {
    if (!AuthService.isLoggedIn()) {
      console.warn('Not logged in - cannot create review');
      return null;
    }
    try {
      const response = await fetch(`${API_BASE}/reviews`, {
        method: 'POST',
        headers: this.getHeaders(),
        body: JSON.stringify({ placeId, rating, comment })
      });
      return response.ok ? response.json() : null;
    } catch (error) {
      console.error('Error creating review:', error);
      return null;
    }
  }
};

// ── USER ──────────────────────────────────────────────────────
const UserService = {
  getHeaders() {
    const token = AuthService.getToken();
    return {
      'Content-Type': 'application/json',
      ...(token && { 'Authorization': `Bearer ${token}` })
    };
  },

  async getProfile() {
    if (!AuthService.isLoggedIn()) {
      console.warn('Not logged in');
      return null;
    }
    try {
      const response = await fetch(`${API_BASE}/users/profile`, {
        headers: this.getHeaders()
      });
      return response.ok ? response.json() : null;
    } catch (error) {
      console.error('Error fetching profile:', error);
      return null;
    }
  },

  async updateProfile(fullname, phone) {
    if (!AuthService.isLoggedIn()) {
      console.warn('Not logged in');
      return false;
    }
    try {
      const response = await fetch(`${API_BASE}/users/profile`, {
        method: 'PUT',
        headers: this.getHeaders(),
        body: JSON.stringify({ fullname, phone })
      });
      return response.ok;
    } catch (error) {
      console.error('Error updating profile:', error);
      return false;
    }
  },

  async deleteAccount() {
    if (!AuthService.isLoggedIn()) {
      console.warn('Not logged in');
      return false;
    }
    try {
      const response = await fetch(`${API_BASE}/users/profile`, {
        method: 'DELETE',
        headers: this.getHeaders()
      });
      if (response.ok) {
        AuthService.logout();
      }
      return response.ok;
    } catch (error) {
      console.error('Error deleting account:', error);
      return false;
    }
  }
};

// ── CATEGORIES ────────────────────────────────────────────────
const CategoryService = {
  async getAll() {
    try {
      const response = await fetch(`${API_BASE}/categories`);
      return response.ok ? response.json() : [];
    } catch (error) {
      console.error('Error fetching categories:', error);
      return [];
    }
  }
};

// ── VIBES ─────────────────────────────────────────────────────
const VibeService = {
  async getAll() {
    try {
      const response = await fetch(`${API_BASE}/vibes`);
      return response.ok ? response.json() : [];
    } catch (error) {
      console.error('Error fetching vibes:', error);
      return [];
    }
  }
};
