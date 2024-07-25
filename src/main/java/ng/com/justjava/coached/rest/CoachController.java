package ng.com.justjava.coached.rest;

import ng.com.justjava.coached.entity.Coach;
import ng.com.justjava.coached.entity.CoachRepository;
import ng.com.justjava.coached.entity.Coachee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
@CrossOrigin
public class CoachController {

    @Autowired
    CoachRepository coachRepository;

    @GetMapping("/coaches")
    public String employees(OAuth2AuthenticationToken oAuth2AuthenticationToken,
                            Model model){
        model.addAttribute("princi",oAuth2AuthenticationToken.getPrincipal().getAttributes().get("name"));
        return "coaches";
    }

    @GetMapping("/coaches/{coachId}")
    public String employee(OAuth2AuthenticationToken oAuth2AuthenticationToken,
                           Model model, @PathVariable Long coachId){
        Optional<Coach> tempCoach = coachRepository.findById(coachId);

        System.out.println(" The coach retrieved ====="+tempCoach.get().getFullName()
        +" the total certificates size " +
                "=="+tempCoach.get().getCertificate().size());

        model.addAttribute("coach",tempCoach.get());
        model.addAttribute("name",oAuth2AuthenticationToken.getPrincipal().getAttributes().get("name"));
        return "coachInformation";
    }
}
