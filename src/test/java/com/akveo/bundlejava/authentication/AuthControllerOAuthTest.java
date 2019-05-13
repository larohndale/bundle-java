package com.akveo.bundlejava.authentication;

import java.util.Locale;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AuthControllerOAuthTest {

    public static final String EXISTING_EMAIL = "admin@admin.com";
    public static final String EXISTING_PASSWORD = "password";
    public static final String NEW_EMAIL = "user123@user.com";
    public static final String NEW_PASSWORD = "1234";
    public static final String USERNAME = "user123";

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setUp() {
        Locale.setDefault(Locale.US);
    }

    @Test
    public void loginTest() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        LoginDTO request = new LoginDTO(EXISTING_EMAIL, EXISTING_PASSWORD);
        HttpEntity<LoginDTO> entity = new HttpEntity<>(request, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(
            "/auth/login", entity, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("access_token", "refresh_token", "expires_in");
    }

    @Test
    public void signUpTest() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        SignUpDTO request = new SignUpDTO(
            USERNAME, NEW_EMAIL, NEW_PASSWORD, NEW_PASSWORD);
        HttpEntity<SignUpDTO> entity = new HttpEntity<>(request, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(
            "/auth/sign-up", entity, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("access_token", "refresh_token", "expires_in");
    }

    @Test
    public void refreshTokenTest() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Token currentToken = refreshTokenDTO().getToken();
        RefreshTokenDTO requestToken = new RefreshTokenDTO(
            new Token(0L, currentToken.getAccessToken(), currentToken.getRefreshToken()));

        HttpEntity<RefreshTokenDTO> entity = new HttpEntity<>(refreshTokenDTO(), headers);

        ResponseEntity<String> response = restTemplate.postForEntity(
            "/auth/refresh-token", entity, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("access_token", "refresh_token", "expires_in");
    }

    private RefreshTokenDTO refreshTokenDTO() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        LoginDTO request = new LoginDTO(EXISTING_EMAIL, EXISTING_PASSWORD);
        HttpEntity<LoginDTO> entity = new HttpEntity<>(request, headers);

        ResponseEntity<RefreshTokenDTO> response = restTemplate.postForEntity(
            "/auth/login", entity, RefreshTokenDTO.class);
        return response.getBody();
    }
}
