package com.akveo.bundlejava.authentication;

import com.akveo.bundlejava.authentication.resetpassword.RequestPasswordDTO;
import com.akveo.bundlejava.authentication.resetpassword.ResetPasswordDTO;
import java.util.Locale;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Before;
import org.junit.Ignore;
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
public class AuthControllerPasswordTest {

    public static final String EXISTING_EMAIL = "admin@admin.com";
    public static final String EXISTING_PASSWORD = "password";
    public static final String NEW_PASSWORD = "1234";
    public static final String AUTH_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setUp() {
        Locale.setDefault(Locale.US);
    }


    @Test
    public void refreshTokenTest() {
        RefreshTokenDTO login = login();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(AUTH_HEADER, TOKEN_PREFIX + login.getToken().getAccessToken());

        ResetPasswordDTO resetPasswordDTO = new ResetPasswordDTO(NEW_PASSWORD);
        HttpEntity<ResetPasswordDTO> entity = new HttpEntity<>(resetPasswordDTO, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(
            "/auth/reset-pass", entity, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void requestPassTest() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        RequestPasswordDTO requestPasswordDTO = new RequestPasswordDTO(EXISTING_EMAIL);
        HttpEntity<RequestPasswordDTO> entity = new HttpEntity<>(requestPasswordDTO, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(
            "/auth/request-pass", entity, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Ignore("It's not so clear how it should works")
    @Test
    public void restorePassTest() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        RequestPasswordDTO requestPasswordDTO = new RequestPasswordDTO(EXISTING_EMAIL);
        HttpEntity<RequestPasswordDTO> entity = new HttpEntity<>(requestPasswordDTO, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(
            "/auth/restore-pass", entity, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    private RefreshTokenDTO login() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        LoginDTO request = new LoginDTO(EXISTING_EMAIL, EXISTING_PASSWORD);
        HttpEntity<LoginDTO> entity = new HttpEntity<>(request, headers);

        ResponseEntity<RefreshTokenDTO> response = restTemplate.postForEntity(
            "/auth/login", entity, RefreshTokenDTO.class);
        return response.getBody();
    }
}
