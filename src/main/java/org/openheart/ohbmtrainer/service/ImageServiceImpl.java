package org.openheart.ohbmtrainer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    private DirectoryService directoryService;

    private Random random = new Random();

    @Override
    public String suggestImage(Set<Integer> levels) {
        Integer level = randomLevel(levels);

        return suggestImage(level);
    }

    private String suggestImage(Integer level) {
        return randomImage(directoryService.getMappedFilenames().get(level));
    }

    private String randomImage(List<String> images) {
        return images.get(random.nextInt(images.size()));
    }

    private Integer randomLevel(Set<Integer> levels) {
        return ((ArrayList<Integer>) new ArrayList(levels)).get(random.nextInt(levels.size()));
    }

}
