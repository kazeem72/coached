package ng.com.justjava.coached.rest;

import ng.com.justjava.coached.entity.Coachee;
import ng.com.justjava.coached.entity.Organization;
import ng.com.justjava.coached.entity.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@CrossOrigin
public class OrganizationController {

    @Autowired
    OrganizationRepository organizationRepository;
    @GetMapping("/organizationsList")
    public String coacheesList(OAuth2AuthenticationToken oAuth2AuthenticationToken,
                               Model model){
        List<Organization> organizations= organizationRepository.findAll();

        model.addAttribute("organizations",organizations);
        model.addAttribute("princi",oAuth2AuthenticationToken.getPrincipal().getAttributes().get("name"));

        return "/organizationsList";
    }
    @GetMapping("/organizations/{orgId}")
    public String organizationsList(OAuth2AuthenticationToken oAuth2AuthenticationToken,
                               @PathVariable Long orgId,
                               Model model){

        System.out.println(" Acceptance======"+orgId);
        Organization organization= organizationRepository.findById(orgId).get();

        model.addAttribute("organization",organization);
        model.addAttribute("princi",oAuth2AuthenticationToken.getPrincipal().getAttributes().get("name"));

        return "/organizationInfo";
    }
}
