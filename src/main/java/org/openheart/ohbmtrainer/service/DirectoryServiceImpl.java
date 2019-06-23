package org.openheart.ohbmtrainer.service;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@PropertySource("classpath:image.properties")
public class DirectoryServiceImpl implements DirectoryService {

    @Value("${image.directory}")
    private String imagesDirectory;

    // see https://regex101.com/r/RHSuzi/2 for explanation of regex
    private String regexJava = "\\s+([0-9]*\\.?[0-9]*)\\s+([Bb][Hh][Uu][Mm][Ii])";

    private Logger logger = LoggerFactory.getLogger(DirectoryServiceImpl.class);

    @Autowired
    private ResourceLoader resourceLoader;

    @Cacheable("mappedFilenames")
    @Override
    public Map<Integer, List<String>> getMappedFilenames() throws IOException {
        Map<Integer, List<String>> mappedFilenames = initializeMappedFilenames();

        imagesDirectory = "/Users/jonathanodonovan/workspace/react/ohbm-trainer/src/main/resources/static/images/";

//        File resourceUtilsFile = ResourceUtils.getFile("classpath:" + imagesDirectory);

//        logger.info("ResourceUtils.getFile(\"classpath:\" + imagesDirectory);" + resourceUtilsFile.getName());

        logger.info("ImagesDirectory:" + imagesDirectory);
//        logger.info("ClassLoader.getSystemClassLoader().getResource(imagesDirectory):" + ClassLoader.getSystemClassLoader().getResource(imagesDirectory));
//        File directory = new File(ClassLoader.getSystemClassLoader().getResource(imagesDirectory).getFile());


//        InputStream inputStream = ResourceUtils.getURL(imagesDirectory).openStream();


        // WORKS !! :
        File file1 = ResourceUtils.getFile(imagesDirectory);
        logger.info("File1 Exists : " + file1.exists());
        List<File> filesExternalDir = (List<File>) FileUtils.listFiles(file1, null, false);
        // END OF WORKS


//        Resource resource = resourceLoader.getResource("classpath:" + imagesDirectory); // works in intellij
        Resource resource = resourceLoader.getResource(imagesDirectory); // works in intellij FOR static/images

//        File file3 = ResourceUtils.getInputStream("classpath:" + imagesDirectory);

//        InputStream inputStream = resource.getInputStream();


//        logger.info("File3 exists : " + file3.exists());

        File directory = resource.getFile();
        logger.debug("Image directory " + imagesDirectory + " exists : {} ", directory.exists());


//        List<File> filesHome = (List<File>) FileUtils.listFiles(new File("/Users/jonathanodonovan/workspace/react/ohbm-trainer"), null, false);
//        logger.info("Home Dir " + filesHome.toString());

//        List<File> files = (List<File>) FileUtils.listFiles(directory, null, false);

        Pattern pattern = Pattern.compile(regexJava);
        logger.debug("Number of files : {}", filesExternalDir.size());
        for (File file : filesExternalDir) {
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
