package be.salushealthcare.salus;

import be.salushealthcare.salus.address.Address;
import be.salushealthcare.salus.person.Person;
import be.salushealthcare.salus.person.staff.Staff;
import be.salushealthcare.salus.person.staff.StaffService;
import be.salushealthcare.salus.security.JWTUserDetails;
import be.salushealthcare.salus.security.SecurityProperties;
import be.salushealthcare.salus.user.CreateUserInput;
import be.salushealthcare.salus.user.UpdatePasswordInput;
import be.salushealthcare.salus.user.User;
import be.salushealthcare.salus.user.UserRepository;
import be.salushealthcare.salus.user.UserService;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Verification;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserTest {

    private static UserService service;

    private static UserRepository repository;
    private static StaffService staffService;
    private static SecurityProperties properties;
    private static Algorithm algorithm;
    private static JWTVerifier verifier;
    private static PasswordEncoder passwordEncoder;

    Address testResidence = new Address(0L, "via Roma", "1", "Roma", "RM", "00159", "Italy");
    Address testDomicile = new Address(1L, "via Roma", "1", "Roma", "RM", "00159", "Italy");

    Person testPerson = Staff.builder()
            .id(0L)
            .firstName("firstName")
            .lastName("lastName")
            .birthDate(LocalDate.parse("1999-09-09"))
            .telephoneNumber("+393333333333")
            .taxCode("MMMDDD99K09J092X")
            .residence(testResidence)
            .domicile(testDomicile)
            .user(null)
            .build();

    User testUser = new User(0L, "email", "password", new HashSet<>(List.of("ADMIN")), testPerson);

    @BeforeAll
    static void init() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        repository = mock(UserRepository.class);
        staffService = mock(StaffService.class);
        properties = new SecurityProperties(10, "secret", false);
        algorithm = Algorithm.HMAC256("secret");

        Class<JWTVerifier> jwtVerifierClass = JWTVerifier.class;
        Method method = jwtVerifierClass.getDeclaredMethod("init", Algorithm.class);
        method.setAccessible(true);
        verifier = ((Verification)method.invoke(null, algorithm)).build();

        passwordEncoder = mock(PasswordEncoder.class);

        service = new UserService(repository, staffService, properties, algorithm, verifier, passwordEncoder);
    }

    @Test
    void loadUserByUsernameTest() {
        when(repository.findByEmail(anyString())).thenReturn(Optional.of(testUser));

        JWTUserDetails userDetails = service.loadUserByUsername(testUser.getEmail());

        assertEquals(testUser.getEmail(), userDetails.getUsername());
    }

    @Test
    void loadUserByTokenTest() {
        when(repository.findByEmail(anyString())).thenReturn(Optional.of(testUser));

        String token = service.getToken(testUser);
        JWTUserDetails userDetails = service.loadUserByToken(token);

        assertEquals(testUser.getEmail(), userDetails.getUsername());
    }

    @Test
    void getCurrentUserTest() {
        User user = service.getCurrentUser();

        assertNull(user);
    }

    @Test
    void createUsertest() {
        when(repository.existsById(anyLong())).thenReturn(false);
        when(repository.saveAndFlush(any())).thenAnswer(i -> i.getArguments()[0]);

        Person personInput = testPerson;
        CreateUserInput userInput = new CreateUserInput("email", "password");

        User user = service.createUser(personInput, userInput);

        assertNotNull(user);
        assertEquals("email", user.getEmail());
        assertEquals("encodedPassword", user.getPassword());
        assertTrue(user.getRoles().contains("STAFF"));
    }

    @Test
    void updatePasswordTest() {
        when(repository.findById(anyLong())).thenReturn(Optional.ofNullable(testUser));
        when(repository.saveAndFlush(any())).thenAnswer(i -> i.getArguments()[0]);
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
        when(passwordEncoder.encode(anyString())).thenReturn("newEncodedPassword");

        UpdatePasswordInput passwordInput = new UpdatePasswordInput("password", "newPassword");

        User user = service.updatePassword(0L, passwordInput);

        assertNotNull(user);
        assertEquals("email", user.getEmail());
        assertEquals("newEncodedPassword", user.getPassword());
    }

    @Test
    void getTokenTest() {
        String token = service.getToken(testUser);

        Optional<DecodedJWT> decodedJWT = ReflectionTestUtils.invokeMethod(service, "getDecodedToken", token);

        assertEquals(testUser.getEmail(), decodedJWT.get().getSubject());
    }

    @Test
    void isAdminTest() {
        boolean isAdmin = service.isAdmin();

        assertEquals(false, isAdmin);
    }

    @Test
    void hasAuthorityTest() {
        boolean hasAuthority = service.hasAuthority("ADMIN");

        assertEquals(false, hasAuthority);
    }

    @Test
    void isAuthenticatedTest() {
        boolean isAuthenticated = service.isAuthenticated();

        assertEquals(false, isAuthenticated);
    }

    @Test
    void deleteUserTest() {
        when(repository.existsById(anyLong())).thenReturn(true);

        boolean deleteUser = service.deleteUser(0L);

        assertEquals(true, deleteUser);
    }

    @Test
    void promoteStaffTest() {
        Staff staff = Staff.builder().user(testUser).build();

        when(staffService.getStaffById(anyLong())).thenReturn(staff);

        Staff promotedStaff = service.promoteStaff(0L);

        assertTrue(promotedStaff.getUser().getRoles().contains("ADMIN"));
    }

    @Test
    void unpromoteStaffTest() {
        Staff staff = Staff.builder().user(testUser).build();
        staff.getUser().withRole("ADMIN");

        when(staffService.getStaffById(anyLong())).thenReturn(staff);

        Staff promotedStaff = service.unpromoteStaff(0L);

        assertFalse(promotedStaff.getUser().getRoles().contains("ADMIN"));
    }
}
