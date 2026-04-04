<script setup lang="ts">
import { ref, reactive } from "vue";
import * as z from "zod";
import { kebabCase } from "scule";

import type { FormSubmitEvent } from "@nuxt/ui";
import type { KeycloakData } from "@/types/keycloak";

const props = defineProps<{
  kcData: KeycloakData;
}>();

const schema = z
  .object({
    passwordNew: z.string("Must be at least 8 characters").min(8, "Must be at least 8 characters"),
    passwordConfirm: z
      .string("Must be at least 8 characters")
      .min(8, "Must be at least 8 characters"),
  })
  .refine((data) => data.passwordNew === data.passwordConfirm, {
    message: "Passwords do not match",
    path: ["passwordConfirm"],
  });

type Schema = z.output<typeof schema>;

const state = reactive<Partial<Schema>>({
  passwordNew: undefined,
  passwordConfirm: undefined,
});

const loading = ref(false);

function onSubmit(event: FormSubmitEvent<Schema>) {
  loading.value = true;

  const { data } = event;

  const form = document.createElement("form");
  form.method = "POST";
  form.action = props.kcData.urls.updatePasswordAction;

  Object.entries(data).forEach(([key, value]) => {
    const input = document.createElement("input");
    input.type = "hidden";
    input.name = kebabCase(key);
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
      <p class="mt-2 text-toned">Please enter your new password</p>
    </template>

    <UAlert
      v-if="kcData.errors.hasError"
      icon="i-lucide:alert-triangle"
      :title="String(kcData.errors.password || 'An error occurred')"
      color="error"
      variant="soft"
      class="mb-4"
    />

    <UForm :schema="schema" :state="state" class="space-y-6" @submit="onSubmit">
      <UFormField :label="kcData.messages.passwordNew" name="passwordNew" required>
        <UInput v-model="state.passwordNew" type="password" size="xl" class="w-full" />
      </UFormField>

      <UFormField :label="kcData.messages.passwordConfirm" name="passwordConfirm" required>
        <UInput v-model="state.passwordConfirm" type="password" size="xl" class="w-full" />
      </UFormField>

      <UButton :label="kcData.messages.doSubmit" size="xl" type="submit" block :loading="loading" />
    </UForm>
  </UCard>
</template>
