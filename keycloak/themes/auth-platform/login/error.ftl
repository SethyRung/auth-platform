<#import "vue-template.ftl" as layout>
<@layout.vueLayout ; section>
  <#if section = "kcData">
    <script>
      window.$kcData = {
        page: "error",
        realm: {
          name: "${realm.name}",
          displayName: "${realm.displayName!'Auth Platform'}"
        },
        meta: {},
        messages: {
          backToLogin: "${msg('backToLogin')?js_string}"
        },
        errors: {
          hasError: true,
          title: <#if message?has_content>"${message.summary?js_string}"<#else>null</#if>,
          description: <#if message?has_content && message.description?has_content>"${message.description?js_string}"<#else>null</#if>
        },
        urls: {
          loginUrl: "${url.loginUrl?js_string}"
        }
      };
    </script>
  </#if>
</@layout.vueLayout>
