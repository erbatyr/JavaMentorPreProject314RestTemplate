package com.erbatyr.resttemplate.controllers;

import com.erbatyr.resttemplate.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.Cookie;
import java.util.ArrayList;
import java.util.Arrays;


@RestController
public class RestTemplateRest {
    @Autowired
    RestTemplate restTemplate;

    String url = "http://91.241.64.178:7081/api/users";

    String session = "";

    @RequestMapping(value = "/users")
    public String getUserList() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(headers);

        if (session.isEmpty()==true) {
            ResponseEntity<String> forEntity = restTemplate.getForEntity(url, String.class);
            for (String s : forEntity.getHeaders().get("Set-Cookie")) {
                session += s;
            }
        }

        return restTemplate.exchange(url, HttpMethod.GET, entity, String.class).getBody();
    }

    @PostMapping(value = "/users")
    public String postUsers(@RequestBody User user) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.set("Cookie", session);
        HttpEntity<User> entity = new HttpEntity<>(user, headers);

        return restTemplate.exchange(url, HttpMethod.POST, entity, String.class).getBody()  ;
    }

    @PutMapping(value = "/user/{id}")
    public String updateProduct(@PathVariable("id") String id, @RequestBody User user) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.set("Cookie", session);
        HttpEntity<User> entity = new HttpEntity<>(user, headers);

        return restTemplate.exchange(url, HttpMethod.PUT, entity, String.class).getBody();
    }

    @DeleteMapping(value = "/user/{id}")
    public String deleteProduct(@PathVariable("id") String id) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.set("Cookie", session);

        HttpEntity<User> entity = new HttpEntity<>(headers);

        return restTemplate.exchange(url, HttpMethod.DELETE, entity, String.class).getBody();
    }
}
