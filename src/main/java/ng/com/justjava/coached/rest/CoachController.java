package ng.com.justjava.coached.rest;

import jakarta.servlet.http.HttpServletRequest;
import ng.com.justjava.coached.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@CrossOrigin
public class CoachController {

    @Autowired
    CoachRepository coachRepository;

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private MaterialsRepository materialsRepository;

    @GetMapping("/coachesList")
    public String coachesList(OAuth2AuthenticationToken oAuth2AuthenticationToken,
                            Model model){
        List<Coach> coaches= coachRepository.findAll();

        model.addAttribute("coaches",coaches);
        model.addAttribute("princi",oAuth2AuthenticationToken.getPrincipal().getAttributes().get("name"));

        return "/coachesList";
    }
    @GetMapping("/coaches")
    public String coaches(OAuth2AuthenticationToken oAuth2AuthenticationToken,
                          Model model){
        model.addAttribute("princi",oAuth2AuthenticationToken.getPrincipal().getAttributes().get("name"));

        return "/coaches";
    }
    @GetMapping("/coaches/{coachId}")
    public String employee(OAuth2AuthenticationToken oAuth2AuthenticationToken,
                           Model model, @PathVariable Long coachId){
        Optional<Coach> tempCoach = coachRepository.findById(coachId);

        System.out.println(" The coach retrieved ====="+tempCoach.get().getFullName()
        +" the total certificates size " +
                "=="+tempCoach.get().getMyCertifications().size());

        model.addAttribute("coach",tempCoach.get());
        model.addAttribute("name",oAuth2AuthenticationToken.getPrincipal().getAttributes().get("name"));
        return "/coachInformation";
    }

    @PostMapping(path="/editCoach")
    public String editCoach(OAuth2AuthenticationToken oAuth2AuthenticationToken,
                            HttpServletRequest request,
                            @ModelAttribute Coach coach,
                            @RequestParam("picture") MultipartFile picture,
                            @RequestParam("video") MultipartFile video,
                            @RequestParam("award1") MultipartFile awards,
                            @RequestParam("certificate1") MultipartFile certificate,
                                Model model){


        String companyName= (String) oAuth2AuthenticationToken.getPrincipal().getAttributes().get("orgName");
        String firstName= (String) oAuth2AuthenticationToken.getPrincipal().getAttributes().get("given_name");
        String lastName= (String) oAuth2AuthenticationToken.getPrincipal().getAttributes().get("family_name");
        String email= (String) oAuth2AuthenticationToken.getPrincipal().getAttributes().get("email");
        String fullName= (String) oAuth2AuthenticationToken.getPrincipal().getAttributes().get("name");
        String userType= (String) oAuth2AuthenticationToken.getPrincipal().getAttributes().get("userType");



//parameters===={
// picture=Remita.png, biography=Here is a short biography,
// video=VID-20240426-WA0019.mp4,
// focus_area=Executive,
// certificate1=Payment for Coollink subscription.pdf}

        try {

            Diary booked = new Diary();
            Diary available = new Diary();
            coach.setBooked(booked);
            coach.setAvailable(available);
            coach.setEmail(email);
            //coach.setExperience(6);
            coach.setFirstName(firstName);
            coach.setLastName(lastName);
            coach.setVerify(false);
            coach.addCertificate(certificate.getOriginalFilename()+"_"+firstName);
            coach.addAward(awards.getOriginalFilename()+"_"+firstName);


            coach.setMyPicture(picture.getOriginalFilename()+"_"+firstName);
            coach.setMyVideo(video.getOriginalFilename()+"_"+firstName);

            File pictureFile = new File(resourceLoader
                    .getResource("classpath:static/images")
                    .getFile()+"/"+picture.getOriginalFilename()+"_"+firstName);
            picture.transferTo(pictureFile);

            File awardFile = new File(resourceLoader
                    .getResource("classpath:static/images")
                    .getFile()+"/"+awards.getOriginalFilename()+"_"+firstName);
            awards.transferTo(awardFile);

            File certificateFile = new File(resourceLoader
                    .getResource("classpath:static/images")
                    .getFile()+"/"+certificate.getOriginalFilename()+"_"+firstName);
            certificate.transferTo(certificateFile);
            File videoFile = new File(resourceLoader
                    .getResource("classpath:static/images")
                    .getFile()+"/"+video.getOriginalFilename()+"_"+firstName);
            video.transferTo(videoFile);
            System.out.println(" After creating the file picture....."+pictureFile.getAbsolutePath());
            System.out.println(" After creating the file video ....."+videoFile.getAbsolutePath());

            System.out.println(" coach==="+coach
                    + " picture===="+picture.getSize() + "  video==="+video.getSize()+
                    " certificate=="+certificate.getSize()+"  award==="+awards.getSize());

            coachRepository.save(coach);
            request.getSession(true).setAttribute("firstTime",false);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


//The sent parameters ===={picture=Remita.png,
// video=VID-20240426-WA0019.mp4,
// certificate1=Payment for Coollink subscription.pdf}

        model.addAttribute("coach",coach);
        model.addAttribute("name",oAuth2AuthenticationToken.getPrincipal().getAttributes().get("name"));
        return "/coachInformation";
    }

    //adminCoachList
    @GetMapping("/adminCoachList")
    public String adminCoachList(OAuth2AuthenticationToken oAuth2AuthenticationToken,
                              @RequestParam(name = "keyword",required = false) String keyword,
                              Model model){
        List<Coach> coaches= coachRepository.findAll();

        if(keyword!=null){
            coaches = coaches.stream().filter(coach -> coach.getFocusArea()
                    .contains(keyword)).collect(Collectors.toList());
        }

        model.addAttribute("coaches",coaches);
        model.addAttribute("princi",oAuth2AuthenticationToken.getPrincipal().getAttributes().get("name"));

        return "/adminCoachList";
    }
    @GetMapping("/reviews")
    public String reviews(OAuth2AuthenticationToken oAuth2AuthenticationToken,
                                 Model model){
        String email= (String) oAuth2AuthenticationToken.getPrincipal().getAttributes().get("email");

        Coach coach = coachRepository.findByEmail(email).get();
        model.addAttribute("coach",coach);
        model.addAttribute("princi",oAuth2AuthenticationToken.getPrincipal().getAttributes().get("name"));

        return "/reviews";
    }
}
