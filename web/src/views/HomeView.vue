<script setup lang="ts">
import { computed, h, onMounted, ref } from "vue";
import { useRouter } from "vue-router";

import { useAuthStore } from "@/stores/auth";

import type { DropdownMenuItem, NavigationMenuItem } from "@nuxt/ui";

const router = useRouter();
const authStore = useAuthStore();

onMounted(async () => {
  await authStore.initialize();
});

const search = ref("");

const sidebarItems = computed<NavigationMenuItem[]>(() => [
  ...(authStore.isAdmin
    ? [
        {
          label: "Dashboard",
          icon: "i-lucide-layout-dashboard",
          click: () => router.push("/dashboard"),
        },
      ]
    : []),
  {
    label: "My Tickets",
    icon: "i-lucide-ticket",
    active: true,
    click: () => router.push("/tickets"),
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

const userMenuItems = ref<DropdownMenuItem[][]>([
  [
    {
      label: "GitHub",
      icon: "i-simple-icons:github",
      to: "https://github.com/SethyRung/auth-platform",
      target: "_blank",
    },
    {
      label: "Support",
      icon: "i-lucide-life-buoy",
    },
  ],
  [
    {
      label: "Logout",
      icon: "i-lucide-log-out",
      color: "error",
      onSelect: authStore.logout,
    },
  ],
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
        </template>

        <template #footer="{ collapsed }">
          <UDropdownMenu
            :items="userMenuItems"
            :content="{
              side: 'right',
            }"
          >
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
          </UDropdownMenu>
        </template>
      </UDashboardSidebar>

      <UDashboardPanel
        :ui="{
          body: 'space-y-6',
        }"
      >
        <template #header>
          <UDashboardNavbar title="My Tickets">
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
