package com.Damien.springbootAPI.controller;

import com.Damien.springbootAPI.Application;
import com.Damien.springbootAPI.model.Question;
import org.json.JSONException;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.test.context.junit4.SpringRunner;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SurveyControllerTest {

    @LocalServerPort
    private int port;

    TestRestTemplate restTemplate = new TestRestTemplate();
    HttpHeaders headers = createHttpHeaders("user1","secret1");

    private HttpHeaders createHttpHeaders(String userId, String password) {
        HttpHeaders headers = new HttpHeaders();
        String auth = userId + ":" + password;

        byte[] encodedAuth = Base64.encode(auth.getBytes(Charset
                .forName("US-ASCII")));

        String headerValue = "Basic " + new String(encodedAuth);

        headers.add("Authorization", headerValue);

        return headers;
    }

    @Before
    public void before() {
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testJsonAssert() throws JSONException {
        JSONAssert.assertEquals("{id:1}","{id:1,name:Damien}", false);
    }

    @Test
    public void addQuestion(){

        Question question = new Question("DOESNTMATTER", "Question1", "Russia",
                Arrays.asList("India", "Russia", "United States", "China"));

        HttpEntity entity = new HttpEntity<Question>(question, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/surveys/Survey1/questions"),
                HttpMethod.POST, entity, String.class);

        String actual = response.getHeaders().get(HttpHeaders.LOCATION).get(0);

        assertTrue(actual.contains("/surveys/Survey1/questions/"));
    }

    @Test
    public void testRetrieveSurveyQuestion() throws JSONException {

        HttpEntity<String> entity = new HttpEntity<String>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/surveys/Survey1/questions/Question1"),
                HttpMethod.GET, entity, String.class);

        String expected = "{'id':'Question1','description':'Largest Country in the World','correctAnswer':'Russia'}";

        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Test
    public void retrieveAllSurveyQuestions() throws Exception {

        ResponseEntity<List<Question>> response = restTemplate.exchange(
                createURLWithPort("/surveys/Survey1/questions"),
                HttpMethod.GET, new HttpEntity<String>("DUMMY_DOESNT_MATTER",
                        headers),
                new ParameterizedTypeReference<List<Question>>() {
                });

        Question sampleQuestion = new Question("Question1",
                "Largest Country in the World", "Russia", Arrays.asList(
                "India", "Russia", "United States", "China"));

        assertTrue(response.getBody().contains(sampleQuestion));
    }

    private String createURLWithPort(final String uri) {
        return "http://localhost:" + port + uri;
    }

}