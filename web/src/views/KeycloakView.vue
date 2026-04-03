<script setup lang="ts">
import { computed, ref } from "vue";
import type { KeycloakData } from "@/types/keycloak";

import LoginView from "@/views/auth/LoginView.vue";
import RegisterView from "@/views/auth/RegisterView.vue";
import type { NavigationMenuItem } from "@nuxt/ui";

const kcData = ref<KeycloakData | null>();

const currentView = computed(() => {
  const page = kcData.value?.page;
  switch (page) {
    case "login":
      return LoginView;
    case "register":
      return RegisterView;
    default:
      return LoginView;
  }
});

const items = computed<NavigationMenuItem[]>(() => [
  {
    label: "Login",
    active: kcData.value?.page === "login",
    onSelect: () => {
      kcData.value = {
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
          loginAction:
            "http://localhost:8080/realms/auth-platform/login-actions/authenticate?session_code=B_G4RIJEZICsOXKLbnZ-jZt3J3tJRZVFrFGXjfu8W8E&amp;execution=cfc4045b-5b0d-499f-a0b4-2a3bbe02ceea&amp;client_id=auth-platform-client&amp;tab_id=GSCHUqYrKjk&amp;client_data=eyJydSI6Imh0dHA6Ly9sb2NhbGhvc3Q6ODAwMC9hcGkvYXV0aC9jYWxsYmFjayIsInJ0IjoiY29kZSJ9",
          forgotPassword:
            "/realms/auth-platform/login-actions/reset-credentials?client_id=auth-platform-client&amp;tab_id=GSCHUqYrKjk&amp;client_data=eyJydSI6Imh0dHA6Ly9sb2NhbGhvc3Q6ODAwMC9hcGkvYXV0aC9jYWxsYmFjayIsInJ0IjoiY29kZSJ9",
          register:
            "/realms/auth-platform/login-actions/registration?client_id=auth-platform-client&amp;tab_id=GSCHUqYrKjk&amp;client_data=eyJydSI6Imh0dHA6Ly9sb2NhbGhvc3Q6ODAwMC9hcGkvYXV0aC9jYWxsYmFjayIsInJ0IjoiY29kZSJ9",
        },
      };
    },
  },
  {
    label: "Register",
    active: kcData.value?.page === "register",
    onSelect: () => {
      kcData.value = {
        page: "register",
        realm: {
          name: "auth-platform",
          displayName: "Helpdesk",
          registrationEmailAsUsername: false,
        },
        messages: {
          firstName: "First name",
          lastName: "Last name",
          email: "Email",
          username: "Username",
          password: "Password",
          passwordConfirm: "Confirm password",
          doRegister: "Register",
        },
        errors: {
          hasError: false,
          firstName: "",
          lastName: "",
          email: "",
          username: "",
          password: "",
          passwordConfirm: "",
        },
        urls: {
          registerAction:
            "http://localhost:8080/realms/auth-platform/login-actions/registration?session_code=B_G4RIJEZICsOXKLbnZ-jZt3J3tJRZVFrFGXjfu8W8E&amp;execution=18fd358a-0cbd-4c0a-9104-cc52d4e22b19&amp;client_id=auth-platform-client&amp;tab_id=GSCHUqYrKjk&amp;client_data=eyJydSI6Imh0dHA6Ly9sb2NhbGhvc3Q6ODAwMC9hcGkvYXV0aC9jYWxsYmFjayIsInJ0IjoiY29kZSJ9",
          loginUrl:
            "/realms/auth-platform/login-actions/authenticate?client_id=auth-platform-client&amp;tab_id=GSCHUqYrKjk&amp;client_data=eyJydSI6Imh0dHA6Ly9sb2NhbGhvc3Q6ODAwMC9hcGkvYXV0aC9jYWxsYmFjayIsInJ0IjoiY29kZSJ9",
        },
      };
    },
  },
]);
</script>

<template>
  <div class="min-h-screen flex items-center justify-center px-4 sm:px-6 lg:px-8 relative">
    <component v-if="kcData" :is="currentView" :kc-data="kcData" />
    <div v-else class="text-center">
      <p class="text-gray-500">Select a page from the menu to preview the Keycloak templates.</p>
    </div>

    <USlideover title="Change page">
      <UButton
        icon="i-lucide:panel-right-open"
        label="Change page"
        color="neutral"
        variant="subtle"
        class="absolute bottom-4 right-4 z-10"
      />

      <template #body>
        <UNavigationMenu
          :items="items"
          color="neutral"
          orientation="vertical"
          type="single"
          :ui="{
            link: 'h-10',
          }"
        />
      </template>
    </USlideover>
  </div>
</template>
