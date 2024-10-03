package ng.com.justjava.coached.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    String username;
    String firstName;
    String lastName;
    String email;
    Boolean enabled;
    //String userType;
    //List<String> interestArea;
    Map attributes;
    List<Map> credentials;
}
