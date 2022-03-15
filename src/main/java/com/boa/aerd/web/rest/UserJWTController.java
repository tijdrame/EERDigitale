package com.boa.aerd.web.rest;

import com.boa.aerd.security.jwt.JWTFilter;
import com.boa.aerd.security.jwt.TokenProvider;
import com.boa.aerd.web.rest.vm.LoginVM;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.Api;

import java.time.Instant;

import javax.validation.Valid;

/**
 * Controller to authenticate users.
 */
@RestController
@RequestMapping("/eerd/api")
@Api(description = "Entrée en Relation Digitale", tags = "EERD.")
public class UserJWTController {

    private final Logger log = LoggerFactory.getLogger(UserJWTController.class);

    private final TokenProvider tokenProvider;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    public UserJWTController(TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder) {
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<JWTToken> authorize(@Valid @RequestBody LoginVM loginVM) {
        log.debug("REST request to authenticate a User : {}", loginVM);
        HttpHeaders httpHeaders = new HttpHeaders();
        try {
            UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginVM.getUsername(), loginVM.getPassword());
            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            boolean rememberMe = (loginVM.isRememberMe() == null) ? false : loginVM.isRememberMe();
            String jwt = tokenProvider.createToken(authentication, rememberMe);
            
            httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
            JWTToken token = new JWTToken(jwt);
            token.setDescription("Authentification réussie");
            token.setDateResponse(Instant.now());
            token.setCode("200");
            return new ResponseEntity<>(token, httpHeaders, HttpStatus.OK);
        } catch (Exception e) {
            JWTToken token = new JWTToken(null);
            token.setDescription("Authentification échouée");
            token.setDateResponse(Instant.now());
            token.setCode("402");
            return new ResponseEntity<>(token, httpHeaders, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Object to return as body in JWT Authentication.
     */
    static class JWTToken {

        private String idToken;
        private String code;
        private String description;
        private Instant dateResponse;

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

        @JsonProperty("code")
        String getCode() {
            return code;
        }

        void setCode(String code) {
            this.code = code;
        }

        @JsonProperty("description")
        String getDescription() {
            return description;
        }

        void setDescription(String description) {
            this.description = description;
        }

        @JsonProperty("dateResponse")
        Instant getDateResponse() {
            return dateResponse;
        }

        void setDateResponse(Instant dateResponse) {
            this.dateResponse = dateResponse;
        }

    }
}
