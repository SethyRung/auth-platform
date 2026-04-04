<script setup lang="ts">
import { computed, onMounted, ref } from "vue";
import { useRouter, useRoute } from "vue-router";

import { useAuthStore } from "@/stores/auth";

import type { NavigationMenuItem } from "@nuxt/ui";
import { Role } from "@/types";

const router = useRouter();
const route = useRoute();
const authStore = useAuthStore();

onMounted(async () => {
  if (!authStore.user) {
    return;
  }

  if (route.path !== "/") {
    return;
  }

  if (authStore.user.roles.includes(Role.Admin)) {
    router.push("/dashboard");
  } else {
    router.push("/tickets");
  }
});

const search = ref("");

const sidebarItems = computed<NavigationMenuItem[]>(() => [
  ...(authStore.isAdmin
    ? [
        {
          label: "Dashboard",
          icon: "i-lucide-layout-dashboard",
          to: "/dashboard",
        },
      ]
    : []),
  {
    label: "My Tickets",
    icon: "i-lucide-ticket",
    to: "/tickets",
  },
  {
    label: "Department Tickets",
    icon: "i-lucide-send",
  },
  ...(authStore.isAdmin
    ? [
        {
          label: "Users",
          icon: "i-lucide-users",
        },
      ]
    : []),
]);

const navbarTitle = computed(() => {
  const match = sidebarItems.value.find(
    (i) => typeof i.to === "string" && route.path.startsWith(i.to),
  );
  return match?.label ?? "";
});

const userMenuItems = ref<NavigationMenuItem[]>([
  {
    label: "GitHub",
    icon: "i-simple-icons:github",
    to: "https://github.com/SethyRung/helpdesk",
    target: "_blank",
  },
  {
    label: "Support",
    icon: "i-lucide:circle-help",
  },
  {
    label: "Logout",
    icon: "i-lucide-log-out",
    onSelect: authStore.logout,
  },
]);
</script>

<template>
  <div class="min-h-screen bg-default">
    <UDashboardGroup class="h-screen w-full">
      <UDashboardSidebar collapsible resizable :ui="{ footer: 'border-t border-default' }">
        <template #header="{ collapsed }">
          <div class="flex items-center gap-2">
            <div class="flex h-8 w-8 items-center justify-center rounded-lg bg-muted">
              <UIcon name="i-lucide-life-buoy" />
            </div>
            <h1 v-if="!collapsed" class="text-lg font-semibold">Helpdesk</h1>
          </div>
        </template>

        <template #default="{ collapsed }">
          <UInput
            v-if="!collapsed"
            v-model="search"
            placeholder="Search..."
            icon="i-lucide-search"
            class="mb-4"
          />

          <UNavigationMenu :collapsed="collapsed" :items="sidebarItems" orientation="vertical" />

          <div class="flex-1"></div>

          <UNavigationMenu :collapsed="collapsed" :items="userMenuItems" orientation="vertical" />
        </template>

        <template #footer="{ collapsed }">
          <UUser
            :name="authStore.user?.username"
            :avatar="{
              src: '',
              loading: 'lazy',
            }"
            :ui="{
              root: 'flex-1',
              wrapper: collapsed ? 'hidden' : '',
            }"
          />
        </template>
      </UDashboardSidebar>

      <UDashboardPanel
        :ui="{
          body: 'space-y-6',
        }"
      >
        <template #header>
          <UDashboardNavbar :title="navbarTitle">
            <template #leading>
              <UDashboardSidebarCollapse variant="ghost" />
            </template>
          </UDashboardNavbar>
        </template>

        <template #body>
          <RouterView />
        </template>
      </UDashboardPanel>
    </UDashboardGroup>
  </div>
</template>
