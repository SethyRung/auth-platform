import type { AuthUser } from "@/types";
import { api } from "@/utils/api";

class AuthService {
  getLoginUrl() {
    return `${import.meta.env.VITE_API_BASE_URL}/auth/login`;
  }

  login() {
    window.location.href = this.getLoginUrl();
  }

  async getMe() {
    return await api.get<AuthUser>("/auth/me");
  }

  async refresh() {
    return await api.post<string>("/auth/refresh");
  }

  async logout() {
    return await api.post<string>("/auth/logout");
  }
}

export const authService = new AuthService();
