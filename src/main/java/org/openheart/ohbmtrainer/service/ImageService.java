package org.openheart.ohbmtrainer.service;


import java.io.IOException;

public interface ImageService {

    String suggestImage(Integer level) throws IOException;

    String unObfuscateImageName(String key);
}
