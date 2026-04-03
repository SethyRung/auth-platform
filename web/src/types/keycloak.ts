export interface KeycloakData {
  page: "login" | "register" | "forgot-password" | "update-password" | "verify-email";
  realm: {
    name: string;
    displayName: string;
    rememberMe?: boolean;
    resetPasswordAllowed?: boolean;
    registrationAllowed?: boolean;
    registrationEmailAsUsername?: boolean;
  };
  messages: Record<string, string>;
  errors: {
    hasError: boolean;
    [key: string]: unknown;
  };
  urls: Record<string, string>;
}

declare global {
  interface Window {
    $kcData?: KeycloakData;
  }
}
