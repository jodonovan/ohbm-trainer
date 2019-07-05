package org.openheart.ohbmtrainer.service;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
public class ImageNameObfuscatorImpl implements ImageNameObfuscator {
    private Cache<String, String> cache;

    @Override
    public String addImagePath(String imagePath) {
        String fileExtension = imagePath.substring(imagePath.lastIndexOf("."));
        String key = UUID.randomUUID().toString() + fileExtension;

        cache.put(key, imagePath);

        return key;
    }

    @Override
    public String getImagePath(String key) {
        return cache.getIfPresent(key);
    }

    @PostConstruct
    private void createCache() {
        cache = CacheBuilder.newBuilder().expireAfterAccess(30, TimeUnit.MINUTES).build();
    }
}
