package com.example.urlshortener.service;

import com.example.urlshortener.Commons.Constants;
import org.springframework.stereotype.Component;
import java.security.SecureRandom;

@Component
public class ShortUrlGenerator {

    private static final int leftLimitInASCII = 48; //0
    private static final int rightLimitInASCII = 123; //z
    private static final int leftExcludeLimitInAscii = 57; //9
    private static final int rightExcludeLimitInASCII = 97; //a

    public String generateShortUrl() {
        return new SecureRandom().ints(leftLimitInASCII, rightLimitInASCII)
                .filter(i -> (i <= leftExcludeLimitInAscii || i >= rightExcludeLimitInASCII))
                .mapToObj(i -> (char) i)
                .limit(Constants.stringLength)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
    }
}
