import "./assets/main.css";

import { createApp } from "vue";

import App from "./AppKeycloak.vue";
import ui from "@nuxt/ui/vue-plugin";

const app = createApp(App);

app.use(ui);

app.mount("#app");
