package backendprojekti.directory.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import backendprojekti.directory.model.AppUser;
import backendprojekti.directory.model.AppUserRepository;
import backendprojekti.directory.model.ContactRepository;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final AppUserRepository userRepository;
    private final ContactRepository contactRepository;

    public AdminController(AppUserRepository userRepository, ContactRepository contactRepository) {
        this.userRepository = userRepository;
        this.contactRepository = contactRepository;
    }

    @GetMapping
    public String adminPage(Model model) {
        List<AppUser> users = (List<AppUser>) userRepository.findAll();
        long totalContacts = contactRepository.count();

        model.addAttribute("users", users);
        model.addAttribute("totalUsers", users.size());
        model.addAttribute("totalContacts", totalContacts);

        return "admin";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        AppUser user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        contactRepository.deleteAll(contactRepository.findByUser(user));
        userRepository.deleteById(id);

        return "redirect:/admin?deleted=true";
    }
}
