package ng.com.justjava.coached.rest;

//import com.google.common.collect.Lists;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ng.com.justjava.coached.entity.*;
import ng.com.justjava.coached.entity.Event;
import ng.com.justjava.coached.services.CoachService;
import org.apache.catalina.filters.ExpiresFilter;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@CrossOrigin
public class BookingController {


    @Autowired
    CoachRepository coachRepository;

    @Autowired
    SessionRepository sessionRepository;

    @Autowired
    CoacheeRepository coacheeRepository;

    @Autowired
    AssessmentRepository assessmentRepository;

    @Autowired
    CoachService coachService;
    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private SystemParametersRepository systemParametersRepository;

    @GetMapping("/booking/{coachID}")
    public String booking(OAuth2AuthenticationToken oAuth2AuthenticationToken,
                          @PathVariable Long coachID, Model model){

        System.out.println("######################################" +
                "############################## The booking is called ..............................");
        String companyName= (String) oAuth2AuthenticationToken.getPrincipal().getAttributes().get("orgName");
        String email = (String) oAuth2AuthenticationToken.getPrincipal().getAttributes().get("email");

        Coachee coachee = coacheeRepository.findByEmail(email).get();
        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setCoachId(coachID);

        List<Coachee> employees = coacheeRepository.findByOrganization_Name(companyName);


        model.addAttribute("princi","demo");
        model.addAttribute("booking",bookingDTO);
        model.addAttribute("coachee",coachee);

        model.addAttribute("employees",employees);
        return "/booking";
    }

    @PostMapping("/bookingPreview")
    public String bookingPreview(HttpServletRequest request,
                                 OAuth2AuthenticationToken oAuth2AuthenticationToken,
                                 @ModelAttribute BookingDTO bookingDTO, Model model){

        String nextPage = "bookingPreview";
        String email = (String) oAuth2AuthenticationToken.getPrincipal().getAttributes().get("email");

        if(bookingDTO.getCoachId()!=null) {
            System.out.println(" The booking meeting type" +
                    " ===" + bookingDTO.getMeetingType() +
                    " frequency===" + bookingDTO.getFrequency() +
                    " preview====" + bookingDTO.getPreview() +
                    " the coach ID===" + bookingDTO.getCoachId()+
                    " bookingDTO.getEmployeeList() size"+bookingDTO.getEmployeeList().size());
            bookingDTO.getEmployeeList()
                    .forEach(str -> System.out.println(" The String here ==== "
                            + str));


            Coach coach = coachRepository.findById(bookingDTO.getCoachId()).get();
            Coachee loginCoachee=coacheeRepository.findByEmail(email).get();

            List<Coachee> coachees = new ArrayList<>();

            if(loginCoachee.getAdmin())
                coachees.addAll(coacheeRepository.findByEmailIn(bookingDTO.getEmployeeList()));
            else coachees.add(loginCoachee);
            model.addAttribute("princi", "demo");
            model.addAttribute("coach", coach);
            model.addAttribute("employees", coachees);
            request.getSession().setAttribute("booking",bookingDTO);

        }
        model.addAttribute("bookingPreview", bookingDTO);
        return nextPage;
    }

    @GetMapping("/bookSession")
    public String bookSession(HttpServletRequest request,
                              OAuth2AuthenticationToken oAuth2AuthenticationToken,Model model){

        String companyName= (String) oAuth2AuthenticationToken.getPrincipal().getAttributes().get("orgName");
        String email = (String) oAuth2AuthenticationToken.getPrincipal().getAttributes().get("email");
        BookingDTO bookingDTO = (BookingDTO) request.getSession().getAttribute("booking");
        Coach coach = coachRepository.findById(bookingDTO.getCoachId()).get();
        Coachee bookingCoachee=coacheeRepository.findByEmail(email).get();
        List<Coachee> coachees = new ArrayList<>();

        if(bookingCoachee.getEmployee())
            coachees.addAll(coacheeRepository.findByEmailIn(bookingDTO.getEmployeeList()));
        else coachees.add(bookingCoachee);

        System.out.println(" Total Number of Coaches==="+coachees.size());

        Session session = new Session();
        session.setSessionOwner(email);
        session.setSessionStatus(SessionStatus.INITIATE);
        session.setManyToOne("Group".equalsIgnoreCase(bookingDTO.getMeetingType()));
        session.setCoach(coach);
        session.setFrequency(bookingDTO.getFrequency());
        Agreement coachAgreement = new Agreement();
        coachAgreement.setDescription(" The Agreement for Coach "+coach.getFullName());
        coachAgreement.setLink("/coached/coachAgreement/"+coach.getFullName());
        session.setCoachAgreement(coachAgreement);
        Agreement orgAgreement = new Agreement();
        orgAgreement.setDescription(" The Agreement for Company Name "+companyName);
        orgAgreement.setLink("/coached/orgAgreement/"+companyName);


        session.setOrgAgreement(orgAgreement);

        Invoice invoice = new Invoice();
        invoice.setInvoiceID(email+coach.getId()+System.nanoTime());

        SystemParameters systemParameters = systemParametersRepository.findByModule("session").get();
        Map parameters= systemParameters.getParameters();
        System.out.println(" The Parameters already setup==="+parameters);
        // Find the cost of a session, due date,
        invoice.setAmountDue(Double.valueOf((String) parameters.get("cost")));
        invoice.setDueDate(LocalDate.now());
        invoice.setIssueDate(LocalDate.now());
        invoice.setTaxPercent(Double.valueOf((String) parameters.get("cost")));

        invoice.setIssueTo(email);
        session = session.addInvoice(invoice);
        invoice.setSession(session);
        for (Coachee coachee:coachees) {
            SessionCoachees sessionCoachee = new SessionCoachees();
            sessionCoachee.setCoachee(coachee);
            sessionCoachee.setAssesmentLink("/coached/assesment/"+coachee.getId());
            System.out.println(" The selected employees here= coachee==="+sessionCoachee);
            session.addSessionCoachee(sessionCoachee);
        }
        session = sessionRepository.save(session);

        model.addAttribute("bookedSession",session);
        System.out.println(" The frequency here==="+bookingDTO.getFrequency());
        return "/bookingSuccess";
    }

    @GetMapping("/schedule/{sessionID}")
    public String schedule(@PathVariable Long sessionID, Model model){

        System.out.println(" The session scheduling  is called ..............................");

        Session session = sessionRepository.findById(sessionID).get();

        System.out.println(" The coach here===="+session.getCoach().getFullName());

        model.addAttribute("bookingSession",session);
        model.addAttribute("scheduleDTO",new BookingDTO());

        return "/schedule";
    }
    @GetMapping("/coachSchedule")
    public String coachSchedule(OAuth2AuthenticationToken oAuth2AuthenticationToken,
                                Model model){
        System.out.println(" The session scheduling  is called .............");
        String email = (String) oAuth2AuthenticationToken.getPrincipal().getAttributes().get("email");
        String userType = (String) oAuth2AuthenticationToken.getPrincipal().getAttributes().get("userType");

        Coach coach = coachRepository.findByEmail(email).get();

        List<String> dates = new ArrayList<>();//List.of("2024-08-07T18:56", "2024-08-15T18:56", "2024-08-13T18:56");

        coach.getAvailable().getEvents().forEach(event -> {
            System.out.println(" The date ==="+event.getEventDate().toString()+"T18:56");
            dates.add(event.getEventDate().toString()+"T18:56");
        });


        model.addAttribute("email",email);
        model.addAttribute("dates",dates);
        model.addAttribute("userType",userType);

        return "/coachSchedule";
    }
    @GetMapping("/coachDiary")
    public String coachDiary(OAuth2AuthenticationToken oAuth2AuthenticationToken,
                                Model model){
        System.out.println(" The session scheduling  is called .............");
        String email = (String) oAuth2AuthenticationToken.getPrincipal().getAttributes().get("email");
        String userType = (String) oAuth2AuthenticationToken.getPrincipal().getAttributes().get("userType");

        model.addAttribute("email",email);
        model.addAttribute("userType",userType);
        return "/coachDiary";
    }
    @PostMapping("/keepDiary/{dateTime}")
    public String keepDiary(OAuth2AuthenticationToken oAuth2AuthenticationToken,
                            @PathVariable String dateTime, Model model){
        System.out.println(" dateTime sent=="+dateTime);

        String email = (String) oAuth2AuthenticationToken.getPrincipal().getAttributes().get("email");
        String userType = (String) oAuth2AuthenticationToken.getPrincipal().getAttributes().get("userType");


        String date = dateTime.split("T")[0];
        String time = dateTime.split("T")[1];

        System.out.println(" The Date Here=="+date+"  time=="+time);

/*        DateTimeFormatter pattern=DateTimeFormatter.ofPattern("yyyy-mm-dd");*/
        LocalDate localDate = LocalDate.parse(date);
        Coach coach = coachRepository.findByEmail(email).get();


        Diary diary = coach.getAvailable();
        Event event = diary.getEvents().stream()
                .filter(event1 -> event1.getEventDate()
                        .isEqual(localDate)).findFirst().orElse(new Event());

        if(event.getEventDate()==null)
            event.setEventDate(localDate);

        event.addEventTime(time);
        diary.addEvent(event);
        System.out.println(" The Date Here=="+date+"  time=="+time + " event ==="+event);



        Map userAttributes = oAuth2AuthenticationToken.getPrincipal().getAttributes();
        //userAttributes.put("diary",diary);


        coach.setAvailable(diary);
        coachRepository.save(coach);

        model.addAttribute("email",email);
        model.addAttribute("userType",userType);
        return "/coachDiary";
    }
    @GetMapping(value = "/availableTime/{sessionID}/{eventDate}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Event availableTimes(@PathVariable Long sessionID,
                                 @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd")
                                 LocalDate eventDate){


        System.out.println(" The coach Id==="+sessionID + " the eventDate=="+eventDate);
        Session session = sessionRepository.findById(sessionID).get();
        List<Event> availableEvents = session.getCoach().getAvailable().getEvents();


        Event actualDate= availableEvents.stream()
                .filter(event -> event.getEventDate().isEqual(eventDate))
                .collect(Collectors.toList()).get(0);

        System.out.println(" The date ======"+actualDate.toString());

        List<String> availableTimes = actualDate.getEventTime();
        //model.addAttribute("bookingSession",session);
        return actualDate;
    }
    @GetMapping("/joinMeeting/{sessionID}")
    public String joinMeeting(@PathVariable Long sessionID, Model model){

        System.out.println(" The session scheduling  is called ..............................");
        return "/videocall";
    }

    @PostMapping("/saveSchedule")
    public String saveSchedule(OAuth2AuthenticationToken oAuth2AuthenticationToken,
                               @ModelAttribute ScheduleDTO scheduleDTO,
                               Model model){
        System.out.println(" The scheduleDTO................."+scheduleDTO.toString());

        Session session = sessionRepository.findById(scheduleDTO.getSessionId()).get();
        for (String schedule:scheduleDTO.getSchedule()) {
            if(!Strings.isEmpty(schedule)) {
                schedule = schedule.replace("#","T");
                System.out.println(" The scheduled Date around here ==="+schedule);
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                LocalDateTime dateTime = LocalDateTime.parse(schedule);

                //String datePart=schedule;//.split("#")[0];
                System.out.println(" The schedule around here ==="+schedule);
                Date date =                 java.util.Date
                        .from(dateTime.atZone(ZoneId.systemDefault())
                                .toInstant());

                System.out.println(" The date ==="+date);
                session.addSchedule(date);
            }
        }
        session.setSessionStatus(SessionStatus.SCEDULED);
        sessionRepository.save(session);

        String owner= (String) oAuth2AuthenticationToken.getPrincipal().getAttributes().get("orgName");

        List<Session> sessions = sessionRepository.findBySessionOwnerAndSessionStatusOrderByActualDateAsc(owner,SessionStatus.SCEDULED);
        System.out.println(" The pulled out size====="+sessions.size());
        model.addAttribute("upcomingSessions", sessions);
        //System.out.println(" Session after all ==="+session.toString());
        return "/upcoming_session";
    }
    @GetMapping("/gotomeeting/{sessionID}")
    public String gotoMeeting(OAuth2AuthenticationToken oAuth2AuthenticationToken,
                              @PathVariable String sessionID, Model model){

        String fullName = (String) oAuth2AuthenticationToken.getPrincipal().getAttributes().get("name");
        System.out.println(" The Name in Chat==="+fullName);
        model.addAttribute("roomId","room"+sessionID);
        model.addAttribute("fullName",fullName);
        return "/videocall";
    }
    @GetMapping("/assesment/{sessionId}/{email}")
    public String getAssessmentForm(OAuth2AuthenticationToken oAuth2AuthenticationToken,
                              @PathVariable String email,
                                    @PathVariable Long sessionId, Model model){

        String fullName = (String) oAuth2AuthenticationToken.getPrincipal().getAttributes().get("name");

        System.out.println(" sessionId ==="+sessionId);

        model.addAttribute("assessment",new Assessment());
        model.addAttribute("sessionId", sessionId);
        model.addAttribute("email",email);
        return "/assessmentForm";
    }
    @PostMapping("/assesment/{sessionId}/{email}")
    public String createAssessment(OAuth2AuthenticationToken oAuth2AuthenticationToken,
                                    @PathVariable Long sessionId,@PathVariable String email,
                                    @ModelAttribute Assessment assessment, Model model){
        String fullName = (String) oAuth2AuthenticationToken.getPrincipal().getAttributes().get("name");
        System.out.println(" Assessment ==="+assessment+" with session ID=="+sessionId
        + " Email=="+email);

        Session session = sessionRepository.findById(sessionId).get();

        session.getSessionCoachees().forEach(sessionCoachees -> {
            if(sessionCoachees.getCoachee().getEmail().equalsIgnoreCase(email)) {
                sessionCoachees.setAssessment(assessment);
                sessionCoachees.setAssesmentSigned(true);
            }
        });

        session.getSessionCoachees().forEach(sessionCoachees ->
                System.out.println(" Coachee Session ="+sessionCoachees.getCoachee()
                + " and the sign status=="+sessionCoachees.getAssesmentSigned()));

        sessionRepository.save(session);

        //assessmentRepository.save(assessment);
        //model.addAttribute("assessment",new Assessment());
        model.addAttribute("fullName",fullName);
        return "/ongoingBookings";
    }

    @ResponseBody
    @GetMapping("/checkout/{sessionId}")
    public InitiateCheckoutResponse initiateCheckout(@PathVariable Long sessionId,
                                                     Model model){

        System.out.println(" Session ID in InitiateCheckoutResponse=="+sessionId);

        Session session = sessionRepository.findById(sessionId).get();
        String invoiceOwner= session.getInvoice().getIssueTo();

        Double amountDue = session.getInvoice().getAmountDue();

        System.out.println(" Invoice owner==="+invoiceOwner + " amount=="+amountDue);
        CheckoutCustomer customer = new CheckoutCustomer();
        Map metadata = new HashMap();
        metadata.put("sessionId",sessionId);
        customer.setAmount(String.valueOf(amountDue));
        customer.setCallBack("https://justjavacoached.share.zrok.io/coached");
        customer.setMetadata(metadata);
        customer.setEmail(invoiceOwner);
        RestClient restClient = RestClient.builder().baseUrl("https://api.paystack.co").build();

        InitiateCheckoutResponse checkoutResponse= restClient.post()
                .uri("/transaction/initialize")
                .contentType(MediaType.APPLICATION_JSON)
                .body(customer)
                .header("Authorization","Bearer sk_test_4d881150e66cce2a78e6d2309532dd3824e95471")
                .retrieve()
                .body(InitiateCheckoutResponse.class);
        System.out.println(" The checkoutResponse access code==="+checkoutResponse);
        return checkoutResponse;
    }

    @PostMapping(value = "/checkoutWebhook",consumes = "application/json")
    @ResponseBody
    public void checkoutWebhook(@RequestBody CheckoutPaymentResponse response){
        Map metadata = response.getCutomData();
        System.out.println(" The Response Here from paystack ==="+response);
        Long sessionId = Long.valueOf((String) metadata.get("sessionId")) ;
        Session session = sessionRepository.findById(sessionId).get();
        Invoice invoice= session.getInvoice();
        invoice.setPaid(true);
        invoiceRepository.save(invoice);
        System.out.println(" metadata ==="+metadata);
    }
}
