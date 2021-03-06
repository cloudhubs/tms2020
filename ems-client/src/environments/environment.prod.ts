import {KeycloakConfig} from 'keycloak-angular';

let keycloakConfig: KeycloakConfig = {
  url: 'https://tcs.ecs.baylor.edu/auth/',
  realm: 'UserManagement',
  clientId: 'ems-frontend'
};
export const environment = {
  production: true,
  keycloak: keycloakConfig,
};
