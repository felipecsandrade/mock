package com.example.mock.resource;

import com.example.mock.properties.UrlProperties;
import com.example.mock.to.Data;
import com.example.mock.to.Root;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.runner.RunWith;
import org.mockserver.client.MockServerClient;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.Header;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

import static org.junit.Assert.assertEquals;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.matchers.Times.exactly;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE,

        classes = TemperaturaComponent.class)
@EnableConfigurationProperties(value = UrlProperties.class)
public class TemperaturaComponent2Test {


    @Autowired
    private TemperaturaComponent client;


    private static ClientAndServer mockServer;


    @BeforeClass

// @Before for JUnit 4
   public static void setUp() throws JsonProcessingException {
        mockServer = startClientAndServer(8083);
        createExpectationForInvalidAuth();


    }

    private static void createExpectationForInvalidAuth() throws JsonProcessingException {
        Root input = getRootTemplate();
        ObjectMapper object = new ObjectMapper();

        mockServer.when(
                        request()
                                .withMethod("GET")
                                .withPath("/tempo-api/temperatura/Norte")
                                .withHeader("Accept", "application/json, application/*+json")
                       ,exactly(1)
                )
                .respond(
                        response()
                                .withStatusCode(200)
                                .withHeaders(
                                        new Header("Content-Type", String.valueOf(MediaType.APPLICATION_JSON))
                                        )
                                .withBody(object.writeValueAsString(input))
                )

        ;

        input.getData().setTemperatura(20.0);
        mockServer.when(
                request()
                        .withMethod("GET")
                        .withPath("/tempo-api/temperatura/Sul")
                        .withHeader("Accept", "application/json, application/*+json")
                        ,exactly(1)
        )
                .respond(
                        response()
                                .withStatusCode(200)
                                .withHeaders(
                                        new Header("Content-Type", String.valueOf(MediaType.APPLICATION_JSON))
                                )
                                .withBody(object.writeValueAsString(input))
                )

        ;

    }


    @AfterClass
    public static void stopServer() {
        mockServer.stop();
    }


    @Test
    public void whenCallingGetUserDetails_thenClientMakesCorrectCall()
            throws Exception {

        Root input = getRootTemplate();

        Root root  = this.client.chamadaTemperatura("Norte");
        Root root2  = this.client.chamadaTemperatura("Sul");
        assertEquals(input.getData().getTemperatura(), root.getData().getTemperatura(),0.0);
        assertEquals(20.0, root2.getData().getTemperatura(),0.0);



    }

    public static Root getRootTemplate(){
        return Root.builder().data(Data.builder().temperatura(11.0).regiao("Sul").build()).build();
    }


}





