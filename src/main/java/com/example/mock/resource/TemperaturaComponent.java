package com.example.mock.resource;

import com.example.mock.properties.UrlProperties;
import com.example.mock.to.Root;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@AllArgsConstructor
public class TemperaturaComponent {

    private final UrlProperties URL;

    public Root chamadaTemperatura(String regiao){
        RestTemplate template = new RestTemplate();
        return template.getForObject(URL.getPortal()+"/temperatura/"+regiao, Root.class);
    }


}
