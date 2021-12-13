package io.github.felipepedrosa.springapiinternationalization.controllers;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = {"/messages", "/message"})
public class MessageController {

    private final MessageSource messageSource;

    public MessageController(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @GetMapping
    public ResponseEntity<String> getGreetingMessage(@RequestHeader String name) {
        String message = messageSource.getMessage(
                "greeting.withName",
                new Object[]{name},
                LocaleContextHolder.getLocale()
        );

        return ResponseEntity.ok(message);
    }
}
