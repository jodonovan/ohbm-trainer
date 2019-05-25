package org.openheart.ohbmtrainer.service;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class DirectoryServiceImpl implements DirectoryService {
    @Value("${image.directory}")
    private String imagesDirectory;

//    @Value("${image.regex.java}")
    private String regexJava = "\\s+([0-9]*\\.?[0-9]*)\\s+([Bb][Hh][Uu][Mm][Ii])";

    private Logger logger = LoggerFactory.getLogger(DirectoryServiceImpl.class);

    @Cacheable("mappedFilenames")
    @Override
    public Map<Integer, List<String>> getMappedFilenames() {
        // see https://regex101.com/r/RHSuzi/2 for explanation of regex
        // list the files
        // iterate through them
        // extract the bhumi - try and parse an Integer - if fail, skip, log a warning
        // create a the map and return
        // cache the result for 1 hour
        Map<Integer, List<String>> mappedFilenames = initializeMappedFilenames();
        File directory = new File(ClassLoader.getSystemClassLoader().getResource(imagesDirectory).getFile());
        logger.debug("Image directory " + imagesDirectory + " exists : {} ", directory.exists());
        List<File> files = (List<File>) FileUtils.listFiles(directory, null, false);

        Pattern pattern = Pattern.compile(regexJava);
        logger.debug("Number of files : {}", files.size());
        for (File file : files) {
            String fileName = file.getName();
            Matcher matcher = pattern.matcher(fileName);
            if (matcher.find()) {
                Integer level = parseLevel(matcher.group(1));
                mappedFilenames.get(level).add(fileName);
                logger.debug(level + " : " + fileName);
            } else {
                logger.warn("Filename {} could not be parsed.", fileName);
            }
        }

        return mappedFilenames;
    }


    /**
     * Handles occasional x.5 levels
     * @param level
     * @return
     */
    private Integer parseLevel(String level) {
        return (int) Math.floor(Double.valueOf(level));
    }

    private HashMap<Integer, List<String>> initializeMappedFilenames() {
        HashMap<Integer, List<String>> initialMap = new HashMap<>();
        for (int i = 0; i <= 13; i++) {
            initialMap.put(i, new ArrayList<>());
        }

        return initialMap;
    }
}
