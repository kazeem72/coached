package ng.com.justjava.coached.services;

import ng.com.justjava.coached.entity.User;
import ng.com.justjava.coached.entity.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    //String accessToken = null;
    public Map getAdminAccessToken(){

        Map<String,String> parmMaps= new HashMap<>();
        parmMaps.put("client_id",clientId);
        parmMaps.put("client_secret",clientSecret);
        parmMaps.put("grant_type",grantType);
        Map accessToken = keycloakFeignClient.getAdminAccessToken(parmMaps);//,clientSecret,GRANTTYPE);
        return accessToken ;

    }

    public List<User> getUsers(){
        Map tokens = getAdminAccessToken();

        String accessToken ="Bearer "+ tokens.get("access_token");


        System.out.println(" The pulled out access token ==="+ accessToken);
        List<User> users = new ArrayList<>();
        try {
            users = keycloakFeignClient.getUsers(accessToken);
        }catch (Exception exc){
            exc.printStackTrace();
        }
        return users;
    }
    public void createUser(User user){


        //if(accessToken==null) {
            Map tokens = getAdminAccessToken();
            String accessToken = (String) tokens.get("access_token");

            accessToken = "Bearer " + tokens.get("access_token");
        //}
        System.out.println(" The pulled out access token ==="+ accessToken + " and user==="+user);

        try {
             User user1 = keycloakFeignClient.createUser(accessToken, user);
            System.out.println(" The Created User Here==="+user1);

        }catch (Exception exc){
            exc.printStackTrace();
        }
    }


}
