package org.openheart.ohbmtrainer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ImageServiceImpl implements ImageService {
    @Autowired
    private DirectoryService directoryService;

    @Autowired
    private ImageNameObfuscator imageNameObfuscator;

    private Random random = new Random();

    @Override
    public String suggestImage(Integer level) {
        String imageName = randomImage(directoryService.getMappedFilenames().get(level));

        return imageNameObfuscator.addImageName(imageName);
    }

    @Override
    public String unObfuscateImageName(String key) {
        return imageNameObfuscator.getImageName(key);
    }

    private String randomImage(List<String> images) {
        return images.get(random.nextInt(images.size()));
    }
}
