package ng.com.justjava.coached.rest;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ng.com.justjava.coached.entity.*;
import ng.com.justjava.coached.services.KeycloakService;
import ng.com.justjava.coached.util.PdfGenerator;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.context.Context;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

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
    CoachRepository coachRepository;

    @Autowired
    CoacheeRepository coacheeRepository;

    @Autowired
    InvoiceRepository invoiceRepository;

    @Autowired
    SystemParametersRepository systemParametersRepository;


    @GetMapping({"/","/coached"})
    public String index(OAuth2AuthenticationToken oAuth2AuthenticationToken,
            HttpServletRequest request, Model model){


        String entryPage ="index";
        System.out.println(" The whole oAuth2AuthenticationToken ====="+oAuth2AuthenticationToken);

        SystemParameters systemParameters=systemParametersRepository
                                        .findByModule("session").get();
        System.out.println(" The Session Parameters==="+systemParameters.getParameters());

        System.out.println(" The Focus Area==="+systemParameters.getFocusAreas());

        request.getSession(true).setAttribute("firstTime",false);
        request.getSession(true).setAttribute("systemParameters",systemParameters);

/*

        List<String> areaOfInterest=(List<String>) oAuth2AuthenticationToken.getPrincipal().getAttributes().get("interestArea");

        areaOfInterest.forEach(interest-> System.out.println("The Interest Here=="+interest));
*/

        String companyName= (String) oAuth2AuthenticationToken.getPrincipal().getAttributes().get("orgName");
        String firstName= (String) oAuth2AuthenticationToken.getPrincipal().getAttributes().get("given_name");
        String lastName= (String) oAuth2AuthenticationToken.getPrincipal().getAttributes().get("family_name");
        String email= (String) oAuth2AuthenticationToken.getPrincipal().getAttributes().get("email");
        String fullName= (String) oAuth2AuthenticationToken.getPrincipal().getAttributes().get("name");
        String userType= (String) oAuth2AuthenticationToken.getPrincipal().getAttributes().get("userType");


        System.out.println("Entry value The person just logged in now has user type=="+userType);
        String myPicture = "woman pic.png";
        List<String> groups= (List<String>) oAuth2AuthenticationToken.getPrincipal().getAttributes().get("groups");


        Boolean isEZAdmin = false;
        if(groups!=null) {
            isEZAdmin = groups.stream()
                    .anyMatch(group -> group.equalsIgnoreCase("EZ37Admin"));
        }
        List<Session> sessions = sessionRepository.findAll();
        List<Invoice> invoices = invoiceRepository.findByIssueTo(email);


        if(isEZAdmin)
            userType="EZ37 Admin";

        Organization organization=new Organization();

        if("coachee".equalsIgnoreCase(userType) || "Organization Admin".equalsIgnoreCase(userType)){

            Optional<Coachee> optionalCoachee = coacheeRepository.findByEmail(email);

            if(!optionalCoachee.isPresent()) {


                entryPage = "coacheeComplete";
                request.getSession(true).setAttribute("firstTime",true);


            }else {
                System.out.println("===========================================" +
                        "============================================== The optionalCoachee=="
                        +optionalCoachee.get().getFullName() + " and status==="+optionalCoachee.get().getStatus());

                Coachee coachee=optionalCoachee.get();
                System.out.println(" The Status of Coachee login in here==="+coachee.getStatus());
                if("Invited".equalsIgnoreCase(coachee.getStatus())){
                    coachee.setStatus("Joined");
                    coacheeRepository.save(coachee);
                }
                System.out.println(" Is this an employee =="+optionalCoachee.get().getEmployee()
                + " the email=="+email+" the fullname==="+optionalCoachee.get().getFullName());
                if(!optionalCoachee.get().getEmployee()){
                    userType="Direct Coachee";
                }
                myPicture = !Strings.isEmpty(optionalCoachee.get().getMyPicture())?
                        optionalCoachee.get().getMyPicture():myPicture;
                if(optionalCoachee.get().getOrganization() != null){
                    organization=optionalCoachee.get().getOrganization();
                    sessions = sessions.stream().filter(session ->
                            session.getSessionOwner()
                                    .equalsIgnoreCase(
                                            optionalCoachee.get().getOrganization().getEmail()))
                                    .collect(Collectors.toList());

                }else {
                    sessions = sessions.stream().filter(session ->
                            session.getSessionOwner()
                                    .equalsIgnoreCase(
                                            optionalCoachee.get().getEmail()))
                            .collect(Collectors.toList());

                }
            }

/*
            Optional<Organization> optionalOrganization=organizationRepository.findByOrgAdmin_Email(email);

            if(optionalOrganization.isPresent()){
                organization = optionalOrganization.get();
            }else{
                Coachee coachee = new Coachee();
                coachee.setEmail(email);
                coachee.setFullName(fullName);
                coachee.setAdmin(true);
                coachee.setStatus("Joined");

                organization.setOrgAdmin(coachee);
                organization.setName(companyName !=null?companyName:email);
                organization.setEmail(email);
                organization.addCoachee (coachee);

                organization=organizationRepository.save(organization);
            }
*/

        }if("coach".equalsIgnoreCase(userType)){
            Optional<Coach> optionalCoach = coachRepository.findByEmail(email);

            if(!optionalCoach.isPresent() ) {
                entryPage = "coachComplete";
                request.getSession(true).setAttribute("firstTime",true);
            }else {
                myPicture = !StringUtils.isEmpty(optionalCoach.get().getMyPicture())
                ?optionalCoach.get().getMyPicture():myPicture;
                sessions = sessions.stream().filter
                        (session -> session.getCoach()
                                .getId()==optionalCoach.get().getId())
                        .collect(Collectors.toList());
            }

        }

        System.out.println(" The person just logged in now has user type=="+userType);

        request.getSession(true).setAttribute("userType",userType);
        request.getSession(true).setAttribute("myPicture",myPicture);
        request.getSession(true).setAttribute("parameters",systemParameters.getParameters());

        //model.addAttribute("myPicture",myPicture);


        System.out.println(" The company name==="
                +companyName +"  firstName==="+firstName
                +"  lastName=="+lastName+"  email==="+email + " userType==="+userType);







                //= new Organization();

        List<Coach> allCoaches=coachRepository.findAll();

        List<Coachee> allCoachees=coacheeRepository.findAll();

        List<Organization> organizations = organizationRepository.findAll();

        invoices.forEach(invoice -> System.out.println(" Invoice Iterating==="+invoice.getInvoiceID()));

        invoices.forEach(invoice -> System.out.println(" Invoice Coach here==="
                +invoice.getSession().getCoach().getFullName()));

        sessions.forEach(session -> System.out.println(" Session ==="
                +session.getSessionOwner() +" the id==="+session.getId()));

        System.out.println(" Organization ==="+organization.getName());


        Long ongoingBookingCount = sessions.stream()
                .filter(session -> session.getSessionStatus()== SessionStatus.INITIATE)
                        .count();

        Long upcomingSessionCount = sessions.stream()
                .filter(session -> session.getSessionStatus()==SessionStatus.SCEDULED)
                .flatMap(sess->sess.getSchedules().stream())
                .count();

/*        Long upcomingSessionCount = sessions.stream()
                .flatMap(session -> session.getSchedules().stream()).count();*/

        Integer awaitingVerification = coachRepository.findByVerifyFalse().size();
        System.out.println(" The pulled out size====="+upcomingSessionCount);

        Long completedCount = sessions.stream()
                .filter(session -> session.getSessionStatus()== SessionStatus.DONE)
                .count();


        model.addAttribute("name",oAuth2AuthenticationToken.getPrincipal().getAttributes().get("name"));
        model.addAttribute("email",oAuth2AuthenticationToken.getPrincipal().getAttributes().get("email"));
        model.addAttribute("organization",organization);
        model.addAttribute("ongoingBookingCount",ongoingBookingCount);
        model.addAttribute("upcomingSessionCount",upcomingSessionCount);
        model.addAttribute("awaitingVerification",awaitingVerification);
        model.addAttribute("completedCount",completedCount);
        model.addAttribute("remainingSession",(sessions.size()-completedCount));
        model.addAttribute("employees",organization.getCoachees().size());
        model.addAttribute("invoices",invoices);
        model.addAttribute("coachesSize",allCoaches.size());
        model.addAttribute("coacheesSize",allCoachees.size());
        model.addAttribute("organizationSize",organizations.size());

        model.addAttribute("coaches",coachRepository.findByVerifyTrue());
        model.addAttribute("unVerifiedCoaches",coachRepository.findByVerifyFalse());

        model.addAttribute("userType",userType);


        return "/"+entryPage;
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request){


        try {
            request.logout();
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }
        return "/index";
    }
    @GetMapping("/employees")
    public String employees(OAuth2AuthenticationToken oAuth2AuthenticationToken,
                            @RequestParam(name = "keyword",required = false) String keyword,
                            Model model){

        String email= (String) oAuth2AuthenticationToken.getPrincipal().getAttributes().get("email");

        System.out.println(" The Keyword sent here==="+keyword);

        Optional<Coachee> optionalCoachee=coacheeRepository.findByEmail(email);
        if(optionalCoachee.isPresent() && optionalCoachee.get().getOrganization()!=null){
            model.addAttribute("organization",optionalCoachee.get().getOrganization());
            List<Coachee> coachees=optionalCoachee.get().getOrganization().getCoachees();
            if(keyword!=null)
                coachees = coachees.stream().filter(coachee -> coachee.getFullName().toLowerCase().contains(keyword.toLowerCase())
                    ||coachee.getEmail().contains(keyword)).collect(Collectors.toList());

            model.addAttribute("employees",coachees);
        }

        //System.out.println(" Access Token "+keycloakService.getAdminAccessToken());

        //List<User> users = keycloakService.getUsers();

        //System.out.println(" Keycloak users ==== "+users);
        model.addAttribute("name",
                oAuth2AuthenticationToken.getPrincipal().getAttributes().get("name"));
        return "/employees";
    }
    @GetMapping("/searchEmployees")
    public String searchEmployees(OAuth2AuthenticationToken oAuth2AuthenticationToken,
                            @RequestParam("keyword") String keyword,
                            Model model){

        String email= (String) oAuth2AuthenticationToken.getPrincipal().getAttributes().get("email");



        model.addAttribute("name",oAuth2AuthenticationToken.getPrincipal().getAttributes().get("name"));
        return "/employees";
    }
    @GetMapping("/employees/{employeeID}")
    public String employee(OAuth2AuthenticationToken oAuth2AuthenticationToken,
                           @PathVariable Long employeeID,
                           @ModelAttribute EmployeeDTO employeeDTO , Model model){

        String email= (String) oAuth2AuthenticationToken.getPrincipal().getAttributes().get("email");

        model.addAttribute("employeeDTO", new EmployeeDTO());
        model.addAttribute("name",oAuth2AuthenticationToken.getPrincipal().getAttributes().get("name"));
        return "/employee";
    }
    @PostMapping("/employees")
    public String addEmployee(OAuth2AuthenticationToken oAuth2AuthenticationToken,
                           @ModelAttribute EmployeeDTO employeeDTO , Model model){

        System.out.println(" The employee posted ===="+employeeDTO);
        String email= (String) oAuth2AuthenticationToken.getPrincipal().getAttributes().get("email");
        String orgName= (String) oAuth2AuthenticationToken.getPrincipal().getAttributes().get("orgName");

        Organization organization = organizationRepository.findByOrgAdmin_Email(email).get();
        //List<String> interestArea = (List<String>) oAuth2AuthenticationToken.getPrincipal().getAttributes().get("interestArea");
        Coachee coachee = new Coachee();
        coachee.setEmail(employeeDTO.getEmail());
        coachee.setAdmin(false);
        coachee.setEmployee(true);
        coachee.setOrganization(organization);
        coachee.setStatus("Invited");
        coachee.setFullName(employeeDTO.getFirstName() +" "+ employeeDTO.getLastName());
        coacheeRepository.save(coachee);

        // Create User inside the Keycloak


        User user = new User();
        user.setEmail(coachee.getEmail());
        user.setFirstName(employeeDTO.getFirstName());
        user.setLastName(employeeDTO.getLastName());
        user.setEnabled(true);

        //"type":"password","value":"abc123","temporary":false
        List<Map> credentials = new ArrayList<Map>();
        Map credential = new HashMap();

        credential.put("type","password");
        credential.put("value", "password");
        credential.put("temporary", true);

        credentials.add(credential);

        HashMap<String,Object> attributes = new HashMap<String,Object>();
        attributes.put("userType","coachee");
        attributes.put("orgName",orgName);
        //attributes.put("interestArea",interestArea);
        user.setAttributes(attributes);
        user.setCredentials(credentials);

        keycloakService.createUser(user);

        model.addAttribute("employeeDTO", new EmployeeDTO());
        model.addAttribute("name",oAuth2AuthenticationToken.getPrincipal().getAttributes().get("name"));
        return "redirect:/employees";
    }

    @GetMapping("/session_history")
    public String sessionHistory(OAuth2AuthenticationToken oAuth2AuthenticationToken,Model model){
        //System.out.println(" The Principal ====="+principal);
        String userType= (String) oAuth2AuthenticationToken.getPrincipal().getAttributes().get("userType");
        String email= (String) oAuth2AuthenticationToken.getPrincipal().getAttributes().get("email");
        List<String> groups= (List<String>) oAuth2AuthenticationToken.getPrincipal().getAttributes().get("groups");
        Boolean isEZAdmin = groups.stream()
                .anyMatch(group -> group.equalsIgnoreCase("EZ37Admin"));

        List<Session> sessions = sessionRepository.findBySessionStatus(SessionStatus.DONE);
        if(("coachee".equalsIgnoreCase(userType) ||
                "Organization Admin".equalsIgnoreCase(userType)) && !isEZAdmin){
            sessions = sessions.stream().filter(session ->
                    email.equalsIgnoreCase(session.getSessionOwner()))
                    .collect(Collectors.toList());

        }
        if("coach".equalsIgnoreCase(userType)){
            sessions=sessions.stream().filter(session ->
                    email.equalsIgnoreCase(session.getCoach().getEmail()))
                    .collect(Collectors.toList());
         }
        model.addAttribute("sessions",sessions);
        return "/session_history";
    }

    @GetMapping("/coachInformation")
    public String coachInformation(Model model){
        //System.out.println(" The Principal ====="+principal);

        model.addAttribute("princi","demo");
        return "/coachInformation";
    }
    @GetMapping("/upcoming_session")
    public String upcomingSession(OAuth2AuthenticationToken oAuth2AuthenticationToken,Model model){
        //System.out.println(" The Principal ====="+principal);
        String owner= (String) oAuth2AuthenticationToken.getPrincipal().getAttributes().get("orgName");
        String email= (String) oAuth2AuthenticationToken.getPrincipal().getAttributes().get("email");
        String userType= (String) oAuth2AuthenticationToken.getPrincipal().getAttributes().get("userType");


        List<Session> sessions = new ArrayList<>();

        if("coachee".equalsIgnoreCase(userType) || "Organization Admin".equalsIgnoreCase(userType)){

            Coachee coachee = coacheeRepository.findByEmail(email).get();
            if(coachee.getEmployee())
                owner = coachee.getOrganization().getEmail();
            else owner = coachee.getEmail();
            sessions = sessionRepository
                    .findBySessionOwnerAndSessionStatusOrderByActualDateAsc(owner,SessionStatus.SCEDULED);

        } else if ("coach".equalsIgnoreCase(userType)) {
            sessions = sessionRepository
                    .findByCoach_EmailAndSessionStatus(email,SessionStatus.SCEDULED);
        }



        System.out.println(" The pulled out size====="+sessions.size());
        model.addAttribute("upcomingSessions", sessions);
        //System.out.println(" Session after all ==="+session.toString());
        model.addAttribute("princi","demo");
        return "/upcoming_session";
    }
    @GetMapping("/ongoingBookings")
    public String ongoingBookings(OAuth2AuthenticationToken oAuth2AuthenticationToken,
                                  Model model){
        //System.out.println(" The Principal ====="+principal);
        String owner= (String) oAuth2AuthenticationToken.getPrincipal().getAttributes().get("orgName");
        String email= (String) oAuth2AuthenticationToken.getPrincipal().getAttributes().get("email");
        String userType= (String) oAuth2AuthenticationToken.getPrincipal().getAttributes().get("userType");



        System.out.println("in ongoingbooking userType==="
                +userType + "  and the owner===="+owner);

        List<Session> ongoingBookings = new ArrayList<>();


        if("coachee".equalsIgnoreCase(userType) || "Organization Admin".equalsIgnoreCase(userType)){

                Coachee coachee = coacheeRepository.findByEmail(email).get();

                if(coachee.getEmployee())
            ongoingBookings = sessionRepository
                    .findBySessionOwnerAndSessionStatusOrderByActualDateAsc
                            (coachee.getOrganization().getEmail(),SessionStatus.INITIATE);
                else ongoingBookings = sessionRepository
                        .findBySessionOwnerAndSessionStatusOrderByActualDateAsc
                                (coachee.getEmail(),SessionStatus.INITIATE);

        }
        if("coach".equalsIgnoreCase(userType)){
            ongoingBookings = sessionRepository.findByCoach_EmailAndSessionStatus(email,SessionStatus.INITIATE);
        }

        System.out.println(" The total sessions here==="+ongoingBookings.size());
        model.addAttribute("ongoingBookings",ongoingBookings);
        model.addAttribute("userType",userType);
        model.addAttribute("orgAdmin",userType.toLowerCase().contains("admin")?true:false);
        model.addAttribute("email",email);
        return "/ongoingBookings";
    }

    @GetMapping("/chat")
    public String openChat(OAuth2AuthenticationToken oAuth2AuthenticationToken,Model model){

        String name = (String) oAuth2AuthenticationToken.getPrincipal().getAttributes().get("name");
        System.out.println(" The Name in Chat==="+name);
        model.addAttribute("name",name);
        return "/chat";
    }
    @GetMapping("/coachingAgreement/{sessionId}")
    public String coachingAgreement(OAuth2AuthenticationToken oAuth2AuthenticationToken,
                                    @PathVariable Long sessionId, Model model){

        String name = (String) oAuth2AuthenticationToken.getPrincipal().getAttributes().get("name");
        Session session = sessionRepository.findById(sessionId).get();

        System.out.println(" Loading agreement for ==="+session.getSessionOwner());
        model.addAttribute("name",name);
        model.addAttribute("coachingSession",session);
        model.addAttribute("name",name);
        return "/coachingAgreement";
    }

    @GetMapping("/signAgreement/{sessionId}")
    public String signAgreement(OAuth2AuthenticationToken oAuth2AuthenticationToken,
                                    @PathVariable Long sessionId, Model model){

        String name = (String) oAuth2AuthenticationToken.getPrincipal().getAttributes().get("name");
        String userType= (String) oAuth2AuthenticationToken.getPrincipal().getAttributes().get("userType");

        Session session = sessionRepository.findById(sessionId).get();

        if("coach".equalsIgnoreCase(userType))
            session.getCoachAgreement().setSigned(true);
        else
            session.getOrgAgreement().setSigned(true);

        sessionRepository.save(session);

        System.out.println(" Signing agreement for ==="+session.getSessionOwner());

        model.addAttribute("coachingSession",session);
        model.addAttribute("name",name);
        return "/coachingAgreement";
    }
    @GetMapping("/coacheeAssessmentList/{sessionId}")
    public String coacheeAssesmentList(OAuth2AuthenticationToken oAuth2AuthenticationToken,
                                       @PathVariable Long sessionId, Model model){

        String name = (String) oAuth2AuthenticationToken.getPrincipal().getAttributes().get("name");
        Session session = sessionRepository.findById(sessionId).get();


        System.out.println(" The Name in Chat==="+name);
        model.addAttribute("name",name);
        model.addAttribute("coachingSession",session);
        return "/coacheeAssessmentList";
    }
    @GetMapping("/mycoachees")
    public String myCoachees(OAuth2AuthenticationToken oAuth2AuthenticationToken,
                                       Model model){

        String name = (String) oAuth2AuthenticationToken.getPrincipal().getAttributes().get("name");
        String email = (String) oAuth2AuthenticationToken.getPrincipal().getAttributes().get("email");

        List<Session> sessions = sessionRepository.findByCoach_Email(email);
        List<SessionCoachees> sessionCoachees = sessions.stream().flatMap(
                session -> session.getSessionCoachees().stream()).collect(Collectors.toList());
        System.out.println(" The Name in Chat==="+name);
        model.addAttribute("name",name);
        model.addAttribute("sessionCoachees",sessionCoachees);
        return "/mycoachees";
    }

}
