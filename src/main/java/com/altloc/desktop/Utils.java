package com.altloc.desktop;

import java.net.URI;
import java.net.URISyntaxException;

public class Utils {

    private static final String CDN_URL = "http://localhost:9000/altloc/";

    /**
     * Returns the full URL for an image on the CDN.
     *
     * @param key The image key.
     * @return The full image URL as a string.
     * @throws URISyntaxException       If the key or base URL is invalid.
     * @throws IllegalArgumentException If the key is null or empty.
     */
    public static String getCDNImageURL(String key) throws URISyntaxException {
        if (key == null || key.isBlank()) {
            throw new IllegalArgumentException("Image key cannot be null or empty");
        }

        URI baseUri = new URI(CDN_URL);
        URI fullUri = baseUri.resolve(key);
        return fullUri.toString();
    }
}
