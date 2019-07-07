package org.openheart.ohbmtrainer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Random;

@Service
public class ImageServiceImpl implements ImageService {
    @Autowired
    private DirectoryService directoryService;

    @Autowired
    private ImageNameObfuscator imageNameObfuscator;

    private Random random = new Random();

    @Override
    public String suggestImage(Integer level) throws IOException {
        String imagePath = randomImage(directoryService.getMappedFilenames().get(level));

        return imageNameObfuscator.addImagePath(imagePath);
    }

    @Override
    public String unObfuscateImageName(String key) {
        return imageNameObfuscator.getImagePath(key);
    }

    private String randomImage(List<String> images) {
        return images.get(random.nextInt(images.size()));
    }
}
