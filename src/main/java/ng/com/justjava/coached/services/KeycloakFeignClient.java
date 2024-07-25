package ng.com.justjava.coached.services;

import ng.com.justjava.coached.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;


//{{keycloak_url}}/admin/realms/{{realm}}/users
//http://localhost/oauth2/realms/master/protocol/openid-connect/token
@FeignClient(name="KeycloakFeignClient",url="http://localhost")
public interface KeycloakFeignClient {

    @PostMapping(path = "/oauth2/realms/master/protocol/openid-connect/token",
    consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    Map getAdminAccessToken(Map<String,?> paramMap);

    @GetMapping("/oauth2/admin/realms/coached/users")
    public List<User> getUsers();
}
