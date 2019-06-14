package org.openheart.ohbmtrainer.service;

public interface ImageNameObfuscator {

    String addImageName(String imageName);

    String getImageName(String key);
}
