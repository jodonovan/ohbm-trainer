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
    public String addImageName(String imageName) {
        String fileExtension = imageName.substring(imageName.lastIndexOf("."));
        String key = UUID.randomUUID().toString() + fileExtension;

        cache.put(key, imageName);

        return key;
    }

    @Override
    public String getImageName(String key) {
        return cache.getIfPresent(key);
    }

    @PostConstruct
    private void createCache() {
        cache = CacheBuilder.newBuilder().expireAfterAccess(30, TimeUnit.MINUTES).build();
    }
}
