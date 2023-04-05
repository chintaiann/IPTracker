import Keycloak from "keycloak-js";

const keycloak = new Keycloak({
 url: "http://localhost:8080",
 realm: "springdemo",
 clientId: "reactclient",
//  onLoad: 'check-sso',
 silentCheckSsoRedirectUri: window.location.origin + '/silent-check-sso.html',

});
export default keycloak