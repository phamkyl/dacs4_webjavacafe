package project.cafeweb.Controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import project.cafeweb.model.Product;
import project.cafeweb.model.categoryProduct;
import project.cafeweb.model.contact;
import project.cafeweb.service.ContactService;

@Controller
@RequestMapping
public class ContactController {

    @Autowired
    private ContactService contactService;

    @GetMapping("/contac-save")
    public String showContactForm(Model model) {
        model.addAttribute("contactForm", new contact());
        return "contactDetail";
    }

    @PostMapping("/contac-save")
    public String saveContact(@ModelAttribute("contactForm") @Valid contact contactForm,
                              BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "contactDetail";
        }

        contactService.saveContact(contactForm);
        return "redirect:/thankyou"; // Redirect to a thank-you page or another appropriate page
    }

    @GetMapping("/thankyou")
    public String showThankYouPage() {
        return "thankyou";
    }
    
    @GetMapping("/contac-list")
    public String listcontact(Model model) {
        
        List<contact> contact = contactService.listAll();
        model.addAttribute("listcontact", contact);

        return "list-contact";
    }
}
