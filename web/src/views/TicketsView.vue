<script setup lang="ts">
import { computed, h, ref, resolveComponent } from "vue";
import { useRouter } from "vue-router";

import type { TableColumn, TableRow } from "@nuxt/ui";
import type { Ticket } from "@/types/ticket";

import CreateTicketModal from "@/components/CreateTicketModal.vue";
import { getPriorityColor, getStatusColor } from "@/utils/color";
import { formatFullDate } from "@/utils/date";

const UBadge = resolveComponent("UBadge");

const router = useRouter();

function onTicketCreated(ticket: Ticket) {
  tickets.value.unshift(ticket);
}

function onSelect(_e: Event, row: TableRow<Ticket>) {
  router.push({ name: "ticket-details", params: { id: row.id } });
}

// TODO: Replace with actual API call to fetch user's tickets
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
  {
    accessorKey: "createdAt",
    header: "Created At",
    cell: ({ row }) => formatFullDate(row.original.createdAt),
  },
]);
</script>

<template>
  <div>
    <div class="mb-4 flex items-center justify-between">
      <div>
        <h2 class="text-lg font-semibold">My Tickets</h2>
        <p class="text-sm text-muted-foreground">Manage your support tickets</p>
      </div>

      <CreateTicketModal @created="onTicketCreated">
        <UButton label="Create Ticket" icon="i-lucide-plus" color="primary" />
      </CreateTicketModal>
    </div>

    <UCard>
      <UTable
        :data="tickets"
        :columns="columns"
        sticky="header"
        @select="onSelect"
        :ui="{ tbody: 'cursor-pointer' }"
      />
    </UCard>
  </div>
</template>
