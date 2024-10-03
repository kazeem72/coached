package ng.com.justjava.coached.rest;

import ng.com.justjava.coached.entity.Coach;
import ng.com.justjava.coached.entity.Materials;
import ng.com.justjava.coached.entity.MaterialsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
public class ResourceController {

    @Autowired
    MaterialsRepository materialsRepository;
    @GetMapping("/resources")
    public String resources(OAuth2AuthenticationToken oAuth2AuthenticationToken,
                            Model model){

        List<Materials> materials=materialsRepository.findAll();
        model.addAttribute("materials",materials);
        model.addAttribute("name",oAuth2AuthenticationToken.getPrincipal().getAttributes().get("name"));
        return "/resources";
    }
    @PostMapping(path="/addResource")
    public String editCoach(OAuth2AuthenticationToken oAuth2AuthenticationToken,
                            @ModelAttribute Materials material,
                            Model model) {

        material.setShortDescription(material.getDescription());
        material.setLink(material.getDescription());
        material=materialsRepository.save(material);
        System.out.println(" The material sent here==="+material);
        List<Materials> materials=materialsRepository.findAll();
        model.addAttribute("materials",materials);
        model.addAttribute("name",oAuth2AuthenticationToken.getPrincipal().getAttributes().get("name"));

        return "/resources";
    }

    }