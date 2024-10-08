package ng.com.justjava.coached.rest;

//import io.camunda.zeebe.client.ZeebeClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SessionController {

    @GetMapping("/upcoming-session")
    public String getComingSession(){
        return "upcoming-session";
    }
}
