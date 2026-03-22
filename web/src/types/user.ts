export enum Role {
  User = "user",
  Admin = "admin",
}

export interface AuthUser {
  username: string;
  email: string;
  roles: (Role | string)[];
}
