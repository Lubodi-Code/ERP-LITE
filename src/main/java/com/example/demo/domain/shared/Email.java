package com.example.demo.domain.shared;

import java.util.regex.Pattern;

public record Email(String value) {

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[a-zA-Z0-9._%+\\-]+@[a-zA-Z0-9.\\-]+\\.[a-zA-Z]{2,}$");

    public Email {
        if (value == null || value.isBlank())
            throw new IllegalArgumentException("Email must not be blank");
        if (!EMAIL_PATTERN.matcher(value).matches())
            throw new IllegalArgumentException("Invalid email format: " + value);
    }

    public static Email of(String value) {
        return new Email(value);
    }
}
