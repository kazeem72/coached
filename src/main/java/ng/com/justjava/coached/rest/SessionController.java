package ng.com.justjava.coached.rest;

//import io.camunda.zeebe.client.ZeebeClient;

import ng.com.justjava.coached.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class SessionController {

    @Autowired
    SessionRepository sessionRepository;
    @GetMapping("/upcoming-session")
    public String getComingSession(){
        return "upcoming-session";
    }

    @GetMapping("/sessionDetail/{sessionId}")
    public String getSessionDetails(@PathVariable Long sessionId, Model model ){
        
        Session session = sessionRepository.findById(sessionId).get();
        List<Coachee> coachees = session.getSessionCoachees()
                .stream().map(sessionCoachee -> sessionCoachee.getCoachee())
                .collect(Collectors.toList());
        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setFrequency(session.getFrequency());
        bookingDTO.setCoachId(session.getCoach().getId());
        bookingDTO.setCaseNote(session.getCaseNote());
        bookingDTO.setProposedStartDate(session.getProposedStartDate());

        bookingDTO.setMeetingType(session.getManyToOne()?"Many to One":"One to One");
        model.addAttribute("bookingPreview", bookingDTO);
        model.addAttribute("coach", session.getCoach());
        model.addAttribute("employees", coachees);
        model.addAttribute("hideContinue", true);
        return "/bookingPreview";
    }

}
