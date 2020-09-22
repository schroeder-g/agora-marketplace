package com.lambdaschool.african_market_place.controllers;

        import com.lambdaschool.african_market_place.models.User;
        import com.lambdaschool.african_market_place.models.UserMinimum;
        import com.lambdaschool.african_market_place.models.UserRoles;
        import com.lambdaschool.african_market_place.services.RoleService;
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
        import java.util.HashSet;
        import java.util.List;
        import java.util.Set;

@RestController
public class OpenController {
        @Autowired
        private UserService userService;

        @Autowired
        private RoleService roleService;
        // Sprint Challenge also wired in roleService, but I hard-coded everyone's role, so I'm skipping that
    /*
    Allows anyone to create an account
    Route: http://localhost:5280/register
    */
//    @PostMapping(value = "/register", consumes = "application/json", produces = "application/json")
//    public ResponseEntity<?> addNewAccount(HttpServletRequest httpServletRequest,
//                                           @Valid @RequestBody UserMinimum newminuser) throws URISyntaxException {
//        User createdUser = new User();
//        createdUser.setUserid(0);
//        createdUser.setUsername(newminuser.getUsername());
//        createdUser.setPassword(newminuser.getPassword());
//        //No default Roles need to be added
//        createdUser = userService.save(createdUser);
//        HttpHeaders responseHeaders = new HttpHeaders();
//        URI createdUserURI = ServletUriComponentsBuilder.fromUriString(httpServletRequest.getServerName() + ":" +
//                httpServletRequest.getLocalPort() + "/users/{userid}")
//                .buildAndExpand(createdUser.getUserid())
//                .toUri();
//        responseHeaders.setLocation(createdUserURI);
//        String port = "";
//        if(httpServletRequest.getServerName().equalsIgnoreCase("localhost")) {
//            port = ":" + httpServletRequest.getLocalPort();
//        }
//        System.out.println(httpServletRequest.getServerName());
//        String requestURI = "http://" + httpServletRequest.getServerName() + port +"/login";
//        List<MediaType> acceptableMediaTypes = new ArrayList<>();
//        acceptableMediaTypes.add(MediaType.APPLICATION_JSON);
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//        headers.setAccept(acceptableMediaTypes);
//        headers.setBasicAuth(System.getenv("lambda-client"), System.getenv("lambda-secret"));
//        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
//        map.add("grant_type", "password");
//        map.add("scope", "read write trust");
//        map.add("username", createdUser.getUsername());
//        map.add("password", createdUser.getPassword());
//        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
//        RestTemplate restTemplate = new RestTemplate();
//        String theToken = restTemplate.postForObject(requestURI, request, String.class);
//        return new ResponseEntity<>(theToken, responseHeaders, HttpStatus.CREATED);
//    }

        @PostMapping(
                value = "/createnewuser",
                consumes = {"application/json"},
                produces = {"application/json"}
        )
        public ResponseEntity<?> addSelf(
                HttpServletRequest httpServletRequest,
                @Valid @RequestBody UserMinimum userMinimum
        ) {
                System.out.println(userMinimum);
                System.out.println(userMinimum.getUsername());
                // Create the user
                User newUser = new User();
                newUser.setUsername(userMinimum.getUsername());
                newUser.setPassword(userMinimum.getPassword());
                ;

                // add the default role of user
                Set<UserRoles> newRoles = new HashSet<>();
                newRoles.add(new UserRoles(newUser, roleService.findByName("user")));
                newUser.setRoles(newRoles);

                newUser = userService.save(newUser);

                // set the location header for the newly created resource
                // The location comes from a different controller!
                HttpHeaders responseHeaders = new HttpHeaders();
                URI newUserURI = ServletUriComponentsBuilder
                        .fromUriString(
                                httpServletRequest.getServerName() +
                                        ":" +
                                        httpServletRequest.getLocalPort() +
                                        "/users/user/{userId}"
                        )
                        .buildAndExpand(newUser.getUserid())
                        .toUri();
                responseHeaders.setLocation(newUserURI);

                // return the access token
                // To get the access token, surf to the endpoint /login just as if a client had done this.
                RestTemplate restTemplate = new RestTemplate();
                String requestURI =
                        "http://" +
                                httpServletRequest.getServerName() +
                                ":" +
                                httpServletRequest.getLocalPort() +
                                "/login";

                List<MediaType> acceptableMediaTypes = new ArrayList<>();
                acceptableMediaTypes.add(MediaType.APPLICATION_JSON);

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                headers.setAccept(acceptableMediaTypes);
                headers.setBasicAuth(
                        System.getenv("CLIENT_ID"),
                        System.getenv("CLIENT_SECRET")
                );

                MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
                map.add("grant_type", "password");
                map.add("scope", "read write trust");
                map.add("username", userMinimum.getUsername());
                map.add("password", userMinimum.getPassword());

                HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(
                        map,
                        headers
                );

                String theToken = restTemplate.postForObject(
                        requestURI,
                        request,
                        String.class
                );
                return new ResponseEntity<>(theToken, responseHeaders, HttpStatus.CREATED);

        }
}