import { computed, ref } from "vue";
import { defineStore } from "pinia";

import { api } from "@/utils/api";

import { ApiResponseCode, Role, type AuthUser } from "@/types";

export const useAuthStore = defineStore("auth", () => {
  const loading = ref(false);
  function setLoading(value: boolean) {
    loading.value = value;
  }

  function login() {
    window.location.href = `${import.meta.env.VITE_API_BASE_URL}/auth/login`;
  }

  const user = ref<AuthUser | null>(null);
  const isAuthenticated = computed(() => !!user.value);
  const isAdmin = computed(() => {
    return !!user.value && user.value.roles.includes(Role.Admin);
  });

  async function initialize() {
    try {
      loading.value = true;

      const res = await api.get<AuthUser>("/auth/me");

      if (res.status.code !== ApiResponseCode.Success)
        throw new Error(res.status.message);

      user.value = res.data;
    } catch {
      user.value = null;
    } finally {
      loading.value = false;
    }
  }

  async function logout() {
    try {
      loading.value = true;

      const res = await api.post<AuthUser>("/auth/logout");

      if (res.status.code !== ApiResponseCode.Success)
        throw new Error(res.status.message);

      user.value = null;
      login();
    } finally {
      loading.value = false;
    }
  }

  return {
    isAuthenticated,
    loading,
    user,
    isAdmin,
    setLoading,
    login,
    logout,
    initialize,
  };
});
