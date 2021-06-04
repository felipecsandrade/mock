package com.example.mock.controller;

import com.example.mock.to.Root;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.PathVariable;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;

@Api(tags = "Temperatura")
public interface TemperaturaControllerSwagger {

    @ApiOperation(value = "Consulta temperatura",
    notes = "Consulta temperatura por região. <b>regiao</b> nome da região pesquisada")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 500, message = "Forbidden")
    })
    Root bucarTemperatura(String regiao);

}
