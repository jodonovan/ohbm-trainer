package org.openheart.ohbmtrainer.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/image")
public class ImageController {
    private final Logger log = LoggerFactory.getLogger(ImageController.class);

    @GetMapping("/{refId}")
    public ResponseEntity<?> getImage(@PathVariable UUID refId) {
        return ResponseEntity.ok().body(refId.toString());
    }
}
