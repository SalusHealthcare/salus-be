package be.salushealthcare.salus;

import be.salushealthcare.salus.user.User;
import be.salushealthcare.salus.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class CommandLineAppStartupRunner {
        //implements CommandLineRunner {
    /*@Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Value("${salus.security.createAdminAtLaunch}")
    private boolean createAdminAtLaunch;

    // TODO Successivamente passare i parametri di costruzione dell'admin all'avvio dell'app
    @Override
    public void run(String...args) throws Exception {
        String email = "admin@salus.com";
        String password = passwordEncoder.encode("admin");
        Set<String> authorities = Set.of("ADMIN");
        Long personId = 0L;

        if (createAdminAtLaunch &&
                !userRepository.existsByEmail(email)
                        // TODO il controllo sotto non funziona fixarlo e capire il problema
                        // || userRepository.findAll().stream().anyMatch(u -> u.getRoles().contains("ADMIN")))
        ) {
            User admin = User.builder()
                    .email(email)
                    .password(password)
                    .roles(authorities)
                    .personId(personId)
                    .build();
            userRepository.save(admin);
        }
    }*/
}