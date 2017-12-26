package com.enginizer.rest.resources;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("${app.baseURL}/i18n")
@Api(value = "I18nResource", description = "I18n API", basePath = "${app.baseURL}/i18n", tags = {
        "I18n"})
public class I18nController {

    @Autowired
    MessageSource messageSource;

    @GetMapping()
    @ApiOperation(value = "I18n", notes = "I18n message", tags = {"I18n"})
    @ApiImplicitParam(value = "Accept-Language")
    public String message(
            @RequestHeader(
                    value = "Accept-Language", required = false) String locale) {
        return messageSource.getMessage("some.message", null, Locale.forLanguageTag(locale));
    }
}
