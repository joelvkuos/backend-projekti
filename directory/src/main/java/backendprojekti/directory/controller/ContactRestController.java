package backendprojekti.directory.controller;

import java.util.List;

import org.springframework.boot.autoconfigure.graphql.GraphQlProperties.Http;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import backendprojekti.directory.model.Contact;
import backendprojekti.directory.model.ContactRepository;

@RestController
@RequestMapping("/api")
public class ContactRestController {

    private final ContactRepository contactRepository;

    public ContactRestController(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @GetMapping("/contacts")
    public List<Contact> getAllcContacts() {
        return (List<Contact>) contactRepository.findAll();
    }

    @GetMapping("/contacts{id}")
    public Contact geContactById(@PathVariable Long id) {
        return contactRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contact not found"));
    }

}
