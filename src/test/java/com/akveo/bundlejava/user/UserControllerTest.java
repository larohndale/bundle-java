package com.akveo.bundlejava.user;

import static com.akveo.bundlejava.authentication.AuthControllerOAuthTest.NEW_EMAIL;
import static com.akveo.bundlejava.authentication.AuthControllerOAuthTest.USERNAME;
import static com.akveo.bundlejava.authentication.AuthControllerPasswordTest.AUTH_HEADER;
import static com.akveo.bundlejava.authentication.AuthControllerPasswordTest.EXISTING_EMAIL;
import static com.akveo.bundlejava.authentication.AuthControllerPasswordTest.TOKEN_PREFIX;
import com.akveo.bundlejava.authentication.LoginDTO;
import com.akveo.bundlejava.authentication.RefreshTokenDTO;
import java.util.Locale;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserControllerTest {

    public static final String ADMIN_EMAIL = "admin@admin.com";
    public static final String ADMIN_PASSWORD = "password";
    public static final String EXISTING_ID = "1";
    public static final String EXISTING_ID_2 = "2";
    public static final int NEW_AGE = 999;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @Before
    public void setUp() {
        Locale.setDefault(Locale.US);
    }

    @Test
    public void getUser() {
        RefreshTokenDTO login = login();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(AUTH_HEADER, TOKEN_PREFIX + login.getToken().getAccessToken());

        ResponseEntity<UserDTO> response = restTemplate.exchange("/users/" + EXISTING_ID, HttpMethod.GET,
            new HttpEntity<>(headers), UserDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    public void updateUser() {
        RefreshTokenDTO login = login();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(AUTH_HEADER, TOKEN_PREFIX + login.getToken().getAccessToken());
        ResponseEntity<UserDTO> existingUser = restTemplate.exchange("/users/" + EXISTING_ID, HttpMethod.GET,
            new HttpEntity<>(headers), UserDTO.class);
        UserDTO updatedUser = existingUser.getBody();
        updatedUser.setAge(NEW_AGE);

        ResponseEntity<UserDTO> response = restTemplate.exchange("/users/" + EXISTING_ID, HttpMethod.PUT,
            new HttpEntity<>(updatedUser, headers), UserDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(updatedUser);
    }

    @Test
    public void deleteUser() {
        RefreshTokenDTO login = login();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(AUTH_HEADER, TOKEN_PREFIX + login.getToken().getAccessToken());
        ResponseEntity<UserDTO> existingUser = restTemplate.exchange("/users/" + EXISTING_ID, HttpMethod.GET,
            new HttpEntity<>(headers), UserDTO.class);
        UserDTO userForDelete = existingUser.getBody();

        ResponseEntity<Boolean> response = restTemplate.exchange("/users/" + EXISTING_ID, HttpMethod.DELETE,
            new HttpEntity<>(userForDelete, headers), Boolean.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(true);
        assertThat(userRepository.findById(Long.valueOf(EXISTING_ID))).isEqualTo(Optional.empty());
    }

    @Test
    public void currentUser() {
        RefreshTokenDTO login = login();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(AUTH_HEADER, TOKEN_PREFIX + login.getToken().getAccessToken());

        ResponseEntity<UserDTO> response = restTemplate.exchange("/users/current", HttpMethod.GET,
            new HttpEntity<>(headers), UserDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getEmail()).isEqualTo(EXISTING_EMAIL);
    }

    @Test
    public void updateCurrentUser() {
        RefreshTokenDTO login = login();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(AUTH_HEADER, TOKEN_PREFIX + login.getToken().getAccessToken());
        ResponseEntity<UserDTO> existingUser = restTemplate.exchange("/users/current", HttpMethod.GET,
            new HttpEntity<>(headers), UserDTO.class);
        UserDTO updatedUser = existingUser.getBody();
        updatedUser.setAge(NEW_AGE);

        ResponseEntity<UserDTO> response = restTemplate.exchange("/users/current", HttpMethod.PUT,
            new HttpEntity<>(updatedUser, headers), UserDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(updatedUser);
    }

    @Test
    public void createUser() {
        RefreshTokenDTO login = login();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(AUTH_HEADER, TOKEN_PREFIX + login.getToken().getAccessToken());

        UserDTO newUser = new UserDTO(USERNAME, NEW_EMAIL);

        ResponseEntity<UserDTO> response = restTemplate.exchange("/users", HttpMethod.POST,
            new HttpEntity<>(newUser, headers), UserDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(userRepository.findByEmail(NEW_EMAIL)).isNotEmpty();
    }

    private RefreshTokenDTO login() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        LoginDTO request = new LoginDTO(ADMIN_EMAIL, ADMIN_PASSWORD);
        HttpEntity<LoginDTO> entity = new HttpEntity<>(request, headers);

        ResponseEntity<RefreshTokenDTO> response = restTemplate.postForEntity(
            "/auth/login", entity, RefreshTokenDTO.class);
        return response.getBody();
    }
}
