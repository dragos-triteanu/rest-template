package com.enginizer.rest.handler;

import java.util.Date;
import java.util.Locale;

/**
 * Exception response entity that allows for various flavors of configuration.
 * This is also an example of the Builder Pattern
 */
public class ExceptionResponse {

    private final String message;
    private final Date timestamp;
    private final String path;
    private final String details;
    private final Locale locale;

    private ExceptionResponse(Builder builder) {
        message = builder.message;
        timestamp = builder.timestamp;
        path = builder.path;
        details = builder.details;
        locale = builder.locale;
    }

    public static class Builder {

        //Required params
        private final String message;

        //Optional params
        private Date timestamp;
        private String path;
        private String details;
        private Locale locale;

        public Builder(String message) {
            this.message = message;
        }

        public Builder withTimestamp(Date timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Builder withPath(String path) {
            this.path = path;
            return this;
        }

        public Builder withDetails(String details) {
            this.details = details;
            return this;
        }

        public Builder withLocale(Locale locale) {
            this.details = details;
            return this;
        }

        public ExceptionResponse build() {
            return new ExceptionResponse(this);
        }

    }

    public Locale getLocale() {
        return locale;
    }

    public String getMessage() {
        return message;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getPath() {
        return path;
    }

    public String getDetails() {
        return details;
    }
}
