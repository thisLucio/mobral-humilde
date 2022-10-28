package com.solinftec.dev8api.resource;

import com.solinftec.dev8api.service.EmailConfirmationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("confirmation")
@AllArgsConstructor
public class EmailConfirmationResource {
    private EmailConfirmationService emailConfirmationService;

    @GetMapping
    public ResponseEntity<Void> confirm(@RequestParam("token") String token) {
        emailConfirmationService.confirmEmail(token);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
