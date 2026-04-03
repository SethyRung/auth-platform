<#import "vue-template.ftl" as layout>
<@layout.vueLayout ; section>
  <#if section = "kcData">
    <script>
      window.$kcData = {
        page: "update-password",
        realm: {
          name: "${realm.name}",
          displayName: "${realm.displayName!'Auth Platform'}"
        },
        messages: {
          passwordNew: "${msg('passwordNew')?js_string}",
          passwordConfirm: "${msg('passwordConfirm')?js_string}",
          doSubmit: "${msg('doSubmit')?js_string}"
        },
        errors: {
          hasError: <#if messagesPerField.existsError('password','password-confirm')>true<#else>false</#if>,
          password: "<#if messagesPerField.existsError('password')>${kcSanitize(messagesPerField.get('password'))?no_esc?js_string}</#if>",
          passwordConfirm: "<#if messagesPerField.existsError('password-confirm')>${kcSanitize(messagesPerField.get('password-confirm'))?no_esc?js_string}</#if>"
        },
        urls: {
          updatePasswordAction: "${url.loginAction?js_string}"
        }
      };
    </script>
  </#if>
</@layout.vueLayout>
