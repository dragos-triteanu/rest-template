package com.enginizer.resttemplate.controller;

import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("${app.baseURL}/i18n")
public class I18nController {

    @Autowired
    MessageSource messageSource;

    @GetMapping()
    public String message(
            @RequestHeader(value = "Accept-Language", required = false) Locale locale) {
        return messageSource.getMessage("some.message", null, locale);
    }
}
