import {KeycloakConfig} from 'keycloak-angular';

let keycloakConfig: KeycloakConfig = {
  url: 'http://keycloak.test/auth/',
  realm: 'UserManagement',
  clientId: 'ems-frontend'
};
export const environment = {
  production: true,
  keycloak: keycloakConfig,
};
