package com.example.mock.controller;

import com.example.mock.properties.UrlProperties;
import com.example.mock.resource.Temperatura;
import com.example.mock.resource.TemperaturaComponent;
import com.example.mock.to.Root;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/temp")
@AllArgsConstructor
public class TemperaturaController implements TemperaturaControllerSwagger{

    private final Temperatura temperatura;

    @Autowired
    private TemperaturaComponent temperaturaComponent;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{regiao}")
    public Root bucarTemperatura(@PathVariable String regiao){ return temperatura.pesquisaTemperatura(regiao); }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/template/{regiao}")
    Root bucarTemperaturaComp(@PathVariable String regiao){
        return temperaturaComponent.chamadaTemperatura(regiao);
    }
}
