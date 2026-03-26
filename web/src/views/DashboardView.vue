<script setup lang="ts">
import { computed, h, ref, resolveComponent } from "vue";

import type { TableColumn } from "@nuxt/ui";
import type { Ticket } from "@/types/ticket";

const UBadge = resolveComponent("UBadge");

const tickets = ref<Ticket[]>([
  {
    id: 19,
    title: "DFS Issue",
    description: "Doesn't allow admin to add...",
    createdBy: "Bijay Shrestha",
    priority: "HIGH",
    status: "OPEN",
    createdAt: "2024-03-19T10:30:00",
    updatedAt: "2024-03-19T10:30:00",
  },
  {
    id: 20,
    title: "Login Error",
    description: "The admin is unable to create...",
    createdBy: "Julia Basnet",
    priority: "MEDIUM",
    status: "IN_PROGRESS",
    createdAt: "2024-03-19T09:15:00",
    updatedAt: "2024-03-19T11:20:00",
  },
  {
    id: 18,
    title: "Network Issue",
    description: "Connection timeout error...",
    createdBy: "Ram Bahadur",
    priority: "LOW",
    status: "RESOLVED",
    createdAt: "2024-03-18T14:00:00",
    updatedAt: "2024-03-18T16:30:00",
  },
  {
    id: 17,
    title: "Database Error",
    description: "Query execution failed...",
    createdBy: "Sita Sharma",
    priority: "URGENT",
    status: "OPEN",
    createdAt: "2024-03-18T08:45:00",
    updatedAt: "2024-03-18T08:45:00",
  },
]);

const statCards = computed<
  {
    label: string;
    value: string;
    icon: string;
    color: "primary" | "info" | "warning" | "success";
  }[]
>(() => [
  {
    label: "Total Tickets",
    value: "156",
    icon: "i-lucide-ticket",
    color: "primary",
  },
  {
    label: "Open Tickets",
    value: "48",
    icon: "i-lucide-inbox",
    color: "info",
  },
  {
    label: "In Progress",
    value: "23",
    icon: "i-lucide-timer",
    color: "warning",
  },
  {
    label: "Resolved",
    value: "85",
    icon: "i-lucide-check-circle",
    color: "success",
  },
]);

const columns = computed<TableColumn<Ticket>[]>(() => [
  {
    accessorKey: "id",
    header: "Ticket ID",
  },
  {
    accessorKey: "title",
    header: "Title",
  },
  {
    accessorKey: "description",
    header: "Description",
    cell: ({ row }) => h("span", { class: "line-clamp-1" }, row.original.description ?? ""),
  },
  {
    accessorKey: "createdBy",
    header: "Created By",
  },
  {
    accessorKey: "priority",
    header: "Priority",
    cell: ({ row }) =>
      h(
        UBadge,
        {
          label: row.original.priority,
          color: getPriorityColor(row.original.priority),
          variant: "subtle",
        },
        {},
      ),
  },
  {
    accessorKey: "status",
    header: "Status",
    cell: ({ row }) =>
      h(
        UBadge,
        {
          label: row.original.status,
          color: getStatusColor(row.original.status),
          variant: "subtle",
        },
        {},
      ),
  },
]);

function getPriorityColor(priority: string) {
  switch (priority) {
    case "URGENT":
      return "error";
    case "HIGH":
      return "warning";
    case "MEDIUM":
      return "info";
    case "LOW":
      return "neutral";
    default:
      return "neutral";
  }
}

function getStatusColor(status: string) {
  switch (status) {
    case "OPEN":
      return "info";
    case "IN_PROGRESS":
      return "warning";
    case "RESOLVED":
      return "success";
    case "CLOSED":
      return "neutral";
    default:
      return "neutral";
  }
}
</script>

<template>
  <div class="space-y-6 p-4">
    <div>
      <h2 class="text-lg font-semibold">Overview</h2>
      <p class="text-sm text-muted-foreground">
        Welcome to your dashboard. Here's what's happening.
      </p>
    </div>

    <div class="grid grid-cols-1 gap-4 sm:grid-cols-2 lg:grid-cols-4">
      <UCard v-for="stat in statCards" :key="stat.label">
        <div class="flex items-center justify-between">
          <div>
            <p class="text-sm text-muted-foreground">
              {{ stat.label }}
            </p>
            <p class="text-2xl font-semibold">{{ stat.value }}</p>
          </div>
          <div
            class="flex h-10 w-10 items-center justify-center rounded-lg"
            :class="`bg-${stat.color}/10 text-${stat.color}`"
          >
            <UIcon :name="stat.icon" size="24" />
          </div>
        </div>
      </UCard>
    </div>

    <div>
      <div class="mb-4 flex items-center justify-between">
        <h2 class="text-lg font-semibold">Recent Tickets</h2>

        <div class="flex gap-2">
          <UButton label="Create Ticket" icon="i-lucide-plus" />
          <UButton
            label="View All"
            trailing-icon="i-lucide-arrow-right"
            variant="ghost"
            color="neutral"
          />
        </div>
      </div>

      <UCard>
        <UTable :data="tickets" :columns="columns" sticky="header" />
      </UCard>
    </div>
  </div>
</template>
