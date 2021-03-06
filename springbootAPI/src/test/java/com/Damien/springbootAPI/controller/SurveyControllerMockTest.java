package com.Damien.springbootAPI.controller;

import com.Damien.springbootAPI.Application;
import com.Damien.springbootAPI.model.Question;
import com.Damien.springbootAPI.service.SurveyService;
import org.json.JSONException;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@WebMvcTest(value = SurveyController.class)
class SurveyControllerMockTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SurveyService surveyService;

    @Test
    @WithMockUser(username="admin1", roles={"USER","ADMIN"})
    public void retrieveDetailsForQuestion() throws Exception {
        Question mockQuestion = new Question("Question1",
                "Largest Country in the World", "Russia", Arrays.asList(
                "India", "Russia", "United States", "China"));

        Mockito.when(surveyService.retrieveQuestion(Mockito.anyString(),
                Mockito.anyString())).thenReturn(mockQuestion);

        RequestBuilder requestBuilder =
                MockMvcRequestBuilders.get("/surveys/Survey1/questions/Question1")
                        .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        String expected = "{'id':'Question1','description':'Largest Country in the World','correctAnswer':'Russia'}";

        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);

    }

    @Test
    @WithMockUser(username="admin1", roles={"USER","ADMIN"})
    public void createSurveyQuestion() throws Exception {
        Question mockQuestion = new Question("1", "Smallest Number", "1",
                Arrays.asList("1","2","3","4"));

        String question = "{\"description\":\"Smallest Number\",\"correctAnswer\":\"1\",\"options\":[\"1\",\"2\",\"3\",\"4\"]}";

        Mockito.when(surveyService.addQuestion(Mockito.anyString(),
                Mockito.any(Question.class))).thenReturn(mockQuestion);

        RequestBuilder requestBuilder =
                MockMvcRequestBuilders.post("/surveys/Survey1/questions")
                        .accept(MediaType.APPLICATION_JSON).content(question).contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());

        assertEquals("http://localhost/surveys/Survey1/questions/1", response.getHeader(HttpHeaders.LOCATION));

    }

}