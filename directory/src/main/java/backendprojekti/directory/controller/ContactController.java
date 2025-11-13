package backendprojekti.directory.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;

import backendprojekti.directory.model.AppUser;
import backendprojekti.directory.model.AppUserRepository;
import backendprojekti.directory.model.Contact;
import backendprojekti.directory.model.ContactRepository;
import jakarta.validation.Valid;

@Controller
public class ContactController {

    private final ContactRepository contactRepository;
    private final AppUserRepository appUserRepository;

    public ContactController(ContactRepository contactRepository, AppUserRepository appUserRepository) {
        this.contactRepository = contactRepository;
        this.appUserRepository = appUserRepository;
    }

    @GetMapping("/directory")
    public String directory(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        AppUser currentUser = appUserRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        model.addAttribute("contacts", contactRepository.findByUser(currentUser));
        return "directory";
    }

    @GetMapping("/addcontact")
    public String showAddContactForm(Model model) {
        model.addAttribute("contact", new Contact());
        return "addcontact";
    }

    @PostMapping("/addcontact")
    public String addContact(@Valid @ModelAttribute Contact contact,
            BindingResult result,
            @AuthenticationPrincipal UserDetails userDetails,
            Model model) {
        if (result.hasErrors()) {
            return "addcontact";
        }

        AppUser currentUser = appUserRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        contact.setUser(currentUser);
        contactRepository.save(contact);
        return "redirect:/directory";
    }

    @GetMapping("/editcontact/{id}")
    public String editContact(@PathVariable("id") Long ContactId, Model model,
            @AuthenticationPrincipal UserDetails userDetails) {
        AppUser currentUser = appUserRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Contact contact = contactRepository.findById(ContactId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid id: " + ContactId));

        // Varmista että kontakti kuuluu kirjautuneelle käyttäjälle
        if (!contact.getUser().getId().equals(currentUser.getId())) {
            throw new IllegalArgumentException("Unauthorized access");
        }

        model.addAttribute("contact", contact);
        return "editcontact";
    }

    @PostMapping("/editcontact/{id}")
    public String updateContact(@PathVariable("id") Long id,
            @Valid @ModelAttribute("contact") Contact contact,
            BindingResult result,
            @AuthenticationPrincipal UserDetails userDetails,
            Model model) {
        if (result.hasErrors()) {
            contact.setId(id);
            return "editcontact";
        }

        AppUser currentUser = appUserRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        contact.setId(id);
        contact.setUser(currentUser);
        contactRepository.save(contact);
        return "redirect:/directory";
    }

    @GetMapping("/delete/{id}")
    public String deleteContact(@PathVariable("id") Long id, @AuthenticationPrincipal UserDetails userDetails) {
        AppUser currentUser = appUserRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Contact contact = contactRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid id: " + id));

        // Varmista että kontakti kuuluu kirjautuneelle käyttäjälle
        if (!contact.getUser().getId().equals(currentUser.getId())) {
            throw new IllegalArgumentException("Unauthorized access");
        }

        contactRepository.deleteById(id);
        return "redirect:/directory";
    }

}
