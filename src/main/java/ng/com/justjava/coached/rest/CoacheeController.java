package ng.com.justjava.coached.rest;

import jakarta.servlet.http.HttpServletRequest;
import ng.com.justjava.coached.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Optional;

@Controller
@CrossOrigin
public class CoacheeController {

    @Autowired
    private CoacheeRepository coacheeRepository;
    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private OrganizationRepository organizationRepository;

    @PostMapping(path="/editCoachee")
    public String editCoach(OAuth2AuthenticationToken oAuth2AuthenticationToken,
                            HttpServletRequest request,
                            @ModelAttribute Coachee coachee,
                            @RequestParam("focusArea") List<String> focusAreas,
                            @RequestParam("picture") MultipartFile picture,
                            Model model){


        String companyName= (String) oAuth2AuthenticationToken.getPrincipal().getAttributes().get("orgName");
        String firstName= (String) oAuth2AuthenticationToken.getPrincipal().getAttributes().get("given_name");
        String lastName= (String) oAuth2AuthenticationToken.getPrincipal().getAttributes().get("family_name");
        String email= (String) oAuth2AuthenticationToken.getPrincipal().getAttributes().get("email");
        String fullName= (String) oAuth2AuthenticationToken.getPrincipal().getAttributes().get("name");
        String userType= (String) oAuth2AuthenticationToken.getPrincipal().getAttributes().get("userType");


        System.out.println(" coach==="+coachee
                + " picture===="+picture.getSize() + " focus areas=="+focusAreas.size());

        try {
            coachee.setEmail(email);
            coachee.setFullName(fullName);
            coachee.setStatus("Joined");

            coachee.setMyPicture(picture.getOriginalFilename());
            File pictureFile = new File(resourceLoader
                    .getResource("classpath:static/images")
                    .getFile()+"/"+picture.getOriginalFilename());
            picture.transferTo(pictureFile);

            System.out.println(" After creating the file picture....."+pictureFile.getAbsolutePath());

            if("Organization Admin".equalsIgnoreCase(userType)){
                coachee.setAdmin(true);
                coachee.setEmployee(true);
                Organization organization = new Organization();
                organization.setOrgAdmin(coachee);
                organization.setName(companyName);
                organization.setEmail(coachee.getEmail());
                organization.addCoachee(coachee);
                organization = organizationRepository.save(organization);
                //System.out.println(" The Organization newly saved here=="+organization);
            }
            coacheeRepository.save(coachee);
            request.getSession(true).setAttribute("firstTime",false);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        model.addAttribute("coachee",coachee);
        model.addAttribute("name",oAuth2AuthenticationToken.getPrincipal().getAttributes().get("name"));
        return "redirect:/";
    }
    @GetMapping("/coacheeList")
    public String coacheesList(OAuth2AuthenticationToken oAuth2AuthenticationToken,
                              Model model){
        List<Coachee> coachees= coacheeRepository.findAll();

        model.addAttribute("coachees",coachees);
        model.addAttribute("princi",oAuth2AuthenticationToken.getPrincipal().getAttributes().get("name"));

        return "/coacheeList";
    }
}
