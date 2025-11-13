package backendprojekti.directory;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import backendprojekti.directory.model.AppUser;
import backendprojekti.directory.model.AppUserRepository;
import backendprojekti.directory.model.Contact;
import backendprojekti.directory.model.ContactRepository;

@Configuration
public class DataLoader {
    @Bean
    CommandLineRunner init(ContactRepository repo, AppUserRepository userRepo, PasswordEncoder encoder) {
        return args -> {
            AppUser testUser = null;
            AppUser adminUser = null;

            if (userRepo.count() == 0) {
                testUser = new AppUser(null, "user", encoder.encode("password"), "USER");
                userRepo.save(testUser);
                System.out.println("Testikäyttäjä 'user' luotu!");

                adminUser = new AppUser(null, "admin", encoder.encode("admin"), "ADMIN");
                userRepo.save(adminUser);
                System.out.println("Admin-käyttäjä 'admin' luotu!");
            } else {
                testUser = userRepo.findByUsername("user").orElse(null);
                adminUser = userRepo.findByUsername("admin").orElse(null);
            }

            if (repo.count() == 0 && testUser != null) {
                Contact contact = new Contact(null, "Matti", "Meikäläinen", "matti@example.com",
                        "04412345678", "Esimerkkikatu 10", "01100", "Helsinki");
                contact.setUser(testUser);
                repo.save(contact);
            }

            System.out.println("Users: " + userRepo.count() + ", Contacts: " + repo.count());
        };
    }
}