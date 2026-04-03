<script setup lang="ts">
import { computed, ref } from "vue";
import type { KeycloakData } from "@/types/keycloak";

import LoginView from "@/views/auth/LoginView.vue";

const kcData = ref<KeycloakData | null>({
  page: "login",
  realm: {
    name: "auth-platform",
    displayName: "Helpdesk",
    rememberMe: true,
    resetPasswordAllowed: true,
    registrationAllowed: true,
  },
  messages: {
    username: "Username or email",
    password: "Password",
    rememberMe: "Remember me",
    doLogin: "Sign In",
  },
  errors: {
    hasError: false,
    username: "",
    password: "",
  },
  urls: {
    loginAction: "",
    forgotPassword: "",
    register: "",
  },
});

const currentView = computed(() => {
  const page = kcData.value?.page;
  switch (page) {
    case "login":
      return LoginView;
    default:
      return LoginView;
  }
});
</script>

<template>
  <div class="min-h-screen flex items-center justify-center px-4 sm:px-6 lg:px-8">
    <component v-if="kcData" :is="currentView" :kc-data="kcData" />
    <div v-else class="text-center">
      <p class="text-gray-500">Loading...</p>
    </div>
  </div>
</template>
