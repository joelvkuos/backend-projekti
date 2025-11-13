package backendprojekti.directory.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import backendprojekti.directory.model.AppUser;
import backendprojekti.directory.model.AppUserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final AppUserRepository appUserRepository;

    public CustomUserDetailsService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = appUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Käyttäjä " + username + " ei löydy"));

        String role = appUser.getRole() != null ? appUser.getRole() : "USER";

        return User.builder()
                .username(appUser.getUsername())
                .password(appUser.getPassword())
                .roles(role) // ROLE_USER or ROLE_ADMIN
                .build();
    }
}
