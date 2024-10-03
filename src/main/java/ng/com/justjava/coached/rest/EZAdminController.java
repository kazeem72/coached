package ng.com.justjava.coached.rest;

import ng.com.justjava.coached.entity.Coach;
import ng.com.justjava.coached.entity.CoachRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin
public class EZAdminController {

    @Autowired
    CoachRepository coachRepository;

    @GetMapping("/coachVerificationRequest")
    public String coachVerificationRequest(OAuth2AuthenticationToken oAuth2AuthenticationToken,
                            Model model){
        model.addAttribute("princi",oAuth2AuthenticationToken.getPrincipal().getAttributes().get("name"));
        model.addAttribute("unVerifiedCoach",coachRepository.findByVerifyFalse());

        return "/coachVerificationRequest";
    }
    @GetMapping("/verify/{coachId}")
    public String verify(OAuth2AuthenticationToken oAuth2AuthenticationToken,
                                           @PathVariable Long coachId,
                                           Model model){

        System.out.println(" The Coach ID==="+coachId);
        Coach coach = coachRepository.findById(coachId).get();


        model.addAttribute("princi",oAuth2AuthenticationToken.getPrincipal().getAttributes().get("name"));
        model.addAttribute("coach",coach);

        return "/verifyCoach";
    }

    //@ResponseBody
    @PostMapping("/approve")
    public String approveCoach(@RequestParam Long id, Model model){
        Coach coach = coachRepository.findById(id).get();
        coach.setVerify(true);
        coachRepository.save(coach);
        model.addAttribute("unVerifiedCoach",coachRepository.findByVerifyFalse());
        return "/coachVerificationRequest";
    }
}
