package backendprojekti.directory.controller;

import java.net.URI;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import backendprojekti.directory.model.AppUser;
import backendprojekti.directory.model.AppUserRepository;
import backendprojekti.directory.model.Contact;
import backendprojekti.directory.model.ContactRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/contacts")
public class ContactRestController {

    private final ContactRepository contactRepository;
    private final AppUserRepository appUserRepository;

    public ContactRestController(ContactRepository contactRepository, AppUserRepository appUserRepository) {
        this.contactRepository = contactRepository;
        this.appUserRepository = appUserRepository;
    }

    @GetMapping
    public List<Contact> getAllContacts(@AuthenticationPrincipal UserDetails userDetails) {
        AppUser currentUser = appUserRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));
        return contactRepository.findByUser(currentUser);
    }

    @GetMapping("/{id}")
    public Contact geContactById(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        AppUser currentUser = appUserRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));

        Contact contact = contactRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contact not found"));

        if (!contact.getUser().getId().equals(currentUser.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied");
        }

        return contact;
    }

    @PostMapping
    public ResponseEntity<Contact> createContact(@Valid @RequestBody Contact contact,
            @AuthenticationPrincipal UserDetails userDetails) {
        AppUser currentUser = appUserRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));

        contact.setUser(currentUser);
        Contact saved = contactRepository.save(contact);
        URI location = URI.create("/api/contacts/" + saved.getId());
        return ResponseEntity.created(location).body(saved);
    }

    @PutMapping("/{id}")
    public Contact updateContact(@PathVariable Long id,
            @Valid @RequestBody Contact updated,
            @AuthenticationPrincipal UserDetails userDetails) {
        AppUser currentUser = appUserRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));

        Contact existing = contactRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contact not found"));

        if (!existing.getUser().getId().equals(currentUser.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied");
        }

        existing.setFirstName(updated.getFirstName());
        existing.setLastName(updated.getLastName());
        existing.setEmail(updated.getEmail());
        existing.setPhoneNumber(updated.getPhoneNumber());
        existing.setCity(updated.getCity());
        existing.setPostalCode(updated.getPostalCode());
        existing.setStreetAddress(updated.getStreetAddress());
        return contactRepository.save(existing);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContact(@PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails) {
        AppUser currentUser = appUserRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));

        Contact contact = contactRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contact not found"));

        if (!contact.getUser().getId().equals(currentUser.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied");
        }

        contactRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
