import { createRouter, createWebHistory } from "vue-router";
import { useAuthStore } from "@/stores/auth";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      component: () => import("@/views/HomeView.vue"),
      children: [
        {
          path: "dashboard",
          name: "dashboard",
          component: () => import("@/views/DashboardView.vue"),
          meta: { requiresAdmin: true },
        },
        {
          path: "tickets",
          name: "tickets",
          component: () => import("@/views/TicketsView.vue"),
        },
        {
          path: "tickets/:id",
          name: "ticket-details",
          component: () => import("@/views/TicketDetailsView.vue"),
        },
      ],
    },
  ],
});

router.beforeEach(async (to, _from) => {
  const authStore = useAuthStore();

  try {
    if (!authStore.user) {
      await authStore.initialize();
    }
  } catch {
    return false;
  }

  const requiresAdmin = to.matched.some(
    (record) => (record.meta as Record<string, unknown>)?.requiresAdmin === true,
  );

  if (requiresAdmin && !authStore.isAdmin) {
    return { name: "tickets" };
  }

  return true;
});

export default router;
