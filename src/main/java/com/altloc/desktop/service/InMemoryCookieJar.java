// package com.altloc.desktop.service;

// import okhttp3.Cookie;
// import okhttp3.CookieJar;
// import okhttp3.HttpUrl;

// import java.util.List;
// import java.util.Map;
// import java.util.ArrayList;
// import java.util.HashMap;

// public class InMemoryCookieJar implements CookieJar {
// private final Map<String, List<Cookie>> cookieStore = new HashMap<>();

// @Override
// public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
// cookieStore.put(url.host(), cookies);
// // Логирование сохраненных cookies
// System.out.println("Saved cookies for " + url.host() + ": " + cookies);
// }

// @Override
// public List<Cookie> loadForRequest(HttpUrl url) {
// List<Cookie> cookies = cookieStore.get(url.host());
// // Логирование загруженных cookies
// if (cookies != null) {
// System.out.println("Loaded cookies for " + url.host() + ": " + cookies);
// } else {
// System.out.println("No cookies found for " + url.host());
// }
// return cookies != null ? cookies : new ArrayList<>();
// }
// }

package com.altloc.desktop.service;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryCookieJar implements CookieJar {
    private final Map<String, List<Cookie>> cookieStore = new HashMap<>();

    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        cookieStore.put(url.host(), cookies);
        // Логирование сохраненных cookies
        System.out.println("Saved cookies for " + url.host() + ": " + cookies);
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        List<Cookie> cookies = cookieStore.get(url.host());
        // Логирование загруженных cookies
        if (cookies != null) {
            System.out.println("Loaded cookies for " + url.host() + ": " + cookies);
        } else {
            System.out.println("No cookies found for " + url.host());
        }
        return cookies != null ? cookies : new ArrayList<>();
    }
}
