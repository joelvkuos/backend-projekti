package backendprojekti.directory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import backendprojekti.directory.model.Contact;
import backendprojekti.directory.model.ContactRepository;

import org.springframework.boot.CommandLineRunner;

@Configuration
public class DataLoader {
    @Bean
    CommandLineRunner init(ContactRepository repo) {
        return args -> {
            if (repo.count() == 0) {
                repo.save(new Contact(null, "Matti", "Meikäläinen", "matti@example.com",
                        "04412345678", "Esimerkkikatu 10", "01100", "Helsinki"));
            }
            System.out.println("After init, count = " + repo.count());
        };
    }
}