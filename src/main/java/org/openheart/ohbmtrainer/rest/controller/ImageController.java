package org.openheart.ohbmtrainer.rest.controller;

import org.openheart.ohbmtrainer.rest.domain.ImageNameRest;
import org.openheart.ohbmtrainer.service.ImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;

@RestController
@RequestMapping("/api/image")
public class ImageController {
    public static final String PNG = ".png";
    private final Logger log = LoggerFactory.getLogger(ImageController.class);

    @Autowired
    private ImageService imageService;

    @GetMapping()
    public ResponseEntity<?> getObfuscatedImageName(@RequestParam Integer level) throws IOException {
        log.debug("Requested level: {}", level.toString());

        return ResponseEntity.ok().body(new ImageNameRest(imageService.suggestImage(level)));
    }

    @RequestMapping(value = "/resource", method = RequestMethod.GET,
            produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    public void getImageResource(@RequestParam String imageName, HttpServletResponse response) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(imageService.unObfuscateImageName(imageName));
        String mediaType = MediaType.IMAGE_JPEG_VALUE;
        if (imageName.toLowerCase().endsWith(PNG)) {
            mediaType = MediaType.IMAGE_PNG_VALUE;
        }
        response.setContentType(mediaType);

        StreamUtils.copy(fileInputStream, response.getOutputStream());
    }
}
