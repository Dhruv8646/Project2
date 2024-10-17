package com.example.urlshortener.controller;

import com.example.urlshortener.model.Url;
import com.example.urlshortener.repository.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/url")
public class UrlController {

    @Autowired
    private UrlRepository urlRepository;

    // API to shorten the URL
    @PostMapping("/shorten")
    public Url shortenUrl(@RequestBody Url urlRequest) {
        String shortUrl = UUID.randomUUID().toString().substring(0, 8);
        Url url = new Url();
        url.setLongUrl(urlRequest.getLongUrl());
        url.setShortUrl(shortUrl);
        url.setClickCount(0);
        return urlRepository.save(url);
    }

    // API to handle redirection
    @GetMapping("/{shortUrl}")
    public String redirectUrl(@PathVariable String shortUrl) {
        Url url = urlRepository.findByShortUrl(shortUrl);
        if (url != null) {
            url.setClickCount(url.getClickCount() + 1);
            urlRepository.save(url);
            return "Redirecting to: " + url.getLongUrl();
        } else {
            return "Short URL not found!";
        }
    }

    // API to get URL analytics
    @GetMapping("/analytics/{shortUrl}")
    public Url getUrlAnalytics(@PathVariable String shortUrl) {
        return urlRepository.findByShortUrl(shortUrl);
    }
}
