import Keycloak from "keycloak-js";

const keycloak = new Keycloak({
 url: "http://localhost:8080/auth",
//  url:"http://keycloak:8080/auth",
 realm: "springdemo",
 clientId: "react_client",
//  onLoad: 'check-sso',
 silentCheckSsoRedirectUri: window.location.origin + '/silent-check-sso.html',

});
export default keycloak