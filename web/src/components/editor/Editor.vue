<script setup lang="ts">
import { computed, ref } from "vue";

import type {
  EditorToolbarItem,
  EditorMentionMenuItem,
  EditorEmojiMenuItem,
  DropdownMenuItem,
  EditorSuggestionMenuItem,
} from "@nuxt/ui";
import type { Editor, JSONContent } from "@tiptap/vue-3";
import { upperFirst } from "scule";
import { mapEditorItems } from "@nuxt/ui/utils/editor";
import { Emoji, gitHubEmojis } from "@tiptap/extension-emoji";
import { TextAlign } from "@tiptap/extension-text-align";
import { CodeBlockShiki } from "tiptap-extension-code-block-shiki";
import EditorLinkPopover from "./EditorLinkPopover.vue";

const value = ref("");

const bubbleToolbarItems = computed(
  () =>
    [
      [
        {
          label: "Turn into",
          trailingIcon: "i-lucide-chevron-down",
          activeColor: "neutral",
          activeVariant: "ghost",
          tooltip: { text: "Turn into" },
          content: {
            align: "start",
          },
          ui: {
            label: "text-xs",
          },
          items: [
            {
              type: "label",
              label: "Turn into",
            },
            {
              kind: "paragraph",
              label: "Paragraph",
              icon: "i-lucide-type",
            },
            {
              kind: "heading",
              level: 1,
              icon: "i-lucide-heading-1",
              label: "Heading 1",
            },
            {
              kind: "heading",
              level: 2,
              icon: "i-lucide-heading-2",
              label: "Heading 2",
            },
            {
              kind: "heading",
              level: 3,
              icon: "i-lucide-heading-3",
              label: "Heading 3",
            },
            {
              kind: "heading",
              level: 4,
              icon: "i-lucide-heading-4",
              label: "Heading 4",
            },
            {
              kind: "bulletList",
              icon: "i-lucide-list",
              label: "Bullet List",
            },
            {
              kind: "orderedList",
              icon: "i-lucide-list-ordered",
              label: "Ordered List",
            },
            {
              kind: "blockquote",
              icon: "i-lucide-text-quote",
              label: "Blockquote",
            },
            {
              kind: "codeBlock",
              icon: "i-lucide-square-code",
              label: "Code Block",
            },
          ],
        },
      ],
      [
        {
          kind: "mark",
          mark: "bold",
          icon: "i-lucide-bold",
          tooltip: { text: "Bold" },
        },
        {
          kind: "mark",
          mark: "italic",
          icon: "i-lucide-italic",
          tooltip: { text: "Italic" },
        },
        {
          kind: "mark",
          mark: "underline",
          icon: "i-lucide-underline",
          tooltip: { text: "Underline" },
        },
        {
          kind: "mark",
          mark: "strike",
          icon: "i-lucide-strikethrough",
          tooltip: { text: "Strikethrough" },
        },
        {
          kind: "mark",
          mark: "code",
          icon: "i-lucide-code",
          tooltip: { text: "Code" },
        },
      ],
      [
        {
          slot: "link" as const,
          icon: "i-lucide-link",
        },
      ],
      [
        {
          icon: "i-lucide-align-justify",
          tooltip: { text: "Text Align" },
          content: {
            align: "end",
          },
          items: [
            {
              kind: "textAlign",
              align: "left",
              icon: "i-lucide-align-left",
              label: "Align Left",
            },
            {
              kind: "textAlign",
              align: "center",
              icon: "i-lucide-align-center",
              label: "Align Center",
            },
            {
              kind: "textAlign",
              align: "right",
              icon: "i-lucide-align-right",
              label: "Align Right",
            },
            {
              kind: "textAlign",
              align: "justify",
              icon: "i-lucide-align-justify",
              label: "Align Justify",
            },
          ],
        },
      ],
    ] satisfies EditorToolbarItem[][],
);

const selectedNode = ref<{ node: JSONContent; pos: number }>();

function handleItems(editor: Editor): DropdownMenuItem[][] {
  if (!selectedNode.value?.node?.type) {
    return [];
  }

  return mapEditorItems(editor, [
    [
      {
        type: "label",
        label: upperFirst(selectedNode.value.node.type),
      },
      {
        label: "Turn into",
        icon: "i-lucide-repeat-2",
        children: [
          { kind: "paragraph", label: "Paragraph", icon: "i-lucide-type" },
          { kind: "heading", level: 1, label: "Heading 1", icon: "i-lucide-heading-1" },
          { kind: "heading", level: 2, label: "Heading 2", icon: "i-lucide-heading-2" },
          { kind: "heading", level: 3, label: "Heading 3", icon: "i-lucide-heading-3" },
          { kind: "heading", level: 4, label: "Heading 4", icon: "i-lucide-heading-4" },
          { kind: "bulletList", label: "Bullet List", icon: "i-lucide-list" },
          { kind: "orderedList", label: "Ordered List", icon: "i-lucide-list-ordered" },
          { kind: "blockquote", label: "Blockquote", icon: "i-lucide-text-quote" },
          { kind: "codeBlock", label: "Code Block", icon: "i-lucide-square-code" },
        ],
      },
      {
        kind: "clearFormatting",
        pos: selectedNode.value?.pos,
        label: "Reset formatting",
        icon: "i-lucide-rotate-ccw",
      },
    ],
    [
      {
        kind: "duplicate",
        pos: selectedNode.value?.pos,
        label: "Duplicate",
        icon: "i-lucide-copy",
      },
      {
        label: "Copy to clipboard",
        icon: "i-lucide-clipboard",
        onSelect: async () => {
          if (!selectedNode.value) return;

          const pos = selectedNode.value.pos;
          const node = editor.state.doc.nodeAt(pos);
          if (node) {
            await navigator.clipboard.writeText(node.textContent);
          }
        },
      },
    ],
    [
      {
        kind: "moveUp",
        pos: selectedNode.value?.pos,
        label: "Move up",
        icon: "i-lucide-arrow-up",
      },
      {
        kind: "moveDown",
        pos: selectedNode.value?.pos,
        label: "Move down",
        icon: "i-lucide-arrow-down",
      },
    ],
    [
      {
        kind: "delete",
        pos: selectedNode.value?.pos,
        label: "Delete",
        icon: "i-lucide-trash",
      },
    ],
  ]);
}

const suggestionItems = [
  [
    {
      type: "label",
      label: "Style",
    },
    {
      kind: "paragraph",
      label: "Paragraph",
      icon: "i-lucide-type",
    },
    {
      kind: "heading",
      level: 1,
      label: "Heading 1",
      icon: "i-lucide-heading-1",
    },
    {
      kind: "heading",
      level: 2,
      label: "Heading 2",
      icon: "i-lucide-heading-2",
    },
    {
      kind: "heading",
      level: 3,
      label: "Heading 3",
      icon: "i-lucide-heading-3",
    },
    {
      kind: "bulletList",
      label: "Bullet List",
      icon: "i-lucide-list",
    },
    {
      kind: "orderedList",
      label: "Numbered List",
      icon: "i-lucide-list-ordered",
    },
    {
      kind: "blockquote",
      label: "Blockquote",
      icon: "i-lucide-text-quote",
    },
    {
      kind: "codeBlock",
      label: "Code Block",
      icon: "i-lucide-square-code",
    },
  ],
  [
    {
      type: "label",
      label: "Insert",
    },
    {
      kind: "mention",
      label: "Mention",
      icon: "i-lucide-at-sign",
    },
    {
      kind: "emoji",
      label: "Emoji",
      icon: "i-lucide-smile-plus",
    },
    {
      kind: "horizontalRule",
      label: "Horizontal Rule",
      icon: "i-lucide-separator-horizontal",
    },
  ],
] satisfies EditorSuggestionMenuItem[][];

const mentionItems: EditorMentionMenuItem[] = [];

const emojiItems: EditorEmojiMenuItem[] = gitHubEmojis.filter(
  (emoji) => !emoji.name.startsWith("regional_indicator_"),
);
</script>

<template>
  <UEditor
    ref="editorRef"
    v-slot="{ editor, handlers }"
    v-model="value"
    content-type="markdown"
    :extensions="[
      Emoji,
      TextAlign.configure({ types: ['heading', 'paragraph'] }),
      CodeBlockShiki.configure({
        defaultTheme: 'material-theme',
        themes: {
          light: 'material-theme-lighter',
          dark: 'material-theme-palenight',
        },
      }),
    ]"
    placeholder="Write, type '/' for commands..."
    :ui="{ root: 'w-full' }"
  >
    <UEditorToolbar
      :editor="editor"
      :items="bubbleToolbarItems"
      layout="bubble"
      :should-show="
        ({ editor, view, state }) => {
          if (editor.isActive('imageUpload') || editor.isActive('image')) {
            return false;
          }
          const { selection } = state;
          return view.hasFocus() && !selection.empty;
        }
      "
    >
      <template #link>
        <EditorLinkPopover :editor="editor" />
      </template>
    </UEditorToolbar>

    <UEditorSuggestionMenu :editor="editor" :items="suggestionItems" />

    <UEditorMentionMenu :editor="editor" :items="mentionItems" />

    <UEditorEmojiMenu :editor="editor" :items="emojiItems" />

    <UEditorDragHandle
      v-slot="{ ui, onClick }"
      :editor="editor"
      @node-change="selectedNode = $event"
    >
      <UButton
        icon="i-lucide-plus"
        color="neutral"
        variant="ghost"
        size="sm"
        :class="ui.handle()"
        @click="
          (e) => {
            e.stopPropagation();

            const selected = onClick();
            handlers.suggestion?.execute(editor, { pos: selected?.pos }).run();
          }
        "
      />

      <UDropdownMenu
        v-slot="{ open }"
        :modal="false"
        :items="handleItems(editor)"
        :content="{ side: 'left' }"
        :ui="{ content: 'w-48', label: 'text-xs' }"
        @update:open="editor.chain().setMeta('lockDragHandle', $event).run()"
      >
        <UButton
          color="neutral"
          variant="ghost"
          active-variant="soft"
          size="sm"
          icon="i-lucide-grip-vertical"
          :active="open"
          :class="ui.handle()"
        />
      </UDropdownMenu>
    </UEditorDragHandle>
  </UEditor>
</template>

<style>
html.dark .tiptap .shiki,
html.dark .tiptap .shiki span {
  color: var(--shiki-dark) !important;
  background-color: var(--ui-bg-muted) !important;
}
</style>
