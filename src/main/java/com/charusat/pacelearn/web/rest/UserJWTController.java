package com.charusat.pacelearn.web.rest;

import com.charusat.pacelearn.web.rest.errors.InvalidPasswordException;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.charusat.pacelearn.security.jwt.JWTFilter;
import com.charusat.pacelearn.security.jwt.TokenProvider;
import com.charusat.pacelearn.web.rest.vm.LoginVM;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * Controller to authenticate users.
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class UserJWTController {

    private final TokenProvider tokenProvider;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;


//    private final AuthenticationManager authenticationManager;

    public UserJWTController(TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder) {
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
    }


    /**
     * {@code POST  /authenticate} : register the user.
     *
     * @param loginVM the managed user View Model.
     * @throws InvalidPasswordException {@code 400 (Bad Request)} if the password is incorrect.
     */
    @PostMapping("/authenticate")
    public ResponseEntity<JWTToken> authorize(@Valid @RequestBody LoginVM loginVM){
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
            loginVM.getUsername(),
            loginVM.getPassword()
        );


        System.out.println("HAHA in authenticate api endpoint");
        System.out.println("USERNAME is --> "+ loginVM.getUsername());
        System.out.println("Token is is --> "+ authenticationToken);
//        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        System.out.println("HAHA in authenticate api endpoint1");
        SecurityContextHolder.getContext().setAuthentication(authentication);
        System.out.println("HAHA in authenticate api endpoint2");
        String jwt = tokenProvider.createToken(authentication, loginVM.isRememberMe());
        System.out.println("HAHA in authenticate api endpoint3");
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
//        httpHeaders.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN,"http://localhost:3000");
//        httpHeaders.add(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS,"Content-Type");
        return new ResponseEntity<>(new JWTToken(jwt), httpHeaders, HttpStatus.OK);
    }

    /**
     * Object to return as body in JWT Authentication.
     */
    static class JWTToken {

        private String idToken;

        JWTToken(String idToken) {
            this.idToken = idToken;
        }

        @JsonProperty("id_token")
        String getIdToken() {
            return idToken;
        }

        void setIdToken(String idToken) {
            this.idToken = idToken;
        }
    }
}
