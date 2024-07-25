package ng.com.justjava.coached.rest;

import ng.com.justjava.coached.entity.*;
import ng.com.justjava.coached.services.KeycloakService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@CrossOrigin
public class HomeController {

    @Autowired
    SessionRepository sessionRepository;

    @Autowired
    KeycloakService keycloakService;

    @Autowired
    OrganizationRepository organizationRepository;

    @Autowired
    CoacheeRepository coacheeRepository;

    @Autowired
    InvoiceRepository invoiceRepository;

    @Autowired
    CoachRepository coachRepository;

    @GetMapping({"/","/coached"})
    public String index(OAuth2AuthenticationToken oAuth2AuthenticationToken,Model model){


        System.out.println(" The whole oAuth2AuthenticationToken ====="+oAuth2AuthenticationToken);
        String companyName= (String) oAuth2AuthenticationToken.getPrincipal().getAttributes().get("orgName");
        String firstName= (String) oAuth2AuthenticationToken.getPrincipal().getAttributes().get("given_name");
        String lastName= (String) oAuth2AuthenticationToken.getPrincipal().getAttributes().get("family_name");
        String email= (String) oAuth2AuthenticationToken.getPrincipal().getAttributes().get("email");
        String fullName= (String) oAuth2AuthenticationToken.getPrincipal().getAttributes().get("name");

        System.out.println(" The company name==="
                +companyName +"  firstName==="+firstName
                +"  lastName=="+lastName+"  email==="+email);

        Optional<Organization> optionalOrganization=organizationRepository.findByOrgAdmin_Email(email);
        Organization organization=new Organization();
        if(optionalOrganization.isPresent()){
            organization = optionalOrganization.get();
        }else {
            Coachee coachee = new Coachee();
            coachee.setEmail(email);
            coachee.setFullName(fullName);
            coachee.setAdmin(true);
            coachee.setStatus("Joined");

            organization.setOrgAdmin(coachee);
            organization.setName(companyName);
            organization.setEmail(email);
            organization.addCoachee (coachee);

            organization=organizationRepository.save(organization);
        }
                //= new Organization();


        List<Invoice> invoices = invoiceRepository.findByIssueTo(email);
        List<Session> sessions = sessionRepository.
                findByManyToOneAndSessionOwnerOrderByActualDateAsc(true,companyName);

        Long ongoingBookingCount = sessions.stream()
                .filter(session -> session.getSessionStatus()== SessionStatus.INITIATE)
                        .count();

        Long upcomingSessionCount = sessions.stream()
                .filter(session -> session.getSessionStatus()== SessionStatus.SCEDULED)
                .count();
        Long completedCount = sessions.stream()
                .filter(session -> session.getSessionStatus()== SessionStatus.DONE)
                .count();
        model.addAttribute("name",oAuth2AuthenticationToken.getPrincipal().getAttributes().get("name"));
        model.addAttribute("organization",organization);
        model.addAttribute("ongoingBookingCount",ongoingBookingCount);
        model.addAttribute("upcomingSessionCount",upcomingSessionCount);
        model.addAttribute("completedCount",completedCount);
        model.addAttribute("employees",organization.getCoachees().size());
        model.addAttribute("invoices",invoices);
        model.addAttribute("coaches",coachRepository.findAll());


        return "index";
    }

    @GetMapping("/employees")
    public String employees(OAuth2AuthenticationToken oAuth2AuthenticationToken,Model model){

        String email= (String) oAuth2AuthenticationToken.getPrincipal().getAttributes().get("email");

        Optional<Coachee> optionalCoachee=coacheeRepository.findByEmail(email);
        if(optionalCoachee.isPresent() && optionalCoachee.get().getOrganization()!=null){
            model.addAttribute("organization",optionalCoachee.get().getOrganization());
        }

        model.addAttribute("name",oAuth2AuthenticationToken.getPrincipal().getAttributes().get("name"));
        return "employees";
    }
    @GetMapping("/invoices")
    public String invoices(Model model){
        //System.out.println(" The Principal ====="+principal);

        model.addAttribute("princi","demo");
        return "invoices";
    }
    @GetMapping("/session_history")
    public String sessionHistory(Model model){
        //System.out.println(" The Principal ====="+principal);

        model.addAttribute("princi","demo");
        return "session_history";
    }

    @GetMapping("/coachInformation")
    public String coachInformation(Model model){
        //System.out.println(" The Principal ====="+principal);

        model.addAttribute("princi","demo");
        return "coachInformation";
    }
    @GetMapping("/upcoming_session")
    public String upcomingSession(Model model){
        //System.out.println(" The Principal ====="+principal);

        model.addAttribute("princi","demo");
        return "upcoming_session";
    }

}
