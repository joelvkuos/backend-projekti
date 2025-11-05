package backendprojekti.directory.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;

import backendprojekti.directory.model.Contact;
import backendprojekti.directory.model.ContactRepository;

@Controller
public class ContactController {

    private final ContactRepository contactRepository;

    public ContactController(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @GetMapping({ "/", "directory" })
    public String directory(Model model) {
        model.addAttribute("contacts", contactRepository.findAll());
        return "directory";
    }

    @GetMapping("/addcontact")
    public String showAddContactForm(Model model) {
        model.addAttribute("contact", new Contact());
        return "addcontact";
    }

    @PostMapping("/addcontact")
    public String addContact(@ModelAttribute Contact contact) {
        contactRepository.save(contact);
        return "redirect:/directory";
    }

    @GetMapping("/editcontact/{id}")
    public String editContact(@PathVariable("id") Long ContactId, Model model) {
        Contact contact = contactRepository.findById(ContactId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid id: " + ContactId));
        model.addAttribute("contact", contact);
        return "editcontact";
    }

    @PostMapping("/editcontact/{id}")
    public String updateContact(@PathVariable("id") Long id, @ModelAttribute("contact") Contact contact) {
        contact.setId(id);
        contactRepository.save(contact);
        return "redirect:/directory";
    }

    @GetMapping("/delete/{id}")
    public String deleteContact(@PathVariable("id") Long id) {
        contactRepository.deleteById(id);
        return "redirect:/directory";
    }

}
