package com.typeb.webapp;

import org.springframework.stereotype.Service;

/**
 * Business logic for the /hello-world endpoint.
 *
 * <p>A name is considered valid when its first character (ignoring case) falls
 * in the range A–M. Any other first character — including digits, symbols, or
 * letters N–Z — is treated as invalid. A null, empty, or blank name is also
 * invalid.
 */
@Service
public class HelloWorldService {

    /**
     * Returns {@code true} when {@code name} starts with a letter in A–M (case-insensitive).
     */
    public boolean isValidName(String name) {
        if (name == null || name.isBlank()) {
            return false;
        }
        char first = Character.toLowerCase(name.strip().charAt(0));
        return first >= 'a' && first <= 'm';
    }

    /**
     * Capitalises the first letter and lowercases the rest, then returns
     * the greeting string.
     *
     * <p>For example: {@code "alice"} → {@code "Hello Alice"}.
     */
    public String buildGreeting(String name) {
        String stripped = name.strip();
        String capitalised = Character.toUpperCase(stripped.charAt(0)) + stripped.substring(1).toLowerCase();
        return "Hello " + capitalised;
    }
}
