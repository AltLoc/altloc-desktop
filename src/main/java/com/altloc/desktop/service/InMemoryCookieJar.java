package com.altloc.desktop.service;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * In-memory implementation of the CookieJar interface.
 * Stores cookies in memory for each host and allows retrieving them for
 * requests.
 */
public class InMemoryCookieJar implements CookieJar {
    // A map to store cookies by host
    private final Map<String, List<Cookie>> cookieStore = new HashMap<>();

    /**
     * Saves cookies from a response for a given URL.
     * 
     * @param url     The URL from which the cookies were received.
     * @param cookies The cookies to be saved.
     */
    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        cookieStore.put(url.host(), cookies);
        // Log the saved cookies
        System.out.println("Saved cookies for " + url.host() + ": " + cookies);
    }

    /**
     * Loads the cookies for a given URL for the request.
     * 
     * @param url The URL for which cookies are required.
     * @return A list of cookies for the URL. If no cookies are found, returns an
     *         empty list.
     */
    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        List<Cookie> cookies = cookieStore.get(url.host());
        // Log the loaded cookies
        if (cookies != null) {
            System.out.println("Loaded cookies for " + url.host() + ": " + cookies);
        } else {
            System.out.println("No cookies found for " + url.host());
        }
        return cookies != null ? cookies : new ArrayList<>();
    }

    /**
     * Clears all cookies stored in the cookie jar.
     */
    public void clear() {
        cookieStore.clear();
    }

}
