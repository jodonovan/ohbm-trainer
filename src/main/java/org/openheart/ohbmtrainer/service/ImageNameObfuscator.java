package org.openheart.ohbmtrainer.service;

public interface ImageNameObfuscator {

    String addImagePath(String imageName);

    String getImagePath(String key);
}
