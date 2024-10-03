package ng.com.justjava.coached.rest;

import jakarta.servlet.http.HttpServletResponse;
import ng.com.justjava.coached.entity.Invoice;
import ng.com.justjava.coached.entity.InvoiceRepository;
import ng.com.justjava.coached.entity.Session;
import ng.com.justjava.coached.entity.SessionRepository;
import ng.com.justjava.coached.util.PdfGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.context.Context;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@CrossOrigin
public class InvoiceController {

    private final SessionRepository sessionRepository;

    @Autowired
    InvoiceRepository invoiceRepository;
    @Autowired
    PdfGenerator pdfGenerator;

    public InvoiceController(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    @GetMapping("/pendingInvoice/{sessionId}")
    public String pendingInvoice(OAuth2AuthenticationToken oAuth2AuthenticationToken,
                           @PathVariable Long sessionId,
                           Model model){

        String name = (String) oAuth2AuthenticationToken.getPrincipal().getAttributes().get("name");
        System.out.println(" The sent session for this invoice===="+sessionId + " and name=="+name);
        Session session = sessionRepository.findById(sessionId).get();

        System.out.println(" The session owner here ==="+session.getSessionOwner());
        model.addAttribute("name",name);
        model.addAttribute("invoiceSession",session);
        return "/pendingInvoice";
    }


    @GetMapping("/pdf/{sessionId}")
    public void downloadPDF(OAuth2AuthenticationToken oAuth2AuthenticationToken,
                              @PathVariable Long sessionId,
                              HttpServletResponse response){

        String name = (String) oAuth2AuthenticationToken.getPrincipal().getAttributes().get("name");
        System.out.println(" The sent session for this invoice===="+sessionId + " and name=="+name);
        Session session = sessionRepository.findById(sessionId).get();
        try {
            Context context = new Context();
            context.setVariable("name", name);
            context.setVariable("invoiceSession", session);


            pdfGenerator.generatePdfFromHtml("testPDF",context,response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //return "pendingInvoice";
    }
    @GetMapping("/invoices")
    public String invoices(OAuth2AuthenticationToken oAuth2AuthenticationToken,
                           Model model){
        String owner= (String) oAuth2AuthenticationToken.getPrincipal().getAttributes().get("orgName");
        String email= (String) oAuth2AuthenticationToken.getPrincipal().getAttributes().get("email");
        String userType= (String) oAuth2AuthenticationToken.getPrincipal().getAttributes().get("userType");

        List<String> groups= (List<String>) oAuth2AuthenticationToken.getPrincipal().getAttributes().get("groups");

        Boolean isEZAdmin = groups.stream()
                .anyMatch(group -> group.equalsIgnoreCase("EZ37Admin"));


        List<Invoice> invoiceList = invoiceRepository.findAll();
        System.out.println("1 The total list==="+invoiceList.size());

        if(("Organization Admin".equalsIgnoreCase(userType) ||
                ("coachee".equalsIgnoreCase(userType)))
                && !isEZAdmin) {

            invoiceList = invoiceList.stream().filter(invoice ->
                    invoice.getIssueTo().equalsIgnoreCase(email))
                    .collect(Collectors.toList());
            System.out.println("2 The total list==="+invoiceList.size());
        }
        if("coach".equalsIgnoreCase(userType)) {
            invoiceList = invoiceList.stream().filter(invoice ->
                            invoice.getSession()
                                    .getCoach().getEmail().equalsIgnoreCase(email))
                    .collect(Collectors.toList());
            System.out.println("3 The total list==="+invoiceList.size());
        }

        System.out.println("4 The total list==="+invoiceList.size());

            Double totalAmountPaid= invoiceList.stream().filter(invoice -> invoice.getPaid())
                .mapToDouble(inv->inv.getAmountDue()).sum();

        Double totalAmountPending= invoiceList.stream().filter(invoice -> !invoice.getPaid())
                .mapToDouble(inv->inv.getAmountDue()).sum();

        Double totalAmountExpired= invoiceList.stream().filter(invoice -> invoice.getExpired())
                .mapToDouble(inv->inv.getAmountDue()).sum();

        Long totalPaid= invoiceList.stream()
                .filter(invoice -> invoice.getPaid()).count();

        Long totalPending= invoiceList.stream()
                .filter(invoice -> !invoice.getPaid()).count();

        Long expired=invoiceList.stream()
                .filter(invoice -> invoice.getExpired()).count();

        System.out.println(" Total Paid==="+totalPaid);
        System.out.println(" Total totalPending==="+totalPending);


        model.addAttribute("invoiceList",invoiceList);

        model.addAttribute("totalPaid",totalPaid);
        model.addAttribute("totalAmountPaid",totalAmountPaid);
        model.addAttribute("totalPending",totalPending);
        model.addAttribute("totalAmountPending",totalAmountPending);

        model.addAttribute("expired",expired);
        model.addAttribute("totalAmountExpired",totalAmountExpired);
        model.addAttribute("princi","demo");
        return "/invoices";
    }

    @ResponseBody
    @PostMapping("/payInvoice/{id}")
    public String payInvoice(Model model,@PathVariable Long id){
        System.out.println(" The ID Here===="+id);
        Invoice invoice=invoiceRepository.findById(id).get();
        invoice.setPaid(true);
        invoiceRepository.save(invoice);
        return "Invoice " +invoice.getInvoiceID()+"Paid Successfully";
    }
}
