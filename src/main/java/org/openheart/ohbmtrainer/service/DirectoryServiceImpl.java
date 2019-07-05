package org.openheart.ohbmtrainer.service;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class DirectoryServiceImpl implements DirectoryService {

    @Value("${image.directory.bhumi}")
    private String bhumiImagesDirectory;

    @Value("${image.directory.awakening}")
    private String awakeningImagesDirectory;

    // see https://regex101.com/r/RHSuzi/2 for explanation of regexes
    private String regexBhumiImageNameJava = "\\s+([0-9]*\\.?[0-9]*)\\s+([Bb][Hh][Uu][Mm][Ii])";

    private String regexAwakeningImageNameJava = "(.*)\\s([0-9]*)\\.";

    private Logger logger = LoggerFactory.getLogger(DirectoryServiceImpl.class);

    @Cacheable("mappedFilenames")
    @Override
    public Map<Integer, List<String>> getMappedFilenames() throws IOException {
        Map<Integer, List<String>> mappedPaths = initializeMappedPaths();

        File bhumiDirectory = ResourceUtils.getFile(bhumiImagesDirectory);
        File awakeningDirectory = ResourceUtils.getFile(awakeningImagesDirectory);
        logger.info("Bhumi image directory " + bhumiImagesDirectory + " exists :{} ", bhumiDirectory.isDirectory());
        logger.info("Awakening image directory " + awakeningImagesDirectory + " exists: {} ", bhumiDirectory.isDirectory());

        extractBhumiImagePaths(mappedPaths, bhumiDirectory);
        extractAwakeningImagePaths(mappedPaths, awakeningDirectory);

        return mappedPaths;
    }

    private void extractAwakeningImagePaths(Map<Integer, List<String>> mappedPaths, File awakeningDirectory) {
        AwakeningFileNameOrganiser awakeningFileNameOrganiser = new AwakeningFileNameOrganiser();

        List<File> files = (List<File>) FileUtils.listFiles(awakeningDirectory, null, false);
        logger.info("Number of awakening files : {}", files.size());
        Pattern pattern = Pattern.compile(regexAwakeningImageNameJava);
        for (File file : files) {
            String fileName = file.getName();
            Matcher matcher = pattern.matcher(fileName);
            if (matcher.find()) {
                String name = matcher.group(1);
                String index = matcher.group(2);
                PathIndex pathIndex = new PathIndex(Integer.parseInt(index), file.getAbsolutePath());
                awakeningFileNameOrganiser.add(name, pathIndex);
                logger.debug("Adding {}, {}", file.getAbsolutePath(), Integer.parseInt(index));
            }
        }

        awakeningFileNameOrganiser.crop();
        awakeningFileNameOrganiser.getMap().keySet().forEach(key -> {
            SortedSet<PathIndex> pathIndices = awakeningFileNameOrganiser.getMap().get(key);
            String unawakenedFilePath = pathIndices.first().getPath();
            String awakenedFilePath = pathIndices.last().getPath();
            mappedPaths.get(0).add(unawakenedFilePath);
            mappedPaths.get(1).add(awakenedFilePath);
        });
    }

    private void extractBhumiImagePaths(Map<Integer, List<String>> mappedFilenames, File bhumiDirectory) {
        List<File> files = (List<File>) FileUtils.listFiles(bhumiDirectory, null, false);
        Pattern pattern = Pattern.compile(regexBhumiImageNameJava);
        logger.info("Number of bhumi files : {}", files.size());
        for (File file : files) {
            String fileName = file.getName();
            Matcher matcher = pattern.matcher(fileName);
            if (matcher.find()) {
                Integer level = parseLevel(matcher.group(1));
                mappedFilenames.get(level).add(file.getAbsolutePath());
                logger.debug(level + " : " + fileName);
            } else {
                logger.warn("Filename {} could not be parsed.", fileName);
            }
        }
    }

    /**
     * Handles occasional x.5 levels
     * @param level
     * @return
     */
    private Integer parseLevel(String level) {
        return (int) Math.floor(Double.valueOf(level));
    }

    /**
     * Initializes a map of bhumi level to image paths.
     * @return
     */
    private HashMap<Integer, List<String>> initializeMappedPaths() {
        HashMap<Integer, List<String>> initialMap = new HashMap<>();
        for (int i = 0; i <= 13; i++) {
            initialMap.put(i, new ArrayList<>());
        }

        return initialMap;
    }
}
