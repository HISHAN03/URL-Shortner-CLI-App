package service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.URLMapping;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class URLShortener {

    private HashMap<String, String> keyMap;
    private HashMap<String, String> valueMap;
    private String domain;
    private char[] myChars;
    private Random myRand;
    private int keyLength;
    private ObjectMapper objectMapper;
    private final String URL_FILE_PATH = "app/src/main/java/localDb/urls.json";

    public URLShortener() throws IOException {
        keyMap = new HashMap<>();
        valueMap = new HashMap<>();
        myRand = new Random();
        keyLength = 6;
        myChars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
        domain = "http://tinyurl.com/";
        objectMapper = new ObjectMapper();
        loadURLMappingsFromFile();
    }

    public String shortenURL(String longURL) throws IOException {
        String shortURL = "";
        if (validateURL(longURL)) {
            longURL = sanitizeURL(longURL);
            if (valueMap.containsKey(longURL)) {
                shortURL = domain + valueMap.get(longURL);
            } else {
                shortURL = domain + getKey();
                keyMap.put(shortURL.replace(domain, ""), longURL);
                valueMap.put(longURL, shortURL.replace(domain, ""));
                saveURLMappingToFile(new URLMapping(longURL, shortURL));
            }
        }
        return shortURL;
    }

    private String getKey() {
        StringBuilder key = new StringBuilder();
        boolean flag = true;
        while (flag) {
            key.setLength(0);
            for (int i = 0; i < keyLength; i++) {
                key.append(myChars[myRand.nextInt(myChars.length)]);
            }
            if (!keyMap.containsKey(key.toString())) {
                flag = false;
            }
        }
        return key.toString();
    }

    private boolean validateURL(String url) {
        // logic needs to be implemented
        return true;
    }

    private String sanitizeURL(String url) {
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            url = "http://" + url;
        }
        if (url.endsWith("/")) {
            url = url.substring(0, url.length() - 1);
        }
        return url;
    }

    private void saveURLMappingToFile(URLMapping urlMapping) throws IOException {
        List<URLMapping> urlMappings = objectMapper.readValue(new File(URL_FILE_PATH), new TypeReference<List<URLMapping>>() {});
        urlMappings.add(urlMapping);
        objectMapper.writeValue(new File(URL_FILE_PATH), urlMappings);
    }

    private void loadURLMappingsFromFile() throws IOException {
        List<URLMapping> urlMappings = objectMapper.readValue(new File(URL_FILE_PATH), new TypeReference<List<URLMapping>>() {});
        for (URLMapping urlMapping : urlMappings) {
            keyMap.put(urlMapping.getShortURL().replace(domain, ""), urlMapping.getLongURL());
            valueMap.put(urlMapping.getLongURL(), urlMapping.getShortURL().replace(domain, ""));
        }
    }
}
