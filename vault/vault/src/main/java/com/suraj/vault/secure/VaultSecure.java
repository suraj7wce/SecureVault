package com.suraj.vault.secure;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class VaultSecure {

    private static final int MAX_CHAR_VALUE = 256;

    public String encrypt(String text, String key) {
        validateInput(text, key);

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            char k = key.charAt(i % key.length());
            result.append((char) ((c + k) % MAX_CHAR_VALUE));
        }
        return result.toString();
    }

    public String decrypt(String text, String key) {
        validateInput(text, key);

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            char k = key.charAt(i % key.length());
            result.append((char) ((c - k + MAX_CHAR_VALUE) % MAX_CHAR_VALUE));
        }
        return result.toString();
    }

    private void validateInput(String text, String key) {
        if (text == null || key == null || key.isEmpty()) {
            throw new IllegalArgumentException("Text and key must not be null or empty");
        }
    }
}
