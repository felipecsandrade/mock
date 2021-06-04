package com.example.mock.resource;

import com.example.mock.to.Root;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "temperatura", url = "http://juliodelima.com.br/tempo-api")
public interface Temperatura {

    @GetMapping("/temperatura/{regiao}")
    public Root pesquisaTemperatura(@PathVariable("regiao") String regiao);

}
