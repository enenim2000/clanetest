package com.clane.test.controller;

import com.clane.test.dto.request.SignInRequest;
import com.clane.test.dto.request.SignUpRequest;
import com.clane.test.dto.response.AuthorResponse;
import com.clane.test.dto.response.JwtResponse;
import com.clane.test.security.JwtTokenUtil;
import com.clane.test.security.JwtUserDetailsService;
import com.clane.test.service.AuthorService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Validated
@CrossOrigin
@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AuthorService authorService;

    private final JwtUserDetailsService userDetailsService;

    private final AuthenticationManager authenticationManager;

    private final JwtTokenUtil jwtTokenUtil;

    public AccountController(AuthorService authorService,
                             JwtUserDetailsService userDetailsService,
                             AuthenticationManager authenticationManager,
                             JwtTokenUtil jwtTokenUtil) {
        this.authorService = authorService;
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PostMapping("/sign-up")
    public AuthorResponse signUp(@Valid @RequestBody SignUpRequest request) {
        return authorService.saveAuthor(request);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<?> signIn(@Valid @RequestBody SignInRequest request) throws Exception {
        authenticate(request.getUsername(), request.getPassword());
        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}