package ng.com.justjava.coached.rest;

import ng.com.justjava.coached.entity.BookingDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BookingController {
    @GetMapping("/booking")
    public String booking(Model model){

        System.out.println(" The booking is called ..............................");

        model.addAttribute("princi","demo");
        model.addAttribute("booking",new BookingDTO());
        return "booking";
    }

    @PostMapping("/bookingPreview")
    public String bookingPreview(@ModelAttribute BookingDTO bookingDTO, Model model){

        System.out.println(" The booking meeting type" +
                " ==="+ bookingDTO.getMeetingType() +
                " frequency==="+bookingDTO.getFrequency());

        model.addAttribute("princi","demo");
        return "bookingPreview";
    }
}
