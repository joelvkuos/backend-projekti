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
            if (userRepo.count() == 0) {
                AppUser user1 = new AppUser(null, "joel", encoder.encode("salasana123"));
                userRepo.save(user1);
                System.out.println("Testikäyttäjä 'joel' luotu!");
            }

            if (repo.count() == 0) {
                repo.save(new Contact(null, "Matti", "Meikäläinen", "matti@example.com",
                        "04412345678", "Esimerkkikatu 10", "01100", "Helsinki"));
            }

            System.out.println("Users: " + userRepo.count() + ", Contacts: " + repo.count());
        };
    }
}