package org.openheart.ohbmtrainer.rest.controller;

import org.apache.commons.io.IOUtils;
import org.openheart.ohbmtrainer.rest.domain.ImageNameRest;
import org.openheart.ohbmtrainer.rest.domain.RefIdRest;
import org.openheart.ohbmtrainer.service.DirectoryService;
import org.openheart.ohbmtrainer.service.ImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.support.ServletContextResource;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api/image")
public class ImageController {
    public static final String PNG = ".png";
    private final Logger log = LoggerFactory.getLogger(ImageController.class);

    @Autowired
    private ServletContext servletContext;

    @Autowired
    private ImageService imageService;

    @GetMapping()
    public ResponseEntity<?> getImage(@RequestParam Integer level) {
        log.debug(level.toString());

//        RefIdRest refIdRest = new RefIdRest();
//        refIdRest.setRefId(UUID.randomUUID());
//
//        return ResponseEntity.ok().body(refIdRest);
        return ResponseEntity.ok().body(new ImageNameRest(imageService.suggestImage(level)));
    }

    // call http://localhost:8080/api/image/resource2.jpeg
    // this works but the browser has to add .jpg to the request otherwise it treats the response as text/html
    // if this works for other images types - use it as we can return the type of image in the response from the server
    // and add that to the request
    // if we cache the refId for 1 hour, then we can allow cache control from the browser
//    @RequestMapping(value = "/resource2", method = RequestMethod.GET)
//    @ResponseBody
//    public ResponseEntity<Resource> getImageAsResource() {
//        HttpHeaders headers = new HttpHeaders();
////        Resource resource =
////        new ServletContextResource(servletContext, "/WEB-INF/images/dog.jpg");
//        Resource resource = new ClassPathResource("static/images/dog.jpg");
//        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
//    }
//
//    @RequestMapping(value = "/image-manual-response", method = RequestMethod.GET)
//    public void getImageAsByteArray(HttpServletResponse response) throws IOException {
//        InputStream in = servletContext.getResourceAsStream("/WEB-INF/images/dog.jpg");
//        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
//        IOUtils.copy(in, response.getOutputStream());
//    }


    @RequestMapping(value = "/resource", method = RequestMethod.GET,
            produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    public void getImage(@RequestParam String imageName, HttpServletResponse response) throws IOException {
        // get the image filename from the request using the service
        // if the filename is .png return png content type, else if it contains .jpeg or .jpg return .jpg
        // else return bad request
        ///Users/jonathanodonovan/workspace/react/ohbm-trainer/src/main/resources/static/images/Barche Dorje 10 bhumi 5.2018.jpg
        ClassPathResource classPathResource = new ClassPathResource("static/images/" + imageName);

        String mediaType = MediaType.IMAGE_JPEG_VALUE;
        if (imageName.toLowerCase().endsWith(PNG)) {
            mediaType = MediaType.IMAGE_PNG_VALUE;
        }
        response.setContentType(mediaType);

        StreamUtils.copy(classPathResource.getInputStream(), response.getOutputStream());
    }

//    // when the browser sets the image type in the request, it responds correctly with the correct mime type
//    @RequestMapping(value = "/image-byte-array", method = RequestMethod.GET)
//    public @ResponseBody byte[] getImageAsByteArray() throws IOException {
////        InputStream in = servletContext.getResourceAsStream("static/images/dog.jpg");
////        ClassPathResource classPathResource = new ClassPathResource("static/images/dog.jpg");
//        ClassPathResource classPathResource = new ClassPathResource("static/images/marble24.png");
//
//        return IOUtils.toByteArray(classPathResource.getInputStream());
//    }

}
