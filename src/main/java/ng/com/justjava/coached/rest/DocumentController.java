package ng.com.justjava.coached.rest;
import ng.com.justjava.coached.entity.Document;
import ng.com.justjava.coached.entity.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.concurrent.atomic.AtomicReference;

@Controller
public class DocumentController {
    @Autowired
    DocumentRepository documentRepository;
    @GetMapping("/agreement")
    public String document(Model model){

        AtomicReference<Document> document = new AtomicReference<>(new Document());
        documentRepository.findBySubject("Agreement").ifPresent(document1 -> document.set(document1));

        System.out.println(" Inside Get mapping of the agreement document=="+document.get());
        model.addAttribute("document",document.get());
        return "/agreementSetup";
    }
    @PostMapping("/agreement")
    public String saveDocument(@ModelAttribute Document document){

        System.out.println(" The Document Here======"+document);
        document = documentRepository.save(document);
        return "redirect:/agreement";
    }
}
