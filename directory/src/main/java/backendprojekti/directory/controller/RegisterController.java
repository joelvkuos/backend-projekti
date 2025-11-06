package backendprojekti.directory.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import backendprojekti.directory.model.AppUser;
import backendprojekti.directory.model.AppUserRepository;

@Controller
@RequestMapping("/register")
public class RegisterController {

    private final AppUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public RegisterController(AppUserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String showRegisterForm(Model model) {
        model.addAttribute("appuser", new AppUser());
        return "register";
    }

    @PostMapping
    public String registerUser(@ModelAttribute AppUser appUser, Model model) {
        if (userRepository.findByUsername(appUser.getUsername()).isPresent()) {
            model.addAttribute("error", "Username already used!");
            return "register";
        }

        if (appUser.getPassword() == null || appUser.getPassword().isEmpty()) {
            model.addAttribute("error", "Password can't be empty!");
            return "register";
        }

        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));

        userRepository.save(appUser);

        model.addAttribute("success", "Registration successful! You can now log in.");
        return "register";
    }
}
