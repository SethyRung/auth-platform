<#import "vue-template.ftl" as layout>
<@layout.vueLayout ; section>
  <#if section = "kcData">
    <script>
      window.$kcData = {
        page: "register",
        realm: {
          name: "${realm.name}",
          displayName: "${realm.displayName!'Auth Platform'}"
        },
        meta: {
          registrationEmailAsUsername: ${realm.registrationEmailAsUsername?c}
        },
        messages: {
          firstName: "${msg('firstName')?js_string}",
          lastName: "${msg('lastName')?js_string}",
          email: "${msg('email')?js_string}",
          username: "${msg('username')?js_string}",
          password: "${msg('password')?js_string}",
          passwordConfirm: "${msg('passwordConfirm')?js_string}",
          doRegister: "${msg('doRegister')?js_string}",
        },
        errors: {
          hasError: <#if messagesPerField.existsError('firstName','lastName','email','username','password','password-confirm')>true<#else>false</#if>,
          firstName: "<#if messagesPerField.existsError('firstName')>${kcSanitize(messagesPerField.get('firstName'))?no_esc?js_string}</#if>",
          lastName: "<#if messagesPerField.existsError('lastName')>${kcSanitize(messagesPerField.get('lastName'))?no_esc?js_string}</#if>",
          email: "<#if messagesPerField.existsError('email')>${kcSanitize(messagesPerField.get('email'))?no_esc?js_string}</#if>",
          username: "<#if messagesPerField.existsError('username')>${kcSanitize(messagesPerField.get('username'))?no_esc?js_string}</#if>",
          password: "<#if messagesPerField.existsError('password')>${kcSanitize(messagesPerField.get('password'))?no_esc?js_string}</#if>",
          passwordConfirm: "<#if messagesPerField.existsError('password-confirm')>${kcSanitize(messagesPerField.get('password-confirm'))?no_esc?js_string}</#if>"
        },
        urls: {
          registerAction: "${url.registrationAction?js_string}",
          loginUrl: "${url.loginUrl?js_string}"
        }
      };
    </script>
  </#if>
</@layout.vueLayout>
