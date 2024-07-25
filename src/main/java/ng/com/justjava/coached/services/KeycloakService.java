package ng.com.justjava.coached.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;


import java.util.HashMap;
import java.util.Map;

@Service
public class KeycloakService {
    @Autowired
    DiscoveryClient discoveryClient;

    @Autowired
    KeycloakFeignClient keycloakFeignClient;

    @Value("${keycloak.master.access.clientID}")
    String clientId;

    @Value("${keycloak.master.access.clientSecret}")
    String clientSecret;

    @Value("${keycloak.master.access.granttype}")
    String grantType;

    public Map getAdminAccessToken(){

        Map<String,String> parmMaps= new HashMap<>();
        parmMaps.put("client_id",clientId);
        parmMaps.put("client_secret",clientSecret);
        parmMaps.put("grant_type",grantType);
        Map accessToken = keycloakFeignClient.getAdminAccessToken(parmMaps);//,clientSecret,GRANTTYPE);
        System.out.println(" My Long Awaited Access Token "
                +accessToken);
        return accessToken ;

    }


}
