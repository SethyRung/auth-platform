<script setup lang="ts">
import { ref, reactive } from "vue";
import * as z from "zod";

import type { FormSubmitEvent } from "@nuxt/ui";
import type { KeycloakData } from "@/types/keycloak";

const props = defineProps<{
  kcData: KeycloakData;
}>();

const schema = z
  .object({
    firstName: z.string("First name is required").min(1, "First name is required"),
    lastName: z.string("Last name is required").min(1, "Last name is required"),
    email: z.email("Invalid email address"),
    username: z.string("Username is required").min(1, "Username is required"),
    password: z.string("Must be at least 8 characters").min(8, "Must be at least 8 characters"),
    passwordConfirm: z
      .string("Must be at least 8 characters")
      .min(8, "Must be at least 8 characters"),
  })
  .refine((data) => data.password === data.passwordConfirm, {
    message: "Passwords do not match",
    path: ["passwordConfirm"],
  });

type Schema = z.output<typeof schema>;

const state = reactive<Partial<Schema>>({
  firstName: undefined,
  lastName: undefined,
  email: undefined,
  username: undefined,
  password: undefined,
  passwordConfirm: undefined,
});

const loading = ref(false);

function onSubmit(event: FormSubmitEvent<Schema>) {
  loading.value = true;

  const { data } = event;

  const form = document.createElement("form");
  form.method = "POST";
  form.action = props.kcData.urls.registerAction;

  Object.entries(data).forEach(([key, value]) => {
    const input = document.createElement("input");
    input.type = "hidden";
    input.name = key === "passwordConfirm" ? "password-confirm" : key;
    input.value = String(value);
    form.appendChild(input);
  });

  document.body.appendChild(form);
  form.submit();

  loading.value = false;
}
</script>

<template>
  <UCard class="w-full max-w-md">
    <template #header>
      <div class="flex items-center gap-2">
        <UIcon name="i-lucide-life-buoy" :size="20" />

        <h1 class="text-xl font-semibold">{{ kcData.realm.displayName }}</h1>
      </div>
      <p class="mt-2 text-toned">Sign up for {{ kcData.realm.displayName }}</p>
    </template>

    <UAlert
      v-if="kcData.errors.hasError"
      color="error"
      variant="soft"
      title="Please fix the errors below"
      class="mb-4"
    />

    <UForm :schema="schema" :state="state" class="space-y-6" @submit="onSubmit">
      <UFormField :label="kcData.messages.firstName" name="firstName" required>
        <UInput v-model="state.firstName" type="text" size="xl" class="w-full" />
      </UFormField>

      <UFormField :label="kcData.messages.lastName" name="lastName" required>
        <UInput v-model="state.lastName" type="text" size="xl" class="w-full" />
      </UFormField>

      <UFormField :label="kcData.messages.email" name="email" required>
        <UInput v-model="state.email" type="email" size="xl" autocomplete="email" class="w-full" />
      </UFormField>

      <UFormField
        v-if="!kcData.meta.registrationEmailAsUsername"
        :label="kcData.messages.username"
        name="username"
        required
      >
        <UInput
          v-model="state.username"
          type="text"
          size="xl"
          autocomplete="username"
          class="w-full"
        />
      </UFormField>

      <UFormField :label="kcData.messages.password" name="password" required>
        <UInput
          v-model="state.password"
          type="password"
          size="xl"
          autocomplete="new-password"
          class="w-full"
        />
      </UFormField>

      <UFormField :label="kcData.messages.passwordConfirm" name="passwordConfirm" required>
        <UInput
          v-model="state.passwordConfirm"
          type="password"
          size="xl"
          autocomplete="new-password"
          class="w-full"
        />
      </UFormField>

      <UButton
        :label="kcData.messages.doRegister"
        size="xl"
        type="submit"
        block
        :loading="loading"
      />

      <div class="text-center text-sm">
        <span class="text-gray-600 dark:text-gray-400">Already have an account?</span>
        <a
          :href="kcData.urls.loginUrl"
          class="ml-1 font-medium text-primary-600 hover:text-primary-500 dark:text-primary-400 underline"
        >
          Sign In
        </a>
      </div>
    </UForm>
  </UCard>
</template>
