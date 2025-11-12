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
            
            if (userRepo.count() == 0) {
                testUser = new AppUser(null, "user", encoder.encode("password"));
                userRepo.save(testUser);
                System.out.println("Testikäyttäjä 'user' luotu!");
            } else {
                testUser = userRepo.findByUsername("user").orElse(null);
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