package org.openheart.ohbmtrainer.service;


public interface ImageService {
    String suggestImage(Integer level);

    String unObfuscateImageName(String key);
}
