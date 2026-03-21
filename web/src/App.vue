<script setup lang="ts">
import { onMounted } from "vue";
import { useAuthStore } from "@/stores/auth";

const authStore = useAuthStore();

onMounted(async () => {
  await authStore.initialize();
  if (!authStore.isAuthenticated) {
    authStore.login();
  }
});
</script>

<template>
  <UApp>
    <RouterView />
  </UApp>
</template>
