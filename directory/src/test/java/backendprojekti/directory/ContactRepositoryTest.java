package backendprojekti.directory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import backendprojekti.directory.model.Contact;
import backendprojekti.directory.model.ContactRepository;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

@SpringBootTest
public class ContactRepositoryTest {

    @Autowired
    ContactRepository repository;

    @Test
    void findByContactFirstName() {
        Contact contact = new Contact(null, "Matti", "Meikäläinen", "meikalainen@email.com", "044123456",
                "Esimerkkikatu 10", "01001", "Helsinki");
        repository.save(contact);

        List<Contact> contacts = repository.findByLastName("Meikäläinen");

        assertFalse(contacts.isEmpty());
        assertEquals("Meikäläinen", contacts.get(0).getLastName());

    }

    @Test
    void createNewContact() {
        Contact contact = new Contact(null, "Laura", "Virtanen", "laura.virtanen@example.com", "+358502345678",
                "Keskuskatu 15", "00200", "Espoo");
        Contact saved = repository.save(contact);

        assertNotNull(saved.getId());
        assertEquals("Virtanen", saved.getLastName());
    }

    @Test
    void deleteContact() {
        Contact contact = new Contact(null, "Antti", "Korhonen", "antti.korhonen@example.com", "+358445556667",
                "Rantatie 22", "33100", "Tampere");
        Contact saved = repository.save(contact);

        repository.delete(saved);

        List<Contact> contacts = repository.findByLastName("Korhonen");
        assertTrue(contacts.stream().noneMatch(b -> b.getLastName().equals("Korhonen")));
    }

}
