package service;
import entities.URLMapping;
import java.util.HashMap;
import java.util.Random;

public class URLShortener {

    private HashMap<String, String> keyMap; // key-url map
    private HashMap<String, String> valueMap; // url-key map
    private String domain; // Domain name for short URLs
    private char[] myChars; // Character set for generating short URLs
    private Random myRand; // Random object for generating random integers
    private int keyLength; // Length of the generated short URL key


    public URLShortener() {
        keyMap = new HashMap<>();
        valueMap = new HashMap<>();
        myRand = new Random();
        keyLength = 6; // Default length for short URL key
        myChars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
        domain = "http://tinyurl.com/";
    }

    public String shortenURL(String longURL) {
        String shortURL = "";
        if (validateURL(longURL)) {
            longURL = sanitizeURL(longURL);
            if (valueMap.containsKey(longURL)) {
                shortURL = domain + valueMap.get(longURL);
            } else {
                shortURL = domain + getKey();
                keyMap.put(shortURL, longURL);
                valueMap.put(longURL, shortURL);
            }
        }
        return shortURL;
    }

    public String expandURL(String shortURL) {
        return keyMap.getOrDefault(shortURL.replace(domain, ""), "Short URL not found.");
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
        // You can implement URL validation logic here if needed
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








}
