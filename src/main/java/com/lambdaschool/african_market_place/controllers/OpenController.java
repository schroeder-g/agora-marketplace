package com.lambdaschool.african_market_place.controllers;

        import com.lambdaschool.african_market_place.models.User;
        import com.lambdaschool.african_market_place.models.UserMinimum;
        import com.lambdaschool.african_market_place.services.UserService;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.http.*;
        import org.springframework.util.LinkedMultiValueMap;
        import org.springframework.util.MultiValueMap;
        import org.springframework.web.bind.annotation.PostMapping;
        import org.springframework.web.bind.annotation.RequestBody;
        import org.springframework.web.bind.annotation.RestController;
        import org.springframework.web.client.RestTemplate;
        import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
        import javax.servlet.http.HttpServletRequest;
        import javax.validation.Valid;
        import java.net.URI;
        import java.net.URISyntaxException;
        import java.util.ArrayList;
        import java.util.List;
@RestController
public class OpenController {
    @Autowired
    private UserService userService;
    // Sprint Challenge also wired in roleService, but I hard-coded everyone's role, so I'm skipping that
    /*
    Allows anyone to create an account
    Route: http://localhost:5280/register
    */
    @PostMapping(value = "/register", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> addNewAccount(HttpServletRequest httpServletRequest,
                                           @Valid @RequestBody UserMinimum newminuser) throws URISyntaxException {
        User createdUser = new User();
        createdUser.setUserid(0);
        createdUser.setUsername(newminuser.getUsername());
        createdUser.setPassword(newminuser.getPassword());
        //No default Roles need to be added
        createdUser = userService.save(createdUser);
        HttpHeaders responseHeaders = new HttpHeaders();
        URI createdUserURI = ServletUriComponentsBuilder.fromUriString(httpServletRequest.getServerName() + ":" +
                httpServletRequest.getLocalPort() + "/users/{userid}")
                .buildAndExpand(createdUser.getUserid())
                .toUri();
        responseHeaders.setLocation(createdUserURI);
        String port = "";
        if(httpServletRequest.getServerName().equalsIgnoreCase("localhost")) {
            port = ":" + httpServletRequest.getLocalPort();
        }
        String requestURI = "http://" + httpServletRequest.getServerName() + port +"/login";
        List<MediaType> acceptableMediaTypes = new ArrayList<>();
        acceptableMediaTypes.add(MediaType.APPLICATION_JSON);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAccept(acceptableMediaTypes);
        headers.setBasicAuth(System.getenv("WUNDERID"), System.getenv("WUNDERSECRET"));
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type", "password");
        map.add("scope", "read write trust");
        map.add("username", newminuser.getUsername());
        map.add("password", newminuser.getPassword());
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        RestTemplate restTemplate = new RestTemplate();
        String theToken = restTemplate.postForObject(requestURI, request, String.class);
        return new ResponseEntity<>(theToken, responseHeaders, HttpStatus.CREATED);
    }
}