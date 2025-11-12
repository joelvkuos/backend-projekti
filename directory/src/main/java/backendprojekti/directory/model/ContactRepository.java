package backendprojekti.directory.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {

    List<Contact> findByLastName(String lastName);
    List<Contact> findByUser(AppUser user);
}
