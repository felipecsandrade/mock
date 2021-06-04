package com.example.mock.resource;

import com.example.mock.to.Data;
import com.example.mock.to.Root;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;


import java.net.URI;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RunWith(SpringRunner.class)
@RestClientTest(TemperaturaComponent.class)
@AutoConfigureWebClient(registerRestTemplate = true)
public class TemperaturaComponentTest {


    @Autowired
    private TemperaturaComponent client;


    private MockRestServiceServer server;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    /*@Before
    public void setUp() throws Exception {

        /*Root root = Root.builder().data(Data.builder().temperatura(11.0).regiao("Sul").build()).build();
        String detailsString =
                objectMapper.writeValueAsString(root);

        this.server.expect(requestTo("/john/details"))
                .andRespond(withSuccess(detailsString, MediaType.APPLICATION_JSON));
    }*/

    @Before
// @Before for JUnit 4
   public void setUp() {

        server= MockRestServiceServer.createServer(restTemplate);

    }

    @AfterEach
// @After for JUnit 4
    void tearDown() {
        this.server.verify();
    }


    @Test
    public void whenCallingGetUserDetails_thenClientMakesCorrectCall()
            throws Exception {

        Root input = Root.builder().data(Data.builder().temperatura(11.0).regiao("Sul").build()).build();





        String json = this.objectMapper
                .writeValueAsString(input);

        /*this.server
                .expect(requestTo("/temperatura?regiao=Norte"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(json, MediaType.APPLICATION_JSON).body(objectMapper.writeValueAsString(input)));*/


        server.expect(ExpectedCount.once(),
                requestTo(new URI("http://juliodelima.com.br/tempo-api/temperatura?regiao=Norte")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(objectMapper.writeValueAsString(input))
                );


        Root root  = this.client.chamadaTemperatura("Centro-Oeste");
        String t = root.getData().getRegiao();

       // server.verify();
        assertEquals(11.0, root.getData().getTemperatura(),0.0);


        //assertThat("root").isEqualTo("Sul");
        //assertThat(details.getName()).isEqualTo("John Smith");
/*
        Root root = Root.builder().data(Data.builder().temperatura(11.0).regiao("Sul").build()).build();

        mockServer.expect(ExpectedCount.once(), requestTo("http://localhost:8080"))
                .andRespond(withSuccess("{message : 'under construction'}", MediaType.APPLICATION_JSON));*/

      //  Root result = temperaturaComponent.chamadaTemperatura("Norte");
       // System.out.println("testGetRootResourceOnce: " + result);

    }


}





