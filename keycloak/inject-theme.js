const fs = require("fs/promises");
const path = require("path");

const INJECT_DATA_TAG = "<!-- KC_INJECT_DATA -->";
const INJECT_DATA_REPLACE = '<#nested "kcData">';
const ASSETS_PATH_REGEX = /\/assets\//g;
const ASSETS_PATH_REPLACE = "${url.resourcesPath}/vue/";
const FAVICON_PATH_REGEX = /href="\/favicon\.ico"/g;
const FAVICON_PATH_REPLACE = 'href="${url.resourcesPath}/vue/favicon.ico"';

async function main() {
  const distPath = path.resolve(__dirname, "..", "web/dist");
  const themePath = path.resolve(__dirname, "themes/auth-platform/login");

  const indexHtml = await fs.readFile(path.join(distPath, "index-keycloak.html"), "utf-8");

  // Transform to FreeMarker template
  const vueTemplateFtl = `<#macro vueLayout>
${indexHtml
  .replace(INJECT_DATA_TAG, INJECT_DATA_REPLACE)
  .replace(ASSETS_PATH_REGEX, ASSETS_PATH_REPLACE)
  .replace(FAVICON_PATH_REGEX, FAVICON_PATH_REPLACE)}
</#macro>`;

  await fs.writeFile(path.join(themePath, "vue-template.ftl"), vueTemplateFtl);

  // Copy assets
  const distAssetsPath = path.join(distPath, "assets");
  const themeAssetsPath = path.join(themePath, "resources/vue");

  await fs.rm(themeAssetsPath, { recursive: true, force: true });
  await fs.mkdir(themeAssetsPath, { recursive: true });
  await fs.cp(distAssetsPath, themeAssetsPath, { recursive: true });

  // Copy favicon
  const faviconSrc = path.join(distPath, "favicon.ico");
  const faviconDest = path.join(themeAssetsPath, "favicon.ico");
  await fs.copyFile(faviconSrc, faviconDest);
}

main().catch(console.error);
