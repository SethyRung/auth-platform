<script setup lang="ts">
import { computed, h, ref, resolveComponent } from "vue";
import { useRouter } from "vue-router";

import type { NavigationMenuItem, TableColumn } from "@nuxt/ui";
import type { Ticket } from "@/types/ticket";

import { useAuthStore } from "@/stores/auth";

const UBadge = resolveComponent("UBadge");
const router = useRouter();
const authStore = useAuthStore();

const search = ref("");
const isCreateModalOpen = ref(false);

const newTicket = ref({
  title: "",
  description: "",
  priority: "MEDIUM" as const,
});

const priorityOptions: {
  label: string;
  value: "LOW" | "MEDIUM" | "HIGH" | "URGENT";
}[] = [
  { label: "Low", value: "LOW" },
  { label: "Medium", value: "MEDIUM" },
  { label: "High", value: "HIGH" },
  { label: "Urgent", value: "URGENT" },
];

function openCreateModal() {
  isCreateModalOpen.value = true;
}

function closeCreateModal() {
  isCreateModalOpen.value = false;
  newTicket.value = {
    title: "",
    description: "",
    priority: "MEDIUM",
  };
}

async function createTicket() {
  // TODO: Implement API call to create ticket
  console.log("Creating ticket:", newTicket.value);
  closeCreateModal();
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
    cell: ({ row }) =>
      h("span", { class: "line-clamp-1" }, row.original.description ?? ""),
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
    cell: ({ row }) => new Date(row.original.createdAt).toLocaleDateString(),
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
  <div>
    <div class="mb-4 flex items-center justify-between">
      <div>
        <h2 class="text-lg font-semibold">My Tickets</h2>
        <p class="text-sm text-muted-foreground">Manage your support tickets</p>
      </div>

      <UButton
        label="Create Ticket"
        icon="i-lucide-plus"
        color="primary"
        @click="openCreateModal"
      />
    </div>

    <UCard>
      <UTable :data="tickets" :columns="columns" sticky="header" />
    </UCard>

    <UModal v-model:open="isCreateModalOpen" title="Create New Ticket">
      <template #body>
        <div class="space-y-4">
          <UFormField label="Title" required>
            <UInput
              v-model="newTicket.title"
              placeholder="Enter ticket title"
              class="w-full"
            />
          </UFormField>

          <UFormField label="Description">
            <UTextarea
              v-model="newTicket.description"
              placeholder="Enter ticket description"
              :rows="4"
              class="w-full"
            />
          </UFormField>

          <UFormField label="Priority" required>
            <USelectMenu
              v-model="newTicket.priority"
              :options="priorityOptions"
              option-attribute="label"
              value-attribute="value"
              placeholder="Select priority"
              class="w-full"
            />
          </UFormField>
        </div>
      </template>

      <template #footer>
        <div class="flex justify-end gap-2">
          <UButton
            label="Cancel"
            variant="ghost"
            color="neutral"
            @click="closeCreateModal"
          />
          <UButton
            label="Create Ticket"
            color="primary"
            :disabled="!newTicket.title"
            @click="createTicket"
          />
        </div>
      </template>
    </UModal>
  </div>
</template>
