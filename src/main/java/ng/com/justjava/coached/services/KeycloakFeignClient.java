package ng.com.justjava.coached.services;

import ng.com.justjava.coached.entity.User;
import ng.com.justjava.coached.entity.UserRepresentation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;
import java.util.Map;


//{{keycloak_url}}/admin/realms/{{realm}}/users
//http://localhost/oauth2/realms/master/protocol/openid-connect/token
@FeignClient(name="KeycloakFeignClient",url="https://45306ec7-2747-46cd-b78e-a00a7c6d8bb2.app.skycloak.io")
public interface KeycloakFeignClient {

    @PostMapping(path = "/realms/master/protocol/openid-connect/token",
    consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    Map getAdminAccessToken(Map<String,?> paramMap);

    @GetMapping("/admin/realms/justjavaRealm/users")
    public List<User> getUsers(@RequestHeader(value = "Authorization", required = true) String authorizationHeader);

    @PostMapping("/admin/realms/justjavaRealm/users")
    public User createUser(@RequestHeader(value = "Authorization", required = true)
                                     String authorizationHeader, User user);


}
